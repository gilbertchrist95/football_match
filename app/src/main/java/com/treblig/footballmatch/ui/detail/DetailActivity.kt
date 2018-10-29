package com.treblig.footballmatch.ui.detail

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import com.bumptech.glide.Glide
import com.treblig.footballmatch.R
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.pojo.Team
import com.treblig.footballmatch.ui.base.BaseActivity
import com.treblig.footballmatch.ui.match_event.MatchActivity
import com.treblig.footballmatch.util.DateTime
import com.treblig.footballmatch.util.invisible
import com.treblig.footballmatch.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

class DetailActivity : BaseActivity<DetailPresenter>(), DetailView {


    private var optionMenu: Menu? = null
    private lateinit var matchEvent: Event
    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView
    private lateinit var imgHomeBadge: ImageView
    private lateinit var imgAwayBadge: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchEvent = intent.getParcelableExtra<Event>(MatchActivity.EXTRA_EVENT)
        setView(matchEvent)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "${matchEvent.strHomeTeam} vs. ${matchEvent.strAwayTeam}"

        presenter.getTeamDetail(matchEvent.idHomeTeam!!)
        presenter.getTeamDetail(matchEvent.idAwayTeam!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        optionMenu = menu
        presenter.refreshFavorite(matchEvent)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else if (item?.itemId == R.id.add_to_favorite) {
            presenter.changeFavorite(matchEvent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun setView(event: Event) {
        relativeLayout {
            scrollView = scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    textView {
                        gravity = Gravity.CENTER
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        text = DateTime.convertDate(event.dateEvent!!)
                    }

                    linearLayout {
                        padding = dip(16)
                        gravity = Gravity.CENTER

                        textView {
                            textSize = 48f
                            setTypeface(null, Typeface.BOLD)
                            text = event.intHomeScore
                        }

                        textView {
                            padding = dip(16)
                            textSize = 24f
                            text = getString(R.string.vs)
                        }

                        textView {
                            textSize = 48f
                            setTypeface(null, Typeface.BOLD)
                            text = event.intAwayScore
                        }
                    }

                    linearLayout {
                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            imgHomeBadge = imageView {
                            }.lparams {
                                width = dip(100)
                                height = dip(100)
                                gravity = Gravity.CENTER
                            }

                            textView {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 24f
                                setTypeface(null, Typeface.BOLD)
                                text = event.strHomeTeam
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = event.strHomeFormation
                            }
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            orientation = LinearLayout.VERTICAL

                            imgAwayBadge = imageView {
                            }.lparams {
                                width = dip(100)
                                height = dip(100)
                                gravity = Gravity.CENTER
                            }

                            textView {
                                gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 24f
                                setTypeface(null, Typeface.BOLD)
                                text = event.strAwayTeam
                            }

                            textView {
                                gravity = Gravity.CENTER
                                text = event.strAwayFormation
                            }
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    view {
                        backgroundColor = Color.LTGRAY
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(8)
                    }

                    linearLayout {
                        topPadding = dip(8)

                        textView {
                            text = event.strHomeGoalDetails
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = getString(R.string.goals)
                        }

                        textView {
                            gravity = Gravity.END
                            text = event.strAwayGoalDetails
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = event.intHomeShots
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = getString(R.string.shots)
                        }

                        textView {
                            gravity = Gravity.END
                            text = event.intAwayShots
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    view {
                        backgroundColor = Color.LTGRAY
                    }.lparams(matchParent, dip(1)) {
                        topMargin = dip(8)
                    }

                    textView {
                        topPadding = dip(8)
                        gravity = Gravity.CENTER
                        textSize = 18f
                        setTypeface(null, Typeface.BOLD)
                        text = getString(R.string.lineups)
                    }

                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = event.strHomeLineupGoalkeeper
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = getString(R.string.goal_keeper)
                        }

                        textView {
                            gravity = Gravity.END
                            text = event.strAwayLineupGoalkeeper
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = event.strHomeLineupDefense
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = getString(R.string.defense)
                        }

                        textView {
                            gravity = Gravity.END
                            text = event.strAwayLineupDefense
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = event.strHomeLineupMidfield
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = getString(R.string.midfield)
                        }

                        textView {
                            gravity = Gravity.END
                            text = event.strAwayLineupMidfield
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = event.strHomeLineupForward
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = getString(R.string.forward)
                        }

                        textView {
                            gravity = Gravity.END
                            text = event.strAwayLineupForward
                        }.lparams(matchParent, wrapContent, 1f)
                    }

                    linearLayout {
                        topPadding = dip(16)

                        textView {
                            text = event.strHomeLineupSubstitutes
                        }.lparams(matchParent, wrapContent, 1f)

                        textView {
                            leftPadding = dip(8)
                            rightPadding = dip(8)
                            textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                            text = getString(R.string.substitutes)
                        }

                        textView {
                            gravity = Gravity.END
                            text = event.strAwayLineupSubstitutes
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }
            }
            progressBar = progressBar {
                indeterminateDrawable.setColorFilter(
                        ContextCompat.getColor(ctx, R.color.colorPrimary),
                        PorterDuff.Mode.SRC_IN
                )
            }.lparams {
                centerInParent()
            }

        }
    }

    override fun instantiatePresenter(): DetailPresenter {
        return DetailPresenter(this)
    }

    override fun showLoading() {
        scrollView.invisible()
        progressBar.visible()
    }

    override fun hideLoading() {
        scrollView.visible()
        progressBar.invisible()
    }

    override fun showTeamDetail(team: Team) {
        when (team.idTeam) {
            matchEvent.idHomeTeam -> showHomeTeamDetail(team)
            matchEvent.idAwayTeam -> showAwayTeamDetail(team)
        }
    }

    fun showHomeTeamDetail(team: Team) {
        Glide.with(this).load(team.strTeamBadge).into(imgHomeBadge)
    }

    fun showAwayTeamDetail(team: Team) {
        Glide.with(this).load(team.strTeamBadge).into(imgAwayBadge)
    }

    override fun setViewIsFavorites(isFavorite: Boolean, showSnackBar: Boolean) {
        optionMenu?.getItem(0)?.icon = when (isFavorite) {
            true -> ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
            false -> ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
        }
        when {
            showSnackBar -> {
                val message = getString(if (isFavorite) R.string.added_to_favorite else R.string.removed_from_favorite)
                snackbar(this.scrollView, message).show()
            }
        }
    }
}
