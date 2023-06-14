package com.ubaya.adv160419137week4.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.ubaya.adv160419137week4.R
import com.ubaya.adv160419137week4.databinding.FragmentStudentDetailBinding
import com.ubaya.adv160419137week4.util.loadImage
import com.ubaya.adv160419137week4.util.showNotification
import com.ubaya.adv160419137week4.viewmodel.DetailViewModel
import com.ubaya.adv160419137week4.viewmodel.ListViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentDetailFragment : Fragment(), ButtonNotificationClickListener,ButtonUpdateClickListener {
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding:FragmentStudentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_student_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var studentId = ""


        if(arguments != null){
            studentId = StudentDetailFragmentArgs.fromBundle(requireArguments()).studentId
        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(studentId)



        observeViewModel(view)
    }

    fun observeViewModel(view: View){
        viewModel.studentLD.observe(viewLifecycleOwner, Observer {
            dataBinding.student = it
            dataBinding.notificationListener = this
        })
    }

    override fun onNotificationClick(v: View) {
        val btnNotif = view?.findViewById<Button>(R.id.btnNotif)

        btnNotif?.setOnClickListener {
            val observable = Observable.timer(5, TimeUnit.SECONDS).apply {
                subscribeOn(Schedulers.io())
                observeOn(AndroidSchedulers.mainThread())
                subscribe {
                    Log.d("observermessage","Five Seconds")
                    showNotification(btnNotif.tag.toString(),"A new notification created",
                        com.google.android.material.R.drawable.ic_mtrl_checked_circle)
                }
            }
        }
    }

    override fun onButtonUpdateClick(v: View) {
        val action = StudentDetailFragmentDirections.actionUpdateStudent()
        Navigation.findNavController(v).navigate(action)
    }
}