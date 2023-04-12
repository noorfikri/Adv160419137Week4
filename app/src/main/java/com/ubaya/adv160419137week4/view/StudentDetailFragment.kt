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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.ubaya.adv160419137week4.R
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
class StudentDetailFragment : Fragment() {
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_detail, container, false)
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
            val txtID = view.findViewById<TextView>(R.id.txtID)
            val txtName = view.findViewById<TextView>(R.id.txtName)
            val txtBoD = view.findViewById<TextView>(R.id.txtBoD)
            val txtPhone = view.findViewById<TextView>(R.id.txtPhone)
            var imageView = view.findViewById<ImageView>(R.id.imgStudent)
            var progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
            val btnNotif = view.findViewById<Button>(R.id.btnNotif)

            val name = it.name.toString()

            imageView.loadImage(it.photoUrl,progressBar)

            txtID.text = it.id.toString()
            txtName.text = name
            txtBoD.text = it.dob.toString()
            txtPhone.text = it.phone.toString()

            btnNotif.setOnClickListener {
                val observable = Observable.timer(5, TimeUnit.SECONDS).apply {
                    subscribeOn(Schedulers.io())
                    observeOn(AndroidSchedulers.mainThread())
                    subscribe {
                        Log.d("observermessage","Five Seconds")
                        showNotification(name,"A new notification created",
                            com.google.android.material.R.drawable.ic_mtrl_checked_circle)
                    }
                }
            }
        })
    }
}