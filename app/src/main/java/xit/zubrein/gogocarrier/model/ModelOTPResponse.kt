package xit.zubrein.gogocarrier.model

data class ModelOTPResponse(
    var status_code:Int,
    var user_status:String,
    var token:String,
    var error_msg:String
)
