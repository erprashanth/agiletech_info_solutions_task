package com.interview.task.widgets

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<V : BaseViewModel> : Fragment() {


    lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
        setupObservable()
    }

    abstract fun getViewModelClass(): Class<V>

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(getViewModelClass())
    }

    private fun setupObservable() {
        viewModel.isLoading.observe(this, Observer {
            if (it) showProgressbar(false) else dismissProgressbar()
        })

        viewModel.showMessage.observe(this, Observer {

            showMessage(it)

        })

    }

    fun showProgressbar(cancellable: Boolean) {
        if (activity != null && !activity!!.isFinishing && context != null) {
            CustomProgressbar.getProgressbar(context!!, cancellable).show()
        }
    }


    fun dismissProgressbar() {
        if (activity != null && !activity!!.isFinishing && context != null) {
            CustomProgressbar.getProgressbar(context!!, false).dismiss()
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showMessage(message: Int) {
        Toast.makeText(context, resources.getString(message), Toast.LENGTH_LONG).show()
    }

}
