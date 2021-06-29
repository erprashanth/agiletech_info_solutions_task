package com.interview.task

interface Constants {


    interface PaginationConstants {
        companion object {
            const val START_PAGE = 1
            const val PER_PAGE = 10
        }
    }

    interface SearchConstants{
        companion object{
            const val DEFAULT_SEARCH = "kittens"
        }
    }

    interface NetworkConstants{
        companion object{
            const val BASE_URL = "https://api.flickr.com/"
            const val API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736"
            const val RESULT_SUCCESS = "ok"
        }
    }

}
