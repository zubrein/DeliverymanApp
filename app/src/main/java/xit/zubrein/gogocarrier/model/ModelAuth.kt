package xit.zubrein.gogocarrier.model

data class ModelAuth(
    var code : Int,
    var status_code : Int,
    var message : String,
    var token : String?,
    var otp_token : String?,
    var user : User?
){
    data class User (

        var id : Int,
        var name : String,
        var email : String?,
        var address : String?,
        var contact_no : String,
        var reference_name : String,
        var user_nid_front : String,
        var user_nid_back : String,
        var user_image : String,

    )
}
