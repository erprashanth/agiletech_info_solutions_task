package com.interview.task.webservices

import com.interview.task.dto.PhotosListResponse
import retrofit2.Call
import retrofit2.http.*

interface EndPointService {

    @GET("/services/rest")
    fun getPhotosFromServer(
        @Query("method") method : String,
        @Query("api_key") apikey : String,
        @Query("format") format : String,
        @Query("nojsoncallback") noJsonCallBack: Int,
        @Query("safe_search") safe_search : Int,
        @Query("page") page_number : Int,
        @Query("per_page") per_page : Int,
        @Query("text") text : String
    ):Call<PhotosListResponse>

}