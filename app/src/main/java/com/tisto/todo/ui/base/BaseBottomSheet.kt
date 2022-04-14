package com.tisto.todo.ui.base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.inyongtisto.myhelper.util.AppProgressDialog
import com.tisto.todo.R

abstract class BaseBottomSheet : BottomSheetDialogFragment() {

    lateinit var progress: AppProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupProgress()
    }

    override fun onStart() {
        super.onStart()
        val sheetDialog = dialog as BottomSheetDialog
        val bottomSheet = sheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
//        bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
//        bottomSheet.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//        bottomSheetBehavior.maxWidth = getFragmentWidthPercentage(80)
        bottomSheetBehavior.skipCollapsed = true
        bottomSheetBehavior.isFitToContents = false
    }

    private fun setupProgress() {
        progress = AppProgressDialog(requireContext())
        progress.setCancelable(false)
        progress.setCanceledOnTouchOutside(false)
    }

}