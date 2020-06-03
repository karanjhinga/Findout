package com.karan.findout.ui.home

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.karan.findout.R
import com.karan.findout.databinding.BottomSheetSortBinding
import com.karan.findout.utils.Sort

class SortBottomSheetDialog(factory: ViewModelProvider.Factory) : BottomSheetDialogFragment() {

    private var binding: BottomSheetSortBinding? = null
    private val viewModel: HomeViewModel by activityViewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetSortBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            viewModel.sort.value?.let { sort ->
                sortGrp.check(sort.id)
            }

            applyBtn.setOnClickListener {
                val selectedSort = Sort.values().find { it.id == sortGrp.checkedRadioButtonId }
                if (selectedSort != null) {
                    viewModel.sortBy(selectedSort)
                }
                dismiss()
            }
        }
    }
}
