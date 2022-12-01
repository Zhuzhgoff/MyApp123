package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class NewContactActivity: AppCompatActivity() {

    private val list = mutableListOf<Todo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)
        title = "Добавление контакта"
        val dbHelper = DBHelper(this)
        val editText = findViewById<EditText>(R.id.editTextTextMultiLine)
        val editTextName = findViewById<EditText>(R.id.editTextTextMultiLine2)
        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        val buttonAdd = findViewById<Button>(R.id.button)
        val button = findViewById<Button>(R.id.button2)
        buttonAdd.setOnClickListener {
            val title = editText.text.toString()
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            val id = dbHelper.addTodo(title,name,phone)
            list.add(Todo(id, title, name,phone))
            val intent = Intent(this@NewContactActivity, MainActivity::class.java)
            startActivity(intent)

        }
        button.setOnClickListener {
            val intent = Intent(this@NewContactActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}