package oop.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_register.*
import oop.customer.R
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.networktask.jsonRequestBody

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        NetworkTask(
            REGISTER_LINK, NetworkTask.Method.POST, """{
            "username": "${usernameTxt.text}",
            "password":"",
            "password_confirm": "",
            "email":""
        }""".trimIndent().jsonRequestBody,
            this, "لطفا منتظر بمانید"
            )

    }


}
