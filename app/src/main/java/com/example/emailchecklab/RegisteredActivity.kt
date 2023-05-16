package com.example.emailchecklab

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class RegisteredActivity: AppCompatActivity()  {

    private lateinit var registeredTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)

        registeredTextView = findViewById(R.id.registered_textView)
        registeredTextView.text = "Registered\nEmail: ${LoginInfo.email}\nPassword: ${LoginInfo.password}"
    }


}