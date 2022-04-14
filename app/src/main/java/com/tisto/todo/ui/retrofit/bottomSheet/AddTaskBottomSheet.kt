package com.tisto.todo.ui.retrofit.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.logs
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.tisto.todo.core.data.source.remote.network.State
import com.tisto.todo.core.data.source.remote.request.TodoRequest
import com.tisto.todo.core.model.Todo
import com.tisto.todo.databinding.FragmentAddTodoBinding
import com.tisto.todo.ui.base.BaseBottomSheet
import com.tisto.todo.ui.retrofit.RetrofitViewModel
import com.tisto.todo.util.defultError
import com.tisto.todo.util.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskBottomSheet(var todo: Todo? = null, var onSave: ((status: String) -> Unit)? = null) : BaseBottomSheet() {

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RetrofitViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideKeyboard()
        setupUI()
        setupClickListeners()

    }

    private fun setupClickListeners() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            if (todo != null) update()
            else simpan()
        }
    }

    private fun simpan() {

        val body = TodoRequest(
                binding.edtTitle.getString(),
                binding.edtDeskripsi.getString()
        )

        viewModel.create(body).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    dismiss()
                    onSave?.invoke("Success")
                }
                State.ERROR -> {
                    progress.dismiss()
                    showErrorDialog(it.message.defultError())
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun update() {
        val body = Todo(
                id = todo?.id ?: 0,
                binding.edtTitle.getString(),
                binding.edtDeskripsi.getString()
        )

        viewModel.update(body).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    dismiss()
                    onSave?.invoke("Success")
                }
                State.ERROR -> {
                    progress.dismiss()
                    showErrorDialog(it.message.defultError())
                }
                State.LOADING -> {
                    progress.show()
                }
            }
        }
    }

    private fun setupUI() {
        todo?.let {
            binding.edtTitle.setText(it.name)
            binding.edtDeskripsi.setText(it.deskripsi)
        }
    }

    companion object {
        fun newInstance(todo: Todo? = null, onSave: ((status: String) -> Unit)? = null) = AddTaskBottomSheet(todo) {
            onSave?.invoke(it)
        }

        const val TAG = "AddTaskBottomSheet"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}