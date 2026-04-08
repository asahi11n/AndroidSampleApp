package com.example.androidsampleapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.androidsampleapp.databinding.ActivityEditTodoBinding
import java.text.SimpleDateFormat
import java.util.*

class EditTodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTodoBinding
    private val viewModel: TodoViewModel by viewModels()
    private var todoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoId = intent.getIntExtra("EXTRA_ID", -1)
        val title = intent.getStringExtra("EXTRA_TITLE")
        val content = intent.getStringExtra("EXTRA_CONTENT")

        if (todoId != -1) {
            binding.editTextTitle.setText(title)
            binding.editTextContent.setText(content)
            binding.buttonDelete.visibility = View.VISIBLE
        }

        binding.buttonSave.setOnClickListener {
            saveTodo()
        }

        binding.buttonDelete.setOnClickListener {
            deleteTodo()
        }
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
            val updatedTodo = Todo(id = todoId, title = title, content = content, createdAt = currentDate)
            viewModel.update(updatedTodo)
        }
        finish()
    }

    private fun deleteTodo() {
        if (todoId != -1) {
            val title = intent.getStringExtra("EXTRA_TITLE") ?: ""
            val content = intent.getStringExtra("EXTRA_CONTENT") ?: ""
            val date = intent.getStringExtra("EXTRA_DATE") ?: ""
            val todo = Todo(id = todoId, title = title, content = content, createdAt = date)
            viewModel.delete(todo)
            finish()
        }
    }
}
