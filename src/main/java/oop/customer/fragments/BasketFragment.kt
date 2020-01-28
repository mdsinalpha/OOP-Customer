package oop.customer.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_basket.*
import oop.customer.Product
import oop.customer.R
import oop.customer.api.recyclerview.bind

class BasketFragment : Fragment() {

    val testProducts = listOf (
        Product(".۱", "یخچال سای بای ساید سامسونگ K540"),
        Product(".۲", "مایکروفر من"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_basket, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        basket_recycler.bind(testProducts, activity!!, R.layout.listitem_product).apply()
        status_code.setOnClickListener {
            val d = Dialog(activity!!)
            d.setContentView(R.layout.dialog_code)
            d.show()
        }
    }
}
