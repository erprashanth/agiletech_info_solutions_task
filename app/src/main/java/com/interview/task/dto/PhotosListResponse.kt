package com.interview.task.dto

import com.google.gson.annotations.SerializedName

   
data class PhotosListResponse (
   @SerializedName("photos") var photos : Photos,
   @SerializedName("stat") var stat : String
)