package oop.customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import oop.customer.api.showFragment
import oop.customer.api.showFragment2
import oop.customer.fragments.BasketFragment
import oop.customer.fragments.ProductCategoriesFragment
import oop.customer.fragments.ProfileFragment
import oop.customer.fragments.SalesmanInfoFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment<ProductCategoriesFragment>(R.id.activity_main_frame)
        activity_main_bottom_navigation.setOnNavigationItemSelectedListener {
            val frag: Fragment? = when(it.itemId){
                R.id.main_bottom_nav_products ->
                    ProductCategoriesFragment()
                R.id.main_bottom_nav_basket ->
                    BasketFragment()
                R.id.main_bottom_nav_history ->
                    SalesmanInfoFragment()
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
    }

    var onBackPressedCallback: () -> Unit = { super.onBackPressed() }

    override fun onBackPressed() {
        onBackPressedCallback()
    }

    fun onBackReset() {
        onBackPressedCallback = { super.onBackPressed() }
    }
}
