package com.inits.ng.weeklygoals.goals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.inits.ng.weeklygoals.R
import com.inits.ng.weeklygoals.data.Goal
import kotlinx.android.synthetic.main.layout_goal_item.view.*

class GoalAdapter(val context: Context, val list : List<Goal>) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    var onItemEditClicked : ((Int) -> Unit)? = null

    var onItemDeleteClicked : ((Goal) ->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder = GoalViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.layout_goal_item, parent, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) = holder.bindGoal(list[position])


    inner class GoalViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindGoal(goal: Goal){
            itemView.item_title.text = goal.title
            itemView.item_message.text = goal.message
            itemView.imageView.setOnClickListener { onItemEditClicked?.invoke(goal.id) }
            itemView.item_time_range.text = goal.week.dateString
        }

    }
}