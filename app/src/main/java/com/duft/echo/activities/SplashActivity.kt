package com.duft.echo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Handler
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.duft.echo.R

class SplashActivity : AppCompatActivity() {
    object Staticated {
        var SPLASH_TIME_OUT = 1000
    }

    var permissionString = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.RECORD_AUDIO
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!haspermissions(this@SplashActivity, *permissionString)) {
            ActivityCompat.requestPermissions(this@SplashActivity, permissionString, 131)


        } else {
            Handler().postDelayed({
                val startAct = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(startAct)
                this.finish()
            }
                , SplashActivity.Staticated.SPLASH_TIME_OUT.toLong())
        }


    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            131 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED
                ) {
                    Handler().postDelayed({
                        val startAct = Intent(this@SplashActivity, MainActivity::class.java)
                        this.startActivity(startAct)
                        this.finish()
                    }
                        , SplashActivity.Staticated.SPLASH_TIME_OUT.toLong())
                } else {
                    Toast.makeText(
                        this,
                        "Please Grant all THE Permission to continue",
                        Toast.LENGTH_SHORT
                    ).show()
                    this.finish()
                }
                return

            }


            else -> {
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                this.finish()
                return
            }
        }
    }

  private fun haspermissions(context: Context, vararg permissions: String): Boolean {
        var hasallpermissions = true
        for (permission in permissions) {
            val res = context.checkCallingOrSelfPermission(permission)
            if (res != PackageManager.PERMISSION_GRANTED) {
                hasallpermissions = false
            }
        }
        return hasallpermissions
    }


}
