package oop.customer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import com.beust.klaxon.Klaxon
import kotlinx.android.synthetic.main.activity_main.*
import oop.customer.api.networktask.NetworkTask
import oop.customer.api.showFragment
import oop.customer.api.showFragment2
import oop.customer.fragments.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var settings: SharedPreferences

    private var colors = mutableMapOf<Int, Color>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        settings = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
        showFragment<ProductCategoriesFragment>(R.id.activity_main_frame)
        activity_main_bottom_navigation.setOnNavigationItemSelectedListener {
            val frag: Fragment? = when(it.itemId){
                R.id.main_bottom_nav_products ->
                    ProductCategoriesFragment()
                R.id.main_bottom_nav_basket ->
                    BasketPurchaseFragment(colors)
                R.id.main_bottom_nav_profile ->
                    ProfileFragment()
                else ->
                    null
            }
            frag?.let {
                showFragment2(frag, R.id.activity_main_frame)
            }
            true
        }
        NetworkTask(BASKET_LINK, NetworkTask.Method.GET, null, null, null,
            "Authorization" to "Token ${settings.getString(AUTH_KEY, "")}")
            .setOnCallBack { response, s ->
                if(response?.code == 200 && s != null){
                    val jArray = JSONArray(s)
                    settings.edit().putBoolean(BASKET_EXISTS_KEY, jArray.length() != 0).apply()
                }
            }.send()
        // Fetching colors:
        NetworkTask(COLORS_LINK, NetworkTask.Method.GET, null, null, null)
            .setOnCallBack { response, s ->
                if(response?.code == 200 && s != null){
                    val colorsList = Klaxon().parseArray<Color>(s)
                    colorsList?.let {
                        colors.clear()
                        it.forEach { color ->
                            colors[color.id] = color
                        }
                    }
                }
            }.send()

    }

    var onBackPressedCallback: () -> Unit = { super.onBackPressed() }

    override fun onBackPressed() {
        onBackPressedCallback()
    }

    fun onBackReset() {
        onBackPressedCallback = { super.onBackPressed() }
    }
}


