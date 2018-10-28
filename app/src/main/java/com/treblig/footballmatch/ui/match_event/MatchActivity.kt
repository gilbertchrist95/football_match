package com.treblig.footballmatch.ui.match_event

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.*
import com.treblig.footballmatch.R
import com.treblig.footballmatch.pojo.League
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.ui.base.BaseActivity
import com.treblig.footballmatch.ui.detail.DetailActivity
import com.treblig.footballmatch.util.invisible
import com.treblig.footballmatch.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MatchActivity : BaseActivity<MatchPresenter>(), MatchView {

    private lateinit var spinner: Spinner
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: LinearLayout
    private lateinit var matchAdapter: MatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        matchAdapter = MatchAdapter {
            when {
                !TextUtils.isEmpty(it.idHomeTeam) && !TextUtils.isEmpty(it.idAwayTeam) && !TextUtils.isEmpty(it.dateEvent) ->
                    startActivity<DetailActivity>(EXTRA_EVENT to it)
            }
        }
        setView()

        presenter.onViewCreated()
    }

    override fun onResume() {
        super.onResume()
        if (presenter.matchType == Match.FAVORITE) {
            presenter.showFavorite()
        }
    }

    private fun setView() {
        linearLayout {
            orientation = LinearLayout.VERTICAL

            linearLayout {
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.LTGRAY

                spinner = spinner {
                    padding = dip(16)
                    minimumHeight = dip(80)
                }
            }

            relativeLayout {
                emptyView = linearLayout {
                    orientation = LinearLayout.VERTICAL

                    imageView {
                        setImageResource(R.drawable.ic_empty)
                    }

                    textView {
                        gravity = Gravity.CENTER
                        padding = dip(8)
                        text = getString(R.string.no_data)
                    }
                }.lparams {
                    centerInParent()
                }

                recyclerView = recyclerView {
                    layoutManager = LinearLayoutManager(ctx)
                    adapter = matchAdapter
                }.lparams(matchParent, matchParent) {
                    topOf(R.id.navigation)
                }

                progressBar = progressBar {
                    indeterminateDrawable.setColorFilter(
                            ContextCompat.getColor(ctx, R.color.colorPrimary),
                            PorterDuff.Mode.SRC_IN
                    )
                }.lparams {
                    centerInParent()
                }

                bottomNavigationView {
                    id = R.id.navigation
                    backgroundColor = Color.WHITE

                    menu.apply {
                        add(R.string.prev_match)
                                .setIcon(R.drawable.ic_result)
                                .setOnMenuItemClickListener {
                                    this@MatchActivity.spinner.visible()
                                    val league = spinner.selectedItem as League
                                    presenter.getPrevMatch(league.id)
                                    false
                                }

                        add(R.string.next_match)
                                .setIcon(R.drawable.ic_schedule)
                                .setOnMenuItemClickListener {
                                    this@MatchActivity.spinner.visible()
                                    val league = spinner.selectedItem as League
                                    presenter.getNextMatch(league.id)
                                    false
                                }

                        add(R.string.favorite)
                                .setIcon(R.drawable.ic_favorited)
                                .setOnMenuItemClickListener {
                                    this@MatchActivity.spinner.invisible()
                                    presenter.showFavorite()
                                    false
                                }
                    }
                }.lparams(matchParent, wrapContent) {
                    alignParentBottom()
                }
            }
        }
    }

    override fun instantiatePresenter(): MatchPresenter {
        return MatchPresenter(this)
    }

    override fun showLoading() {
        progressBar.visible()
        recyclerView.invisible()
        emptyView.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showAllLeagues(leagueList: List<League>?) {
        spinner.adapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagueList)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val league = spinner.selectedItem as League

                when (presenter.matchType) {
                    Match.PREV -> presenter.getPrevMatch(league.id)
                    Match.NEXT -> presenter.getNextMatch(league.id)
                }
            }
        }
    }

    override fun showEvents(eventList: List<Event>) {
        matchAdapter.swapData(eventList)
        emptyView.invisible()
        recyclerView.visible()
    }

    override fun showNoDataAvailable() {
        recyclerView.invisible()
        emptyView.visible()
    }

    override fun getSelectedLeague(): League {
        return spinner.selectedItem as League
    }

    companion object {
        const val EXTRA_EVENT = "extra_event"
    }

}
