package oop.customer.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.fragment_basket_purchase.*
import kotlinx.android.synthetic.main.listitem_basket.view.*
import oop.customer.*
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.networktask.jsonRequestBody
import oop.customer.api.recyclerview.bind
import oop.customer.api.showFragment2
import oop.customer.api.snackMessage
import oop.customer.api.withDivider
import org.json.JSONArray
import org.json.JSONObject

class BasketPurchaseFragment(val colors: Map<Int, Color>): Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_basket_purchase, container, false)

    private lateinit var settings: SharedPreferences

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        settings = activity!!.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        NetworkTask(
            LAST_BASKET_LINK, NetworkTask.Method.GET, null, activity, getString(R.string.message_wait),
            "Authorization" to "Token ${settings.getString(AUTH_KEY, "")}")
            .setOnCallBack { response, s ->
                if(response?.code == 200 && s != null){
                    val jArray = JSONArray(s)
                    if(jArray.length() == 0){
                        basket_purchase_layout.snackMessage(getString(R.string.message_empty_basket))
                    }else{
                        val jObject = jArray.getJSONObject(0)
                        val products = Klaxon().parseArray<ProductWrapper>(jObject.getJSONArray("products").toString())!!
                        basket_p_recycler.withDivider().bind(products, activity!!, R.layout.listitem_basket)
                            .setCustomBind { element, _ ->
                                val product = element.product
                                li_basket_name.text = product.name
                                li_basket_color.text = colors.getOrDefault(product.color ?: 0,  Color(1, "بی رنگ")).name
                                li_basket_cnt.value = 1
                                li_basket_price.text = "${product.Price} تومان"
                            }.apply()
                        basket_price.text = "${products.fold(0, {acc, it -> acc + it.product.Price })} تومان"
                        basket_purchase.setOnClickListener {
                            NetworkTask(PURCHASE_LINK, NetworkTask.Method.POST,
                                """{}""".jsonRequestBody, activity!!, getString(R.string.message_purchase),
                                "Authorization" to "Token ${settings.getString(AUTH_KEY, "")}")
                                .setOnCallBack { response, s ->
                                    if(response?.code == 200 && s != null){
                                        val trackingCode = JSONObject(s).getInt("tracking_code").toString()
                                        activity?.showFragment2(BasketFragment(products.map { it.product }, trackingCode), R.id.activity_main_frame)
                                    }
                                    else
                                        basket_purchase_layout.snackMessage(getString(R.string.message_purchase_error))
                                }.send()
                        }
                    }
                }
            }.send()
    }
}
