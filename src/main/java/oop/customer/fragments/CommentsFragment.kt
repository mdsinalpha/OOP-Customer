package oop.customer.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.comment_fragment.*
import kotlinx.android.synthetic.main.product_item.*
import oop.customer.*
import oop.customer.api.networktask.NetworkTask


class CommentsFragment(private val productID: Int) : Fragment() {
    private val klaxon = Klaxon()

    private lateinit var settings: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.comment_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        settings = activity!!.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        NetworkTask(
            "$SERVER_LINK/store/comment/$productID/",
            NetworkTask.Method.GET,
            null,
            activity,
            null,
            "Authorization" to "Token ${settings.getString(AUTH_KEY, "")}"
        ).setOnCallBack { response, s ->
            if (response!!.code == 200 && s != null) {
                klaxon.parseArray<Comment>(response!!.body.toString())!!.forEach {
                    val textView = TextView(activity)
                    textView.text= it.text
                    comments.addView(textView)
                }
            }
        }.send()

    }

}