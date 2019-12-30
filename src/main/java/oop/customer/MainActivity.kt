package oop.customer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import oop.customer.api.showFragment
import oop.customer.fragments.ProductCategoriesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Check for login...
        showFragment<ProductCategoriesFragment>(R.id.activity_main_frame)
    }

    var onBackPressedCallback: () -> Unit = { super.onBackPressed() }

    override fun onBackPressed() {
        onBackPressedCallback()
    }

    fun onBackReset() {
        onBackPressedCallback = { super.onBackPressed() }
    }
}