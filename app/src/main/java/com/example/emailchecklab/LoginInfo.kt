package com.example.emailchecklab

import android.util.Patterns
import android.provider.ContactsContract.*
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel

class LoginInfo : ViewModel() {
    companion object {
        var email = ""
        var password = ""
    }
}