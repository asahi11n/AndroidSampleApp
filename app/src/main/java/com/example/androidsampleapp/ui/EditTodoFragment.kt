package com.example.androidsampleapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.androidsampleapp.Todo
import com.example.androidsampleapp.databinding.FragmentEditTodoBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditTodoFragment : Fragment() {

    private var _binding: FragmentEditTodoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoViewModel by activityViewModels()

    private var todoId: Int = -1
    private var todoCreatedAt: String = ""

    companion object {
        private const val ARG_ID = "arg_id"
        private const val ARG_TITLE = "arg_title"
        private const val ARG_CONTENT = "arg_content"
        private const val ARG_DATE = "arg_date"

        fun newInstance(id: Int, title: String, content: String, date: String): EditTodoFragment {
            val fragment = EditTodoFragment()
            val args = Bundle()
            args.putInt(ARG_ID, id)
            args.putString(ARG_TITLE, title)
            args.putString(ARG_CONTENT, content)
            args.putString(ARG_DATE, date)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            todoId = it.getInt(ARG_ID, -1)
            val title = it.getString(ARG_TITLE)
            val content = it.getString(ARG_CONTENT)
            todoCreatedAt = it.getString(ARG_DATE, "")

            if (todoId != -1) {
                binding.editTextTitle.setText(title)
                binding.editTextContent.setText(content)
                binding.buttonDelete.visibility = View.VISIBLE
            }
        }

        binding.buttonSave.setOnClickListener {
            saveTodo()
        }

        binding.buttonDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("削除の確認")
            .setMessage("このTODOを削除してもよろしいですか？")
            .setPositiveButton("削除") { _, _ ->
                deleteTodo()
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    private fun saveTodo() {
        val title = binding.editTextTitle.text.toString()
        val content = binding.editTextContent.text.toString()

        if (title.isBlank()) return

        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        val currentDate = sdf.format(Date())

        if (todoId == -1) {
            val newTodo = Todo(title = title, content = content, createdAt = currentDate)
            viewModel.insert(newTodo)
        } else {
            val updatedTodo =
                Todo(id = todoId, title = title, content = content, createdAt = currentDate)
            viewModel.update(updatedTodo)
        }
        parentFragmentManager.popBackStack()
    }

    private fun deleteTodo() {
        if (todoId != -1) {
            val title = binding.editTextTitle.text.toString()
            val content = binding.editTextContent.text.toString()
            val todo =
                Todo(id = todoId, title = title, content = content, createdAt = todoCreatedAt)
            viewModel.delete(todo)
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}