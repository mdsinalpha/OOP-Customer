package oop.customer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_basket_purchase.*
import oop.customer.R
import oop.customer.api.recyclerview.bind
import oop.customer.api.withDivider

class BasketPurchaseFragment: Fragment(){

    data class Fake(val str: String)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.fragment_basket_purchase, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        basket_p_recycler.withDivider().bind(listOf(Fake("")), activity!!, R.layout.listitem_basket).apply()
    }

}