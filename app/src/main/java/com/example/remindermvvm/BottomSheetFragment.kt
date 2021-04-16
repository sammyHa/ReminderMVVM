package com.example.remindermvvm

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.remindermvvm.databinding.BottomSheetFragmentBinding
import com.example.remindermvvm.viewModels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*
import kotlinx.android.synthetic.main.task_items.*
import java.text.SimpleDateFormat
import java.util.*

class BottomSheetFragment:BottomSheetDialogFragment(), DatePickerDialog.OnDateSetListener{

    private var _binding: BottomSheetFragmentBinding? = null
    private val binding get() = _binding
    lateinit var model: MainViewModel


    var day = 0
    var month1 = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0


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

        return binding!!.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            save_task?.setOnClickListener {
                if (!isEmpty(input_taskTitle.text)){
                    model.saveOrUpdate()
                    Log.d("TAG", " Dismissed")
                    dismiss()
                }else{
                    Toast.makeText(context, "Enter Task", Toast.LENGTH_SHORT).show()
                }

            }

        date_pick_iv.setOnClickListener {
            Log.e(TAG, "onActivityCreated: Date selected!!!!")
            getDateTimeCalender()

        }

        cancel.setOnClickListener {
            date_holder_container.visibility = View.GONE

        }
        observeDatePickerDialogData()
    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Toast.makeText(requireContext().applicationContext, "Dismissed...", Toast.LENGTH_SHORT).show()
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
        }, year, month1, day)
        dpd.show()
    }

    private fun observeDatePickerDialogData(){
        model.datePicker.observe(requireActivity(), {
            tas_due_date?.text = it
        })
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }

}