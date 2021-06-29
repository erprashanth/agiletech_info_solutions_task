package com.interview.task.module.main

import androidx.lifecycle.MutableLiveData
import com.interview.task.Constants
import com.interview.task.R
import com.interview.task.ThisApplication
import com.interview.task.dto.Photo
import com.interview.task.dto.PhotosListResponse
import com.interview.task.webservices.RequestPopulateHandler
import com.interview.task.widgets.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel:BaseViewModel() {

    var photosList : MutableLiveData<List<Photo>> = MutableLiveData()
    var totalPage : MutableLiveData<Int> = MutableLiveData()
    var alertMessage : MutableLiveData<Int> = MutableLiveData()
    var isDataLoading : MutableLiveData<Boolean> = MutableLiveData()

    fun call(pageNumber : Int, text : String){

        if(ThisApplication.instance!!.hasNetworkConnection()){
            isDataLoading.postValue(true)

            val call: Call<PhotosListResponse> = RequestPopulateHandler.instance.getPhotos(pageNumber,1,1,text)

            call.enqueue(object : Callback<PhotosListResponse?> {
                override fun onResponse(call: Call<PhotosListResponse?>?, response: Response<PhotosListResponse?>) {

                    val resource: PhotosListResponse = response.body() as PhotosListResponse

                    if(resource.stat == Constants.NetworkConstants.RESULT_SUCCESS){
                        photosList.postValue(resource.photos.photo)
                        totalPage.postValue(resource.photos.pages)
                    }else{
                        alertMessage.postValue(R.string.request_failed)
                    }
                    isDataLoading.postValue(false)
                }

                override fun onFailure(call: Call<PhotosListResponse?>, t: Throwable?) {
                    call.cancel()
                    isDataLoading.postValue(false)
                }
            })
        }else{
            alertMessage.postValue(R.string.no_internet_connection)
        }

    }

}