package xit.zubrein.gogocarrier.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModelArea(
    var id: Int,
    var name: String
) : Parcelable
