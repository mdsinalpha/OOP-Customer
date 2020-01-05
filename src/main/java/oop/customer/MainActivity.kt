package oop.customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import oop.customer.Fragments.ProfileFragment

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
        // Check for login...
        // showFragment<ProductCategoriesFragment>(R.id.activity_main_frame)

        activity_main_bottom_navigation.setOnNavigationItemSelectedListener {
            val frag: Fragment? = when(it.itemId){
                R.id.main_bottom_nav_products ->
                    null
                R.id.main_bottom_nav_basket ->
                    null
                R.id.main_bottom_nav_history ->
                    null
                R.id.main_bottom_nav_profile ->
                    ProfileFragment()
                else ->
                    null
            }
            frag?.let {
                supportFragmentManager.beginTransaction().replace(R.id.activity_main_frame, frag).commit()
            }
            true
        }


    }

    var onBackPressedCallback: () -> Unit = { super.onBackPressed() }

    override fun onBackPressed() {
        onBackPressedCallback()
    }

    fun onBackReset() {
        onBackPressedCallback = { super.onBackPressed() }
    }
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

    // data class Person(val name: String)

