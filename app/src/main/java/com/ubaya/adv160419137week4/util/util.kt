package com.ubaya.adv160419137week4.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.adv160419137week4.R
import com.ubaya.adv160419137week4.view.MainActivity


fun ImageView.loadImage(url: String?, progressBar:ProgressBar) {
    Picasso.get()
        .load(url)
        .resize(400, 400)
        .centerCrop()
        .error(R.drawable.baseline_error_24)
        .into(this, object:Callback{
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }
            override fun onError(e: Exception?) {
            }
        })
}

fun showNotification(title:String, content:String, icon:Int){
    val channelId = "${MainActivity.instance?.packageName}-${MainActivity.instance?.getString(R.string.app_name)}"

    val builder = NotificationCompat.Builder(MainActivity.instance!!.applicationContext,channelId).apply {
        setSmallIcon(icon)
        setContentTitle(title)
        setContentText(content)
        setStyle(NotificationCompat.BigTextStyle())
        priority = NotificationCompat.PRIORITY_DEFAULT
        setAutoCancel(true)
    }

    val manager = NotificationManagerCompat.from(MainActivity.instance!!.applicationContext)

    if (ActivityCompat.checkSelfPermission(
            MainActivity.instance!!.applicationContext,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {

        return
    }
    manager.notify(1001,builder.build())
}

fun createNotificationChannel(context:Context, importance:Int, showBadge:Boolean, name:String, description:String){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channelId = "${context.packageName}-${name}"
        val channel = NotificationChannel(channelId,name,importance)

        channel.description = description
        channel.setShowBadge(showBadge)

        val manager= context.getSystemService(NotificationManager::class.java)

        manager.createNotificationChannel(channel)
    }
}
