package oop.customer.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.dialog_code.*
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.listitem_product.view.*
import oop.customer.ProductDetail
import oop.customer.R
import oop.customer.api.recyclerview.bind

class BasketFragment(val boughtProducts: List<ProductDetail>, val trackingCode: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_basket, container, false)


    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        basket_recycler.bind(boughtProducts, activity!!, R.layout.listitem_product).
            setCustomBind { _, position ->
                productIndex.text = ".${position + 1}"
            }.apply()
        status_code.setOnClickListener {
            val d = Dialog(activity!!)
            d.setContentView(R.layout.dialog_code)
            d.tracking_code.text = trackingCode
            d.show()
        }
    }
}
