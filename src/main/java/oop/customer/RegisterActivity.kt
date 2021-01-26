package oop.customer

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.networktask.jsonRequestBody
import oop.customer.api.replaceWith
import oop.customer.api.snackMessage
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var settings: SharedPreferences

    private fun loggedIn() =
            settings.getString(AUTH_KEY, null) != null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        settings = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        if (loggedIn())
            replaceWith<MainActivity>()

        loginBtn.setOnClickListener {
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
            NetworkTask(
                LOGIN_LINK, NetworkTask.Method.POST, """{
             "username": "$usernameS",
             "email": "$emailS",
             "password": "$passwordS"
    }""".trimIndent().jsonRequestBody,
                this, getString(R.string.message_wait)
            ).setOnCallBack { response, s ->
                if(response?.code == 200 && s != null) {
                    val jObject = JSONObject(s)
                    settings.edit()
                        .putString(AUTH_KEY, jObject.getString("key"))
                        .putString(USERNAME_KEY, usernameS)
                        .putString(EMAIL_KEY, emailS)
                        .apply()
                    replaceWith<MainActivity>()
                }
                else if(response?.code == 400 && s != null){
                    register_layout.snackMessage(s)
                }
                else{
                    register_layout.snackMessage(getString(R.string.message_net_error))
                }
            }.send()
        }

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
            if(passwordS != passwordcS){
                password_confirmTxt.error = getString(R.string.message_re_password)
                return@setOnClickListener
            }
            NetworkTask(
                REGISTER_LINK, NetworkTask.Method.POST, """{
             "username": "$usernameS",
             "password1":"$passwordS",
             "password2": "$passwordcS",
             "email": "$emailS"
    }""".trimIndent().jsonRequestBody,
                this, getString(R.string.message_wait)
            ).setOnCallBack { response, s ->
                if(response?.code == 201 && s != null) {
                    val jObject = JSONObject(s)
                    settings.edit()
                        .putString(AUTH_KEY, jObject.getString("key"))
                        .putString(USERNAME_KEY, usernameS)
                        .putString(EMAIL_KEY, emailS)
                        .apply()
                    replaceWith<MainActivity>()
                }
                else if(response?.code == 400 && s != null){
                    register_layout.snackMessage(s)
                }
                else{
                    register_layout.snackMessage(getString(R.string.message_net_error))
                }
            }.send()
        }
    }



}
