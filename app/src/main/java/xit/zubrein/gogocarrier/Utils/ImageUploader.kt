package xit.zubrein.gogocarrier.Utils

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import xit.zubrein.gogocarrier.model.ModelUser
import java.io.File

object ImageUploader {

    fun getFilePart(filePath: String, name : String) : MultipartBody.Part{

        val file = File(filePath)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData(
            name,
            file.name,
            requestFile
        )
    }

    fun getDescription(user: ModelUser?) : RequestBody{

        val gson = Gson()
        val userData = gson.toJson(user)
        return RequestBody.create(MultipartBody.FORM, userData)
    }
}