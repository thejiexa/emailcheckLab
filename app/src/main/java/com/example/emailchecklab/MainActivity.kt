package com.example.emailchecklab

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.*
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged


class MainActivity : AppCompatActivity() {

    private lateinit var registButton: Button
    private lateinit var emailTextView: AutoCompleteTextView
    private lateinit var passwordTextEdit: EditText
    private lateinit var repeatPasswordTextEdit: EditText

    private val domains = listOf(
        "gmail.com",
        "yahoo.com",
        "msn.com",
        "hotmail.com",
        "outlook.com",
        "aol.com",
        "rambler.ru",
        "yandex.ru",
        "ya.ru",
        "mail.ru",
        "inbox.ru"
    )

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registButton = findViewById(R.id.button_register)
        emailTextView = findViewById(R.id.email_EditText)
        passwordTextEdit = findViewById(R.id.password_EditText)
        repeatPasswordTextEdit = findViewById(R.id.password_EditText2)

        val adapter = ArrayAdapter(
            this, android.R.layout.simple_dropdown_item_1line, domains
        )

        emailTextView.setAdapter(adapter)

        emailTextView.doOnTextChanged { _, _, _, _ ->
            val str = emailTextView.text.toString()
            if (str.contains("@")) {
                val dom = str.substringAfter("@")
                val filtered = domains.filter { it.startsWith(dom) }
                val emails = mutableListOf<String>()
                filtered.forEach() {
                    emails.add(str.replaceAfter("@", "") + it)
                }

                val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, emails)
                emailTextView.setAdapter(adapter)
                emailTextView.showDropDown()
            }

        }

        registButton.setOnClickListener {
            val email = this.emailTextView.text.toString()
            val password = passwordTextEdit.text.toString()
            val password2 = repeatPasswordTextEdit.text.toString()

            if (email.isEmpty()) this.emailTextView.setHint(R.string.fill_it)
            if (password.isEmpty()) passwordTextEdit.setHint(R.string.fill_it)
            if (password2.isEmpty()) repeatPasswordTextEdit.setHint(R.string.fill_it)


            val errorMessage = checkAllTextViews(email, password, password2)
            if (errorMessage != null) {
                Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
            } else {
                LoginInfo.email = email
                LoginInfo.password = password
                startActivity(Intent(this, RegisteredActivity::class.java))
            }

        }

    }

    private fun passwordLengthCheck(password: String): Boolean = password.length in 4..20

    private fun domainCheck(email: String): Boolean {
        if (email.contains('@')) {
            val domain = email.substringAfter("@")
            if (domains.contains(domain)) {
                return true
            }
        }
        return false
    }

    private fun emailCheck(email: String): Boolean = !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun checkAllTextViews(email: String, password: String, password2: String): String? {
        return if (!domainCheck(email)) "There is no such domain"
        else if (!emailCheck(email)) "Incorrect email"
        else if (email.isEmpty() || password.isEmpty() || password2.isEmpty()) "Fill all text views"
        else if (!passwordLengthCheck(password)) "Password length is 4..20"
        else if (!password.equals(password2)) "Passwords are different"
        else null
    }

}




