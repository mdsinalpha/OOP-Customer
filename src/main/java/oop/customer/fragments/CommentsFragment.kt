package oop.customer.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.comment_fragment.*
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
            "$COMMENTS_LINK$productID/",
            NetworkTask.Method.GET,
            null,
            null,
            null,
            "Authorization" to "Token ${settings.getString(AUTH_KEY, "")}"
        ).setOnCallBack { response, s ->
            if (response?.code == 200 && s != null) {
                klaxon.parseArray<Comment>(s)!!.forEach {
                    val textView = TextView(activity)
                    val layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                    textView.layoutParams = layoutParams
                    textView.text = it.text
                    textView.setPadding(5)
                    comments.addView(textView)
                }
            }
        }.send()

    }

}