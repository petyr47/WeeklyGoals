package com.inits.ng.weeklygoals.goals


import android.app.ActivityOptions
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.transition.Explode
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.inits.ng.weeklygoals.R
import com.inits.ng.weeklygoals.data.Goal
import com.inits.ng.weeklygoals.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val goalViewModel : GoalViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(bar)

        createChannel(
            getString(R.string.goal_notification_channel_id),
            getString(R.string.goal_notification_channel_id)
        )
        fab.setOnClickListener {
            startActivity(Intent(this, InputActivity::class.java))
        }

        setupObservers()

//        val notifmanager = getSystemService(NotificationManager::class.java) as NotificationManager

//        Handler().postDelayed({
//            notifmanager.sendNotification("We have set up Notification for weekly goals", this)
//        }, 2000)


    }


    private fun setupObservers(){
        goalViewModel.goals?.observe(this, Observer {
            it?.let {
                makeList(it)
            }
        })
    }


    private fun makeList(list: List<Goal>){
        val adapter = GoalAdapter(this, list)
        goal_list.adapter = adapter
        adapter.onItemDeleteClicked = {}
        adapter.onItemEditClicked = {
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }
     }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.menu_current -> {
                makeASnack(item.title.toString())
                true
            }
            R.id.menu_history -> {
                makeASnack(item.title.toString())
                true
            }
            R.id.menu_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun makeASnack(message : String) = Snackbar
            .make(main_root, message, Snackbar.LENGTH_LONG).apply {
        anchorView = if (fab.visibility == View.VISIBLE) fab as View else bar as View
        show()
    }


    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.description = "Notification for Goals"

            val notificationManager = getSystemService(
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
