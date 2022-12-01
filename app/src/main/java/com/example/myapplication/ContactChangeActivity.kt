package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class ContactChangeActivity: AppCompatActivity() {


    private val list = mutableListOf<Todo>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_contact)
        title = "Изменение контакта"
        val dbHelper = DBHelper(this)
        val button = findViewById<Button>(R.id.button)

        val editText = findViewById<EditText>(R.id.editTextTextMultiLine)
        val editTextName = findViewById<EditText>(R.id.editTextTextMultiLine2)
        val editTextPhone = findViewById<EditText>(R.id.editTextPhone)


        val intent = intent
        val id = intent.getLongExtra("Id",0)
        val allData = dbHelper.getById(id)

        editText.setText(allData?.title)
        editTextName.setText(allData?.name)
        editTextPhone.setText(allData?.telephone)

        val buttonChange = findViewById<Button>(R.id.button3)
        buttonChange.setOnClickListener {
            val title = editText.text.toString()
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()
            val cool = dbHelper.update(id.toString().toLong(),title,name,phone)
            list.add(Todo(id.toString().toLong(),title,name,phone))
            val intent = Intent(this@ContactChangeActivity, MainActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val intent = Intent(this@ContactChangeActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}