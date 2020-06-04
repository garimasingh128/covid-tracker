package com.example.covidtracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.covidtracker.RecyclerViewAdapter.RecyclerViewHolder
import java.util.*

class RecyclerViewAdapter(private val context: Context, private val coronaItemArrayList: ArrayList<CoronaItem>) : RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val coronaItem = coronaItemArrayList[position]
        val state = coronaItem.state
        val death = coronaItem.death
        val recovered = coronaItem.recovered
        val active = coronaItem.active
        val confirmed = coronaItem.confirmed
        val lastUpdt = coronaItem.lastUpdated
        val todayDeath = coronaItem.todayDeath
        val todayActive = coronaItem.todayActive
        val todayRecovered = coronaItem.todayRecovered
        holder.state.text = state
        holder.death.text = death
        holder.rec.text = recovered
        holder.active.text = active
        holder.con.text = confirmed
        holder.last.text = lastUpdt
        holder.todayD.text = String.format("(%s)", todayDeath)
        holder.todayA.text = String.format("(%s)", todayActive)
        holder.todayR.text = String.format("(%s)", todayRecovered)
    }

    override fun getItemCount(): Int {
        return coronaItemArrayList.size
    }

    inner class RecyclerViewHolder(itemView: View) : ViewHolder(itemView) {
        var state: TextView
        var death: TextView
        var rec: TextView
        var active: TextView
        var con: TextView
        var last: TextView
        var todayD: TextView
        var todayA: TextView
        var todayR: TextView

        init {
            death = itemView.findViewById(R.id.death)
            state = itemView.findViewById(R.id.stateName)
            rec = itemView.findViewById(R.id.recovered)
            active = itemView.findViewById(R.id.active)
            con = itemView.findViewById(R.id.confirmed)
            last = itemView.findViewById(R.id.lastUpdated)
            todayD = itemView.findViewById(R.id.todayDeath)
            todayA = itemView.findViewById(R.id.todayActive)
            todayR = itemView.findViewById(R.id.todayRecovered)
        }
    }

}