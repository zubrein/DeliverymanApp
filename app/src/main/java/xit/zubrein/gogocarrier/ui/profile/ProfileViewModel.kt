package xit.zubrein.gogocarrier.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import xit.zubrein.gogocarrier.model.ModelArea
import xit.zubrein.gogocarrier.model.ModelAreaIDList
import xit.zubrein.gogocarrier.ui.profile.listener.UpdateAreaListener
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
@Inject
constructor(private val profileRepository: ProfileRepository) : ViewModel() {

    var updateAreaListener: UpdateAreaListener? = null

    val areaList: LiveData<List<ModelArea>> = profileRepository.getAreaList()
        .catch { e ->
            Log.d("AreaListError", "getAreaList: ${e.message}")
        }.asLiveData()

    val selectedareaList: LiveData<List<ModelArea>> = profileRepository.getSelectedAreaList()
        .catch { e ->
            Log.d("AreaListError", "getAreaList: ${e.message}")
        }.asLiveData()



    fun update_area(model: ModelAreaIDList) {
        val response = profileRepository.update_area(model)
            .catch { e ->
                updateAreaListener?.updateAreaFailed(e.message!!)
            }.asLiveData()

        updateAreaListener?.updateAreaSuccess(response)

    }

}