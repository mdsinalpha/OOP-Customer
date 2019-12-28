package oop.customer.api.recyclerview

import org.intellij.lang.annotations.Language

@Target(AnnotationTarget.PROPERTY)
annotation class BindView(val id: String = DefaultID, val view: View, val field: Field) {
    enum class View {
        TextView, ImageView
    }

    enum class Field {
        Visibility, Enabled, URL, Text
    }

    companion object {
        const val DefaultID = ""
    }
}

operator fun BindView.component1() = this.id
operator fun BindView.component2() = this.view
operator fun BindView.component3() = this.field

@Language("kotlin")
private const val example = """
class User(
    @BindView(view = BindView.View.TextView, field = BindView.Field.Text) val id: String,
    @BindView("imageView", BindView.View.ImageView, BindView.Field.URL) val imageUrl: String
)
"""