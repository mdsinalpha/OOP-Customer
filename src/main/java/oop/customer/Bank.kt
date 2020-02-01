package oop.customer

const val SERVER_LINK = "http://198.143.182.157"

const val REGISTER_LINK = "$SERVER_LINK/rest-auth/registration/"
const val LOGIN_LINK = "$SERVER_LINK/store/login/"
const val EDIT_PRO_LINK = "$SERVER_LINK/rest-auth/password/change/"
const val CATEGORIES_LINK = "$SERVER_LINK/store/categories/"
const val COLORS_LINK = "$SERVER_LINK/store/colors/"
const val PRODUCTS_LINK = "$SERVER_LINK/store/products/"
const val BASKET_LINK = "$SERVER_LINK/store/last_basket/"
const val LAST_BASKET_LINK = "$SERVER_LINK/store/last-basket/"
const val ADD_PRODUCT_TO_BASKET_LINK = "$SERVER_LINK/store/basketproducts/"
const val PURCHASE_LINK = "$SERVER_LINK/store/purchase/"
const val SALESMAN_INFO_LINK = "$SERVER_LINK/store/salesmans/"
const val COMMENTS_LINK = "$SERVER_LINK/store/product-comment/"

const val PREF_KEY = "pref"
const val AUTH_KEY = "token"
const val USERNAME_KEY = "username"
const val EMAIL_KEY = "email"
const val PRODUCT_ID = "id"
const val SALESMAN_ID = "sid"
const val BASKET_EXISTS_KEY = "basket_exists"

enum class HttpResponseStatus(val code: Int) {
    ERROR(400),
    NOT_FOUND(404),
    CREATED(201),
    OK(200)
}
