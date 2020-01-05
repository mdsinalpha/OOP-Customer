package oop.customer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_salesman_info.*
import oop.customer.R
import oop.customer.SalesmanInfo
import oop.customer.api.recyclerview.bind

class SalesmanInfoFragment : Fragment() {

    val allInfo = listOf(
        SalesmanInfo("نام و نام خانوادگی", "رضا رمضانی"),
        SalesmanInfo("شماره تلفن", "۰۹۱۳۸۷۸۲۵۲۸"),
        SalesmanInfo("ایمیل", "ramezani.cs@gmail.com"),
        SalesmanInfo("آدرس", "ساختمان انصاری")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_salesman_info, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        salesman_info_recycler.bind(allInfo, activity!!, R.layout.listitem_salesman_info).apply()
    }
}