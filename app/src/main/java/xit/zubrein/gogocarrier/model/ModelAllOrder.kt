package xit.zubrein.gogocarrier.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelAllOrder(
    var date: String,
    var order: List<Order>
) : Parcelable {
    @Parcelize
    data class Order(

        var id: Int,
        var order_no: String,
        var user_id: Int,
        var address_id: Int,
        var courier_man: Int,
        var status: String,
        var date: String,
        var order_total: Int,
        var user_details: User_details,
        var delivery_address: String,
        var delivery_contact_no: String,
        var product: List<Product>
    ) : Parcelable {

        @Parcelize
        data class Product(

            var id: Int,
            var category_id: Int,
            var sub_category_id: Int,
            var brand_id: String?,
            var name: String,
            var price: Int,
            var description: String?,
            var thumbnail_image: String,
            var status: Int,
            var created_at: String,
            var updated_at: String,
            var unit_quantity: String,
            var warehouse: List<Warehouse>,
            var count: Int
        ) : Parcelable {
            @Parcelize
            data class Warehouse(
                var id: Int,
                var area_id: Int,
                var name: String,
                var address: String,
                var contact_no: String,
                var status: Int,
            ) : Parcelable
        }

        @Parcelize
        data class User_details(
            var id: Int,
            var name: String,
            var email: String?,
            var email_verified_at: String?,
            var contact_no: String,
            var status: Int,
            var role: String?,
            var created_at: String,
            var updated_at: String
        ) : Parcelable
    }
}
