package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var list = mutableListOf<Todo>()
    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dbHelper = DBHelper(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // заполнить list из базы
        var Data = dbHelper.getAll()
        val  editText = findViewById<EditText>(R.id.editTextTextMultiLine)
        list.addAll(Data)
        var spinner = findViewById<Spinner>(R.id.spinner)
        val options = arrayOf("Имя","Фамилия")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinner.adapter = spinnerAdapter
        var SItem = ""
        fun listReturn(){
            val filtered = Data.filter { item ->
                if (spinner.selectedItem == "Имя") {
                    return@filter item.name.contains(editText.text.toString(), true)
                }
                else{
                    return@filter item.title.contains(editText.text.toString(), true)
                }
            }
            adapter.updateList(filtered)
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // ничего не выбрали
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                listReturn()
            }
        }
        editText.doAfterTextChanged { it ->
            listReturn()
            /*val filtered = Data.filter { item ->
                if (spinner.selectedItem == "Имя") {
                    return@filter item.name.contains(it.toString(), true)
                }
                else{
                    return@filter item.title.contains(it.toString(), true)
                }
            }
            adapter.updateList(filtered)*/
            /*list.clear()
            list.addAll(Data)*/
            /*adapter.notifyDataSetChanged()*/
        }
        adapter = RecyclerAdapter(list) {
            val id = list[it].id
            val intent = Intent(this@MainActivity, ContactActivity::class.java)
            intent.putExtra("Id", id)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        val buttonAdd = findViewById<Button>(R.id.button)
        buttonAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, NewContactActivity::class.java)
            startActivity(intent)
        }

    }
}


