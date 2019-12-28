package oop.customer

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_salesman_info.*
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.networktask.jsonRequestBody
import oop.customer.api.recyclerview.bind

class MainActivity : AppCompatActivity() {

    /*
    val testProducts = listOf(Product(".۱", "یخچال سای بای ساید سامسونگ K540"),
        Product(".۲", "مایکروفر من"))
    val testProducts2 = ArrayList<Product>()
    */

    /*
    val allInfo = listOf(
        SalesmanInfo("نام و نام خانوادگی", "رضا رمضانی"),
        SalesmanInfo("شماره تلفن", "۰۹۱۳۸۷۸۲۵۲۸"),
        SalesmanInfo("ایمیل", "ramezani.cs@gmail.com"),
        SalesmanInfo("آدرس", "ساختمان انصاری")
    )
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_main_bottom_navigation.setOnNavigationItemSelectedListener {
            val frag: Fragment? = when(it.itemId){
                R.id.main_bottom_nav_products ->
                    null
                R.id.main_bottom_nav_basket ->
                    null
                R.id.main_bottom_nav_history ->
                    null
                R.id.main_bottom_nav_profile ->
                    null
                else ->
                    null
            }
            frag?.let {
                supportFragmentManager.beginTransaction().replace(R.id.activity_main_frame, frag).commit()
            }
            true
        }
        // salesman_info_recycler.bind(allInfo, this, R.layout.listitem_salesman_info).apply()
        /*
            status_code.setOnClickListener {
                val d = Dialog(this@MainActivity)
                d.setContentView(R.layout.dialog_code)
                d.show()
            }
            for(i in 0..10)
                testProducts2.addAll(testProducts)
            basket_recycler.bind(testProducts2, this, R.layout.listitem_product).apply()
        */

        /*
        val et: EditText? = null
        NetworkTask(LOGIN_LINK, NetworkTask.Method.POST, """{
            "name": "${et?.text}",
        }""".jsonRequestBody, this, "Please wait...").setOnCallBack { response, s ->
            s?.let {
                val p: Person? = Klaxon().parse<Person>(it)

            }
        } .send()
        */
    }

    // data class Person(val name: String)
}
