package oop.customer.data

import oop.customer.api.recyclerview.BindView

data class Category(
    @BindView(id = "textView", view = BindView.View.TextView, field = BindView.Field.Text)
    val name: String,
    @BindView(id = "imageView", view = BindView.View.ImageView, field = BindView.Field.DrawableId)
    val image: Int
)