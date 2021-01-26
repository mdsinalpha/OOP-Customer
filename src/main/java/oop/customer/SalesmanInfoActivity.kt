package oop.customer

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_salesman_info.*
import oop.customer.api.networktask.NetworkTask

import oop.customer.api.recyclerview.bind
import org.json.JSONObject
import java.util.ArrayList

class SalesmanInfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_salesman_info)
        val id = intent.extras!!.getInt(SALESMAN_ID)
        NetworkTask("$SALESMAN_INFO_LINK$id/", NetworkTask.Method.GET,
            null, this, getString(R.string.message_wait))
            .setOnCallBack { response, s ->
                if(response?.code == 200 && s != null){
                    val allInfo = ArrayList<SalesmanInfo>()
                    val jObject = JSONObject(s)
                    jObject.keys().forEach {
                        val value = jObject.get(it).toString()
                        allInfo.add(SalesmanInfo(it, value))
                    }
                    salesman_info_recycler.bind(allInfo, this, R.layout.listitem_salesman_info).apply()
                }
            }.send()
    }
}