package oop.customer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import oop.customer.R
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.networktask.jsonRequestBody
import kotlin.math.log

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerBtn.setOnClickListener(View.OnClickListener {
            if (true) {
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                NetworkTask(
                    REGISTER_LINK, NetworkTask.Method.POST, """{
                 "username": "${usernameTxt.text}",
                 "password":"${passwordTxt.text}",
                 "password_confirm": "${password_confirmTxt.text}",
                 "email":"${emailTxt.text}"
        }""".trimIndent().jsonRequestBody,
                    this, "لطفا منتظر بمانید"
                ).setOnCallBack { response, s ->

                }.send()
            }
        })


    }



}
