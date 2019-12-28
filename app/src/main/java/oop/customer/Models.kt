package oop.customer

import oop.customer.api.recyclerview.BindView

data class Product(
    @BindView(view = BindView.View.TextView,
    field = BindView.Field.Text, id = "productIndex")
                   val productIndex: String,
    @BindView(view = BindView.View.TextView,
    field = BindView.Field.Text)
                    val productName: String)

data class SalesmanInfo(
    @BindView(view = BindView.View.TextView,
        field = BindView.Field.Text, id = "salesmanInfoProperty")
    val salesmanInfoProperty: String,
    @BindView(view = BindView.View.TextView,
        field = BindView.Field.Text)
    val salesmanInfoValue: String)
