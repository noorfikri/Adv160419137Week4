package com.ubaya.adv160419137week4.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubaya.adv160419137week4.model.Student

class DetailViewModel(application: Application):AndroidViewModel(application) {
    val studentLD = MutableLiveData<Student>()
    val TAG = "volleyDetailTAG"
    private var queue:RequestQueue? = null

    fun fetch(studentId:String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://adv.jitusolution.com/student.php?id="+studentId

        val stringRequest = StringRequest(
            Request.Method.GET,url,{
                val sType = object : TypeToken<Student>(){}.type
                val result = Gson().fromJson<Student>(it,sType)

                studentLD.value = result
                Log.d("detailvolley",result.toString())
            },{
                Log.d("detailvolley",it.toString())
            }
        )

        stringRequest.tag = TAG
        queue?.add(stringRequest)

    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}