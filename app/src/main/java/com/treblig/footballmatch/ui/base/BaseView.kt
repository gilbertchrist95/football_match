package com.treblig.footballmatch.ui.base

import android.content.Context
import com.treblig.footballmatch.util.ErrorUtil

interface BaseView {
    fun getContext(): Context

    fun showLoading()

    fun hideLoading()

    fun showError(title: String, message: String)

    fun showError(throwable: Throwable) {
        try {
            this.showError(ErrorUtil.getTitleError(getContext(), throwable),
                    ErrorUtil.getMessageError(getContext(), throwable))
        } catch (ex: Exception) {
        }
    }

}






