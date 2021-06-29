package com.interview.task.module.main

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.interview.task.Constants
import com.interview.task.R
import com.interview.task.databinding.ActivityMainBinding
import com.interview.task.dto.Photo
import com.interview.task.widgets.BaseActivity


class MainActivity : BaseActivity<MainViewModel>(),ImagesAdapter.OnPhotoClickListener {

    private var binding : ActivityMainBinding?= null
    private var photosAdapter : ImagesAdapter ?= null
    private var searchText = Constants.SearchConstants.DEFAULT_SEARCH
    private var totalPageCount = 2
    private var isDataLoading = false

    var currentPage = Constants.PaginationConstants.START_PAGE

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding?.viewModel = viewModel
        setUpObservable()
        initRecyclerView()
        setSearchKeyword()

        binding?.searchVw?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query.isNullOrEmpty()){
                    showMessage(R.string.invalid_search_query)
                }else{
                    currentPage = Constants.PaginationConstants.START_PAGE
                    searchText = query.toString()
                    setSearchKeyword()
                    viewModel.call(currentPage,searchText)
                    photosAdapter?.updatePhotoList(ArrayList())
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return true
            }
        })

        binding?.searchVw?.setOnCloseListener {
            binding?.toolbarTitle?.visibility = View.VISIBLE
            false
        }

        binding?.searchVw?.setOnSearchClickListener {
            binding?.toolbarTitle?.visibility = View.GONE
        }

        viewModel.call(currentPage,searchText)
    }


    private fun setSearchKeyword(){
        binding?.searchKey?.text = getString(R.string.search_result,searchText)
    }

    private fun initRecyclerView(){

        val layoutManager = GridLayoutManager(this, 3)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        photosAdapter = ImagesAdapter(this)
        binding?.imagesRecyclerView?.setHasFixedSize(true)
        binding?.imagesRecyclerView?.layoutManager = LinearLayoutManager(this)
        binding?.imagesRecyclerView?.layoutManager = layoutManager
        binding?.imagesRecyclerView?.adapter = photosAdapter

        binding?.imagesRecyclerView?.addOnScrollListener(object : PaginationListener(layoutManager,totalPageCount) {

            override fun loadMoreItems() {
                currentPage++
                viewModel.call(currentPage,searchText)
            }

            override val isLastPage: Boolean = (currentPage==totalPageCount)
            override val isLoading: Boolean = isDataLoading
        })
    }


    private fun setUpObservable(){
        viewModel.photosList.observe(this, Observer {
            photosAdapter?.addPhotoList(it)
        })

        viewModel.totalPage.observe(this, Observer {
            totalPageCount = it
        })

        viewModel.isDataLoading.observe(this, Observer {
            isDataLoading = it
            if(it){
                binding?.loadingProgressBar?.visibility = View.VISIBLE
            }else{
                binding?.loadingProgressBar?.visibility = View.GONE
            }
        })

    }





    override fun getViewModelClass()= MainViewModel::class.java

    override fun photoClicked(photo: Photo) {
        Toast.makeText(this,photo.title,Toast.LENGTH_SHORT).show()
    }
}