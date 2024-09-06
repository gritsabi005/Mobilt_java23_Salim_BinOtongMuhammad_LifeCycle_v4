package com.example.javakotlin

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //initiating the sharedPreference here
        val sharedPreferences = getSharedPreferences("Key1", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        var emailField : EditText = findViewById(R.id.editTextTextEmailAddress)
        var passwordField : EditText = findViewById(R.id.editTextTextPassword)
        var b : Button = findViewById(R.id.button)

        b.setOnClickListener( {
            if (emailField.getText().toString() == "sam.millan7@gmail.com" && passwordField.getText().toString() == "12345678"){
                editor.putString("email", emailField.text.toString())
                editor.apply()
                var i : Intent = Intent(this, MainActivity2::class.java)
                startActivity(i)
                Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show()

            }
            else {
                Toast.makeText(this, "Wrong credentials, try again", Toast.LENGTH_SHORT).show()
            }
        })

        emailField.setText(sharedPreferences.getString("email", ""))

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.birthdayText)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

}