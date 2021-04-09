package com.example.remindermvvm

import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.remindermvvm.databinding.BottomSheetFragmentBinding
import com.example.remindermvvm.viewModels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_fragment.*
import kotlinx.android.synthetic.main.task_items.*

class BottomSheetFragment:BottomSheetDialogFragment() {

    private var _binding: BottomSheetFragmentBinding? = null
    private val binding get() = _binding
    lateinit var model: MainViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            save_task.isEnabled = true
            save_task?.setOnClickListener {

                if (!isEmpty(input_taskTitle.text)){
                    model.saveOrUpdate()
                    Log.d("TAG"," Dismissed")
                    dismiss()
                }
                Toast.makeText(context, "Enter Task", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        Toast.makeText(requireContext().applicationContext, "Dismissed...", Toast.LENGTH_SHORT).show()
        return
//        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 1)
    }




}