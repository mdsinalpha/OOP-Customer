package oop.customer

import oop.customer.api.recyclerview.BindView


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
    @BindView(id = "salesmanInfoValue", view = BindView.View.TextView, field = BindView.Field.Text)
    val salesmanInfoValue: String
)

data class ProductDetail(
    @BindView(id = "productIndex", view = BindView.View.TextView, field = BindView.Field.Text)
    val id: Int,
    @BindView(id = "productName", view = BindView.View.TextView, field = BindView.Field.Text)
    val name: String,
    val count: Int,
    val description: String?,
    val isStock: Boolean,
    val Price: Int,
    val recordTime: String,
    val salesman: Int,
    val color: Int?,
    val category: Int?
)

data class AbstractProductDetail(
    val id: Int,
    @BindView(id = "textView", view = BindView.View.TextView, field = BindView.Field.Text)
    val name: String,
    @BindView(id = "imageView", view = BindView.View.ImageView, field = BindView.Field.DrawableId)
    val image: Int = R.drawable.shoe
)

fun ProductDetail.abstract() = AbstractProductDetail(this.id, this.name)

data class Image(
    val id: Int,
    val imageContent: String,
    val product: Int
)

data class Comment(
    val id: Int,
    val text: String,
    val product: Int,
    val customer: Int
)

