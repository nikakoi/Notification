
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.annotation.RequiresApi
import com.example.notification.R


class NotificationService : Service() {

    private val CHANNEL_ID = "NotificationServiceChannel"
    private val NOTIFICATION_ID = 1

    private lateinit var handler: Handler

    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())


        createNotificationChannel()


        startNotifying()
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "my channel"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startNotifying() {
        handler.postDelayed(object : Runnable {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                showNotification()
                handler.postDelayed(this, 10* 60 * 1000)
            }
        }, 0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val disableIntent = Intent(this, NotificationService::class.java)
        disableIntent.action = "disable_service"
        val disablePendingIntent = PendingIntent.getService(
            this,
            0,
            disableIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Service Notification")
            .setContentText("Click to disable service")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentIntent(disablePendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && "disable_service" == intent.action) {
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
