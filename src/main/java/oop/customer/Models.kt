package oop.customer

import oop.customer.api.recyclerview.BindView

data class Product(
    @BindView(id = "productIndex", view = BindView.View.TextView, field = BindView.Field.Text)
    val productIndex: String,
    @BindView(view = BindView.View.TextView, field = BindView.Field.Text)
    val productName: String
)

data class Product2(
    @BindView(id = "textView", view = BindView.View.TextView, field = BindView.Field.Text)
    val name: String,
    @BindView(id = "imageView", view = BindView.View.ImageView, field = BindView.Field.DrawableId)
    val image: Int
)

data class Category(
    val id: Int,
    @BindView(id = "textView", view = BindView.View.TextView, field = BindView.Field.Text)
    val name: String,
    @BindView(id = "imageView", view = BindView.View.ImageView, field = BindView.Field.DrawableId)
    val image: Int = R.drawable.shoe
)

data class SalesmanInfo(
    @BindView(id = "salesmanInfoProperty", view = BindView.View.TextView, field = BindView.Field.Text)
    val salesmanInfoProperty: String,
    @BindView(view = BindView.View.TextView, field = BindView.Field.Text)
    val salesmanInfoValue: String
)
