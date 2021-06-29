package com.interview.task.webservices

import com.interview.task.Constants
import com.interview.task.dto.PhotosListResponse
import retrofit2.Call


class RequestPopulateHandler {

    fun getPhotos(page:Int,noJsonCallBack : Int,safe_search: Int, text: String): Call<PhotosListResponse>{
        return ApiHandler.endPointService.getPhotosFromServer("flickr.photos.search",
            Constants.NetworkConstants.API_KEY,
            "json",
            noJsonCallBack,
            safe_search,
            page,
            Constants.PaginationConstants.PER_PAGE,
            text)
    }

    companion object {

        private var sRequestPopulateHandler: RequestPopulateHandler? = null

        val instance: RequestPopulateHandler
            get() {
                if (sRequestPopulateHandler == null) {
                    sRequestPopulateHandler = RequestPopulateHandler()
                }
                return sRequestPopulateHandler!!
            }
    }
}
