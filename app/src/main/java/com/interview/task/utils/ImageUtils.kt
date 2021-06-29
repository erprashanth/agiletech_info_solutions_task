package com.interview.task.utils

object ImageUtils {
    fun getImageUrlById(farm: Int, id: String,secrete:String) = "http://farm"+farm+".static.flickr.com/578/"+id+"_"+secrete+".jpg"
}