package com.example.tapjacker

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Button to start the tapjacker service
        val startButton: Button = findViewById(R.id.start_button)
        startButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    startActivityForResult(intent, 1234)
                } else {
                    startTapjackerService()
                }
            } else {
                startTapjackerService()
            }
        }

        // Button to stop the tapjacker service
        val stopButton: Button = findViewById(R.id.stop_button)
        stopButton.setOnClickListener {
            stopService(Intent(this, TapjackerService::class.java))
        }
    }

    private fun startTapjackerService() {
        val serviceIntent = Intent(this, TapjackerService::class.java)
        startService(serviceIntent)
    }
}
