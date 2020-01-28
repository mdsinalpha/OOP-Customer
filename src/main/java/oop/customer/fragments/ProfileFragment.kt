package oop.customer.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.*
import oop.customer.*
import oop.customer.api.networktask.NetworkTask

import oop.customer.api.networktask.jsonRequestBody

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_profile, container, false)

    private lateinit var settings: SharedPreferences

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        settings = activity!!.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        p_usernameTxt.setText(settings.getString(USERNAME_KEY, ""))
        p_emailTxt.setText(settings.getString(EMAIL_KEY, ""))
        saveProBtn.setOnClickListener {
            val usernameS = p_usernameTxt.text.trim().toString()
            if(usernameS == ""){
                p_usernameTxt.error = getString(R.string.message_whitespace)
                return@setOnClickListener
            }
            val emailS = p_emailTxt.text.trim().toString()
            if(emailS == ""){
                p_emailTxt.error = getString(R.string.message_whitespace)
                return@setOnClickListener
            }
            // TODO
            NetworkTask(
                EDIT_PRO_LINK, NetworkTask.Method.POST, """{

    }""".trimIndent().jsonRequestBody,
                activity, getString(R.string.message_wait)
            ).setOnCallBack { response, s ->

            }.send()
        }
    }

}
