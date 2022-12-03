package com.example.myapplication
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import dialogYesOrNo

class ContactActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        title = "Контактная информация"
        val dbHelper = DBHelper(this)
        /*val Text = findViewById<TextView>(R.id.textView4)
        val TextName = findViewById<TextView>(R.id.textView2)
        val TextPhone = findViewById<TextView>(R.id.textView6)*/
        val Text = findViewById<EditText>(R.id.editTextNumber)
        val TextName = findViewById<EditText>(R.id.editTextNumber2)
        val TextPhone = findViewById<EditText>(R.id.editTextNumber3)
        val button = findViewById<Button>(R.id.button)
        val buttonDrop = findViewById<Button>(R.id.button2)
        val buttonChange = findViewById<Button>(R.id.button3)
        val id = intent.getLongExtra("Id",0)
        val objects = dbHelper.getById(id)

        /*Text.text = ("Фамилия:  " + objects?.title)
        TextName.text = "Имя:      " + objects?.name
        TextPhone.text = "Телефон:   " + objects?.telephone*/
        Text.setText(objects?.title)
        TextName.setText(objects?.name)
        TextPhone.setText(objects?.telephone)

        TextPhone.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:"+objects?.telephone)
            startActivity(dialIntent)
        }
        /*TextPhone.setOnClickListener {
            val REQUEST_CODE = 1

            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUEST_CODE
                )

            } else {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:" + objects?.telephone)
                startActivity(callIntent)

            }
        }*/
        /*buttonChange.setOnClickListener {
            val intent = Intent(this@ContactActivity, ContactChangeActivity::class.java)
            intent.putExtra("Id", id)
            startActivity(intent)
        }*/
        val buttonChangeTrue = findViewById<Button>(R.id.button4)
        buttonChangeTrue.setOnClickListener {
            /*val title = editText.text.toString()
            val name = editTextName.text.toString()
            val phone = editTextPhone.text.toString()*/
            val title = Text.text.toString()
            val name = TextName.text.toString()
            val phone = TextPhone.text.toString()
            dbHelper.update(id.toString().toLong(),title,name,phone)
            /*val intent = Intent(this@ContactChangeActivity, MainActivity::class.java)*/
            val intent = Intent(this@ContactActivity, MainActivity::class.java)
            startActivity(intent)
        }
        buttonDrop.setOnClickListener {
            val uid = id
            dialogYesOrNo(
                this,
                "Вопрос",
                "Вы уверены,что хотите удалить контакт?",
                DialogInterface.OnClickListener { dialog, id ->
                    dbHelper.remove(uid)
                    val intent = Intent(this@ContactActivity, MainActivity::class.java)
                    startActivity(intent)
                })
        }
        button.setOnClickListener {

           val intent = Intent(this@ContactActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}