package com.treblig.footballmatch.ui.detail

import com.treblig.footballmatch.pojo.Team
import com.treblig.footballmatch.ui.base.BaseView

interface DetailView : BaseView {
    fun showTeamDetail(team: Team)
    fun setViewIsFavorites(isFavorited: Boolean, showSnackBar: Boolean = false)
}