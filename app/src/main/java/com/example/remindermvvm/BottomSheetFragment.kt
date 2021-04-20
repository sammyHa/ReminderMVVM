package com.example.remindermvvm

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.remindermvvm.databinding.BottomSheetFragmentBinding
import com.example.remindermvvm.utils.toast
import com.example.remindermvvm.viewModels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetFragment:BottomSheetDialogFragment(),TaskListener{

    private var _binding: BottomSheetFragmentBinding? = null
    private val binding get() = _binding
    lateinit var model: MainViewModel

    var day = 0
    var month1 = 0
    var year = 0
    var hour = 0
    var minute = 0

    var mDay = 0
    var mMonth1 = 0
    var mYear = 0


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetFragmentBinding.inflate(inflater, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        model = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        _binding!!.bottomSheetViewModel = model
        _binding!!.lifecycleOwner = this
        model.taskListener = this
        return binding!!.root

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        save_task.setTextColor(Color.GRAY)

        cancel.setOnClickListener {
            date_holder_container.visibility = View.GONE
        }
        date_pick_iv.setOnClickListener {
            Log.e(ContentValues.TAG, "onActivityCreated: Date selected!!!!")
            getDateTimeCalender()
           date_holder_container.visibility = View.VISIBLE



        }


        input_taskTitle.addTextChangedListener {
            save_task.isEnabled = true
            save_task.setTextColor(Color.BLACK)
        }

    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        return
    }


    @SuppressLint("SimpleDateFormat")
    fun getDateTimeCalender(){

        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month1 = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)

        val dpd = DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->
            val sdf = SimpleDateFormat("EEE, MMM, dd");
            cal.set(year, month, dayOfMonth);
            val dateString = sdf.format(cal.time)
           model.datePicker.value = dateString
            date_holder_tv.text = dateString
        }, year, month1, day)
        dpd.show()


    }


    override fun onStarted() {
        context?.toast("Started")
    }

    override fun onSuccess() {
        context?.toast("Task added")
    }

    override fun onFailure(message: String) {
        context?.toast(message)
    }

}