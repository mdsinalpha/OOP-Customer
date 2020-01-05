package oop.customer

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.networktask.jsonRequestBody
import oop.customer.api.replaceWith

class RegisterActivity : AppCompatActivity() {

    private lateinit var settings: SharedPreferences

    private fun loggedIn() =
            settings.getString(USERNAME_KEY, null) != null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        settings = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        // TODO Remove Fake Data
        settings.edit().putString(USERNAME_KEY, "mdsinalpha").putString(EMAIL_KEY, "me@mdsinalpha.ir").apply()
        // settings.edit().clear().apply()

        if (loggedIn())
            replaceWith<MainActivity>()

        registerBtn.setOnClickListener{
            val usernameS = usernameTxt.text.trim().toString()
            if(usernameS == ""){
                usernameTxt.error = getString(R.string.message_whitespace)
                return@setOnClickListener
            }
            val emailS = emailTxt.text.trim().toString()
            if(emailS == ""){
                emailTxt.error = getString(R.string.message_whitespace)
                return@setOnClickListener
            }
            val passwordS = passwordTxt.text.trim().toString()
            if(passwordS == ""){
                passwordTxt.error = getString(R.string.message_whitespace)
                return@setOnClickListener
            }
            val passwordcS = password_confirmTxt.text.trim().toString()
            if(passwordcS == ""){
                password_confirmTxt.error = getString(R.string.message_whitespace)
                return@setOnClickListener
            }
            NetworkTask(
                REGISTER_LINK, NetworkTask.Method.POST, """{
             "username": "$usernameS",
             "password":"$passwordS",
             "password_confirm": "$passwordcS",
             "email":"$emailS"
    }""".trimIndent().jsonRequestBody,
                this, getString(R.string.message_wait)
            ).setOnCallBack { response, s ->
                // TODO save user data and move
            }.send()
        }
    }



}
