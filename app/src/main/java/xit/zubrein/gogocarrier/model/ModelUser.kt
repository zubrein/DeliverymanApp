package xit.zubrein.gogocarrier.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelUser(
    var name:String,
    var msisdn:String,
    var password:String,
    var reference_name:String,
    var email:String,
    var address:String
) : Parcelable
