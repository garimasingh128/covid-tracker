package com.example.covidtracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.covidtracker.MainActivity
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var coronaItemArrayList: ArrayList<CoronaItem>? = null
    private val requestQueue: RequestQueue? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private var dailyDeaths: TextView? = null
    private var dailyConfirm: TextView? = null
    private var dailyRecovered: TextView? = null
    private var dailyHeaders: TextView? = null
    private var totalDeath: TextView? = null
    private var totalConfirm: TextView? = null
    private var totalRecovered: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dailyConfirm = findViewById(R.id.dailyConfirm)
        dailyDeaths = findViewById(R.id.dailyDeath)
        dailyRecovered = findViewById(R.id.dailyRecovered)
        dailyHeaders = findViewById(R.id.dateHeader)
        totalRecovered = findViewById(R.id.totalRecovered)
        totalConfirm = findViewById(R.id.totalConfirm)
        totalDeath = findViewById(R.id.totalDeath)
        recyclerView = findViewById(R.id.myRecyclerView)

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.setLayoutManager(llm)
        recyclerView!!.setHasFixedSize(true)
        coronaItemArrayList = ArrayList()
        // requestQueue=  Volley.newRequestQueue(this);
        Log.i("hello", "hi1")
        jsonParse()
    }

    private fun jsonParse() {
        Log.i("hello", "hi2")
        val url = "https://api.covid19india.org/data.json"
        Log.i("hello", "hi3")
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
            Log.i("hello", "hi4")
            try {
                Log.i("hello", "hi5")
                val todayAndTotalDataArray = response.getJSONArray("statewise")
                Log.i("hello", "hi7")
                val todayAndTotalDataJsonObject = todayAndTotalDataArray.getJSONObject(0)
                val dailyConfirmed = todayAndTotalDataJsonObject.getString("deltaconfirmed")
                val dailyDeath = todayAndTotalDataJsonObject.getString("deltadeaths")
                val dailyRec = todayAndTotalDataJsonObject.getString("deltarecovered")
                var dataHeader: String? = todayAndTotalDataJsonObject.getString("lastupdatedtime").substring(0, 5)
                dataHeader = getFormattedDate(dataHeader)
                Log.i("hello", "hi6")
                dailyConfirm!!.text = dailyConfirmed
                dailyRecovered!!.text = dailyRec
                dailyDeaths!!.text = dailyDeath
                dailyHeaders!!.text = dataHeader
                Log.i("hello", "hi6")
                val totalDeathsFetched = todayAndTotalDataJsonObject.getString("deaths")
                val totalRecoveredFetched = todayAndTotalDataJsonObject.getString("recovered")
                val totalConfirmedFetched = todayAndTotalDataJsonObject.getString("confirmed")
                totalConfirm!!.text = totalConfirmedFetched
                totalDeath!!.text = totalDeathsFetched
                totalRecovered!!.text = totalRecoveredFetched
                for (i in 1 until todayAndTotalDataArray.length()) {
                    Log.i("hello", "hi7")
                    val stateWiseArrayJSONObject = todayAndTotalDataArray.getJSONObject(i)
                    val active = stateWiseArrayJSONObject.getString("active")
                    val death = stateWiseArrayJSONObject.getString("deaths")
                    val recovered = stateWiseArrayJSONObject.getString("recovered")
                    val state = stateWiseArrayJSONObject.getString("state")
                    val confirmed = stateWiseArrayJSONObject.getString("confirmed")
                    val lastUpdated = stateWiseArrayJSONObject.getString("lastupdatedtime")
                    val todayActive = stateWiseArrayJSONObject.getString("deltaconfirmed")
                    val todayDeath = stateWiseArrayJSONObject.getString("deltadeaths")
                    val todayRecovered = stateWiseArrayJSONObject.getString("deltarecovered")
                    val coronaItem = CoronaItem(state, death, active, recovered, confirmed, lastUpdated
                            , todayDeath, todayRecovered, todayActive)
                    coronaItemArrayList!!.add(coronaItem)
                }
                Log.d("list", coronaItemArrayList.toString())
                recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity, coronaItemArrayList!!)
                recyclerView!!.adapter = recyclerViewAdapter
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { error ->
            Log.i("hello", "hi8")
            Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
        })
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }

    private fun getFormattedDate(dateHeader: String?): String? {
        Log.i("hello", "hi6")
        return when (dateHeader!!.substring(3, 5)) {
            "01" -> dateHeader.substring(0, 2) + " Jan"
            "02" -> dateHeader.substring(0, 2) + " Feb"
            "03" -> dateHeader.substring(0, 2) + " Mar"
            "04" -> dateHeader.substring(0, 2) + " Apr"
            "05" -> dateHeader.substring(0, 2) + " May"
            "06" -> dateHeader.substring(0, 2) + " June"
            "07" -> dateHeader.substring(0, 2) + " July"
            "08" -> dateHeader.substring(0, 2) + " Aug"
            "09" -> dateHeader.substring(0, 2) + " Sept"
            "10" -> dateHeader.substring(0, 2) + " Oct"
            "11" -> dateHeader.substring(0, 2) + " Nov"
            "12" -> dateHeader.substring(0, 2) + " Dec"
            else -> null
        }
    }

    companion object {
        private val instance: MainActivity? = null
        private val ctx: Context? = null
    }
}