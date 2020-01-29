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
import oop.customer.api.snackMessage

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
            val oldPassword = p_current_passTxt.text.trim().toString()
            val newPassword = p_new_passTxt.text.trim().toString()
            val rePassword = p_re_passTxt.text.trim().toString()
            when {
                oldPassword + newPassword + rePassword == "" -> {
                    settings.edit().putString(EMAIL_KEY, emailS).apply()
                    profile_layout.snackMessage("اطلاعات با موفقیت ذخیره شد.")
                    return@setOnClickListener
                }
                oldPassword == "" -> {
                    p_current_passTxt.error = getString(R.string.message_whitespace)
                    return@setOnClickListener
                }
                newPassword == "" -> {
                    p_new_passTxt.error = getString(R.string.message_whitespace)
                    return@setOnClickListener
                }
                rePassword == "" -> {
                    p_re_passTxt.error = getString(R.string.message_whitespace)
                    return@setOnClickListener
                }
                oldPassword == newPassword -> {
                    p_new_passTxt.error = getString(R.string.message_ol_password)
                    return@setOnClickListener
                }
                newPassword != rePassword -> {
                    p_re_passTxt.error = getString(R.string.message_re_password)
                    return@setOnClickListener
                }
                else -> NetworkTask(
                    EDIT_PRO_LINK, NetworkTask.Method.POST, """{
                    "old_password": "$oldPassword",
                    "new_password1": "$newPassword",
                    "new_password2": "$rePassword"
            }""".trimIndent().jsonRequestBody,
                    activity, getString(R.string.message_wait),
                    "Authorization" to "Token ${settings.getString(AUTH_KEY, "")}"
                ).setOnCallBack { response, s ->
                    when{
                        response?.code == 200 && s != null -> {
                            settings.edit().putString(EMAIL_KEY, emailS).apply()
                            profile_layout.snackMessage("اطلاعات با موفقیت ذخیره شد.")
                        }
                        response?.code == 400 && s != null -> {
                            profile_layout.snackMessage(s)
                        }
                        else -> {
                            profile_layout.snackMessage(getString(R.string.message_net_error))
                        }
                    }
                }.send()
            }
        }
    }

}
