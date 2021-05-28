package xit.zubrein.gogocarrier.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelAreaList(
    @SerializedName("area_id")
    var area_id:List<ModelArea>

) : Parcelable
