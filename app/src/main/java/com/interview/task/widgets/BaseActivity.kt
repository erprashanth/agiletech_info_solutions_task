package com.interview.task.widgets

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity() {


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
        if (!isFinishing) {
            CustomProgressbar.getProgressbar(this@BaseActivity, cancellable).show()
        }
    }

    fun showProgressbar() {
        if (!isFinishing) {
            CustomProgressbar.getProgressbar(this@BaseActivity, true).show()
        }
    }

    fun dismissProgressbar() {
        if (!isFinishing) {
            CustomProgressbar.getProgressbar(this@BaseActivity, false).dismiss()
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(this@BaseActivity, message, Toast.LENGTH_LONG).show()
    }

    fun showMessage(message: Int) {
        Toast.makeText(this@BaseActivity, resources.getString(message), Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val fragments = this.supportFragmentManager.fragments
        val var5 = fragments.iterator()

        while (var5.hasNext()) {
            (var5.next() as Fragment).onActivityResult(requestCode, resultCode, data)
        }

    }

}