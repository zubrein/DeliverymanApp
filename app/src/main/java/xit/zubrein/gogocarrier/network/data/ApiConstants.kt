package xit.zubrein.gogocarrier.network.data

object ApiConstants {

    const val BASE_URL = "https://www.gogoshopbd.com/api/"

    //Auth
    const val LOGIN = "delivery_man_login"
    const val LOGOUT = "logout"
    const val SEND_TOKEN = "token_insertion"
    const val REGISTRATION = "delivery_man_registration"
    const val SENT_OTP = "login_delivery_man"
    const val SUBMIT_OTP = "submit_otp"
    const val WALLET = "wallet"

    //Order
    const val All_ORDER_LIST = "all_order"
    const val TODAYS_ORDER = "todays_order"
    const val PICKED_PRODUCT_FROM_WAREHOUSE = "order_picked"
    const val REMARKS = "remarks"

    //Statistics
    const val DASHBOARD = "delivery_man_dashboard"

    //Area
    const val AREA = "get_area_delivery_man"
    const val UPDATE_AREA = "update_area"
    const val GET_SELECTED_AREA = "get_selected_area"

}