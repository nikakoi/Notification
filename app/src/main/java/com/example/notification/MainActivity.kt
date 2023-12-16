package com.example.notification

import NotificationService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startServiceButton: View = findViewById(R.id.btnStartService)
        startServiceButton.setOnClickListener {
            val serviceIntent = Intent(this, NotificationService::class.java)
            startService(serviceIntent)
        }

    }
}