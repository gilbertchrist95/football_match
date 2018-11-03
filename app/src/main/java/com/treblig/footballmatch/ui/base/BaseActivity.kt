package com.treblig.footballmatch.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog

abstract class BaseActivity<P : BasePresenter<BaseView>> : AppCompatActivity(), BaseView {
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
        presenter.inject()
    }

    protected abstract fun instantiatePresenter(): P

    override fun getContext(): Context {
        return this
    }

    override fun showError(title: String, message: String) {
        try {
            MaterialDialog.Builder(getContext())
                    .title(title)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .build()
                    .show()
        } catch (ex: Exception) {
        }
    }
}






