package com.example.androidsampleapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsampleapp.Todo
import com.example.androidsampleapp.model.TodoDatabase
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = TodoDatabase.Companion.getDatabase(application).todoDao()
    val allTodos: LiveData<List<Todo>> = dao.getAllTodos()

    fun insert(todo: Todo) = viewModelScope.launch {
        dao.insert(todo)
    }

    fun update(todo: Todo) = viewModelScope.launch {
        dao.update(todo)
    }

    fun delete(todo: Todo) = viewModelScope.launch {
        dao.delete(todo)
    }
}