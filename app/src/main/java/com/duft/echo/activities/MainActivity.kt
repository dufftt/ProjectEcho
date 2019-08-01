package com.duft.echo.activities

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.duft.echo.R
import com.duft.echo.activities.MainActivity.Statified.drawerLayout
import com.duft.echo.adapters.NavigationDrawerAdapter
import com.duft.echo.fragments.MainFragment
import com.duft.echo.fragments.SongPlayingFragment
import java.lang.Exception
import androidx.recyclerview.widget.RecyclerView.LayoutManager as LayoutManager1

class MainActivity : AppCompatActivity() {

    var navigationDrawerIconsList: ArrayList<String> = arrayListOf()
    var images_for_navdrawer = intArrayOf(
        R.drawable.navigation_allsongs,
        R.drawable.navigation_favorites,
        R.drawable.navigation_settings,
        R.drawable.navigation_aboutus
    )
    var trackNotificationBuilder: Notification? = null

    object Statified {

        var drawerLayout: DrawerLayout? = null
        var notificationManager: NotificationManager? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        navigationDrawerIconsList.add("ALL SONGS")
        navigationDrawerIconsList.add("FAVOURITE")
        navigationDrawerIconsList.add("SETTINGS")
        navigationDrawerIconsList.add("ABOUT US")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this@MainActivity,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout?.setDrawerListener(toggle)
        toggle.syncState()
        val mainFragment = MainFragment()
        this.supportFragmentManager
            .beginTransaction()
            .add(R.id.details_fragment, mainFragment, "MainFragment")
            .commit()
        val _navigationAdapter = NavigationDrawerAdapter(navigationDrawerIconsList, images_for_navdrawer, this)
        _navigationAdapter.notifyDataSetChanged()

        var navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter = _navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        val pIntent=PendingIntent.getActivity(this@MainActivity,System.currentTimeMillis().toInt(),intent,0)
        trackNotificationBuilder=Notification.Builder(this)
            .setContentTitle("A track is playing in Background")
            .setSmallIcon(R.drawable.echo_logo)
            .setContentIntent(pIntent)
            .setOngoing(true)
            .setAutoCancel(true)
            .build()
        Statified.notificationManager=getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    override fun onStart() {
        super.onStart()
        try {

            Statified.notificationManager?.cancel(1978)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        try {

            if (SongPlayingFragment.Statified.mediaplayer?.isPlaying as Boolean){
                Statified.notificationManager?.notify(1978,trackNotificationBuilder)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try {

            Statified.notificationManager?.cancel(1978)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}