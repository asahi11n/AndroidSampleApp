package com.example.androidsampleapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidsampleapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TodoAdapter { todo ->
            val intent = Intent(this, EditTodoActivity::class.java).apply {
                putExtra("EXTRA_ID", todo.id)
                putExtra("EXTRA_TITLE", todo.title)
                putExtra("EXTRA_CONTENT", todo.content)
                putExtra("EXTRA_DATE", todo.createdAt)
            }
            startActivity(intent)
        }

        binding.recyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.allTodos.observe(this) { todos ->
            adapter.submitList(todos)
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, EditTodoActivity::class.java)
            startActivity(intent)
        }
    }
}
