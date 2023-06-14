package com.ubaya.adv160419137week4.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ubaya.adv160419137week4.R
import com.ubaya.adv160419137week4.util.createNotificationChannel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    init {
        instance = this
    }

    companion object{
        var instance:MainActivity ?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(this,NotificationManagerCompat.IMPORTANCE_DEFAULT,false,getString(R.string.app_name),"App Student Data")


        val observable = Observable.just("Hello","Text","1","2","3")

        //test
        val observer = object : Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d("observermessage","Begin Subscribe")
            }

            override fun onError(e: Throwable) {
                Log.e("observermessage","Error: ${e.message.toString()}")
            }

            override fun onComplete() {
                Log.d("observermessage","Complete")
            }

            override fun onNext(t: String) {
                Log.d("observermessage", "Data: ${t.toString()}")
            }

        }

        observable.apply {
            subscribeOn(Schedulers.io())
            observeOn(AndroidSchedulers.mainThread())
            subscribe(observer)}
    }
}