package com.treblig.footballmatch.ui.match_event

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.treblig.footballmatch.R
import com.treblig.footballmatch.pojo.Event
import com.treblig.footballmatch.util.DateTime
import org.jetbrains.anko.*

class MatchAdapter(private val listener: (Event) -> Unit) : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    private var eventList: MutableList<Event> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = EventListItemUI().createView(AnkoContext.create(parent.context, parent))
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    fun swapData(events: List<Event>) {
        this.eventList.clear()
        this.eventList.addAll(events)
        notifyDataSetChanged()
    }


    class ViewHolder(val view: View, val listener: (Event) -> Unit) : RecyclerView.ViewHolder(view) {
        private val date: TextView = view.findViewById(R.id.date)
        private val homeTeam: TextView = view.findViewById(R.id.home_team)
        private val homeScore: TextView = view.findViewById(R.id.home_score)
        private val awayTeam: TextView = view.findViewById(R.id.away_team)
        private val awayScore: TextView = view.findViewById(R.id.away_score)

        fun bind(event: Event) {
            date.text = DateTime.convertDate(event.dateEvent!!)
            homeTeam.text = event.strHomeTeam
            homeScore.text = event.intHomeScore
            awayTeam.text = event.strAwayTeam
            awayScore.text = event.intAwayScore

            view.setOnClickListener {
                listener(event)
            }
        }
    }

    class EventListItemUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL

                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.VERTICAL
                    padding = dip(8)

                    textView {
                        id = R.id.date
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        gravity = Gravity.CENTER
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL

                        textView {
                            id = R.id.home_team
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = ctx.getString(R.string.home)
                        }.lparams(matchParent, wrapContent, 1f)

                        linearLayout {
                            gravity = Gravity.CENTER_VERTICAL

                            textView {
                                id = R.id.home_score
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }

                            textView {
                                text = ctx.getString(R.string.vs)
                            }

                            textView {
                                id = R.id.away_score
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null, Typeface.BOLD)
                                text = "0"
                            }
                        }

                        textView {
                            id = R.id.away_team
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = ctx.getString(R.string.away)
                        }.lparams(matchParent, wrapContent, 1f)
                    }
                }.lparams(matchParent, matchParent) {
                    setMargins(dip(16), dip(8), dip(16), dip(8))
                }
            }
        }
    }
}






