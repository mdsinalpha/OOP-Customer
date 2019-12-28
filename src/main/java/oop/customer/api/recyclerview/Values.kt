package oop.customer.api.recyclerview

import android.view.View
import org.intellij.lang.annotations.Language

interface VisibilityBind {

    enum class State(val constant: Int) {
        Visible(View.VISIBLE), Invisible(View.INVISIBLE), Gone(View.GONE)
    }

    fun getVisibilityState(id: String, value: Any?): State

}

//Example Usage
@Language("kotlin")
private const val example =
    """
class User(
    @BindView(view = BindView.View.TextView, field = BindView.Field.Visibility) val name: String,
    @BindView(id = "imageView", view = BindView.View.TextView, field = BindView.Field.Visibility) val imageSrc: String
) : VisibilityBind() {

    override fun getVisibilityState(id: String, value: Any?) = 
        when(id) {
            "name" -> {
                value as String
                if (value.startsWith("Mr")) VisibilityBind.State.Visible else VisibilityBind.State.Gone
            }
            "imageView" -> {
                value as String
                VisibilityBind.State.Invisible
            }
            else -> VisibilityBind.State.Gone
        }
}
"""