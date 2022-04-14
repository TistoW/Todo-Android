package com.tisto.todo.ui.room.bottomSheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inyongtisto.myhelper.extension.getString
import com.inyongtisto.myhelper.extension.showErrorDialog
import com.tisto.todo.core.data.source.local.entity.TodoEntity
import com.tisto.todo.core.data.source.remote.network.State
import com.tisto.todo.core.data.source.remote.request.TodoRequest
import com.tisto.todo.core.model.Todo
import com.tisto.todo.databinding.FragmentAddTodoBinding
import com.tisto.todo.ui.base.BaseBottomSheet
import com.tisto.todo.ui.retrofit.RetrofitViewModel
import com.tisto.todo.ui.room.RoomViewModel
import com.tisto.todo.util.defultError
import com.tisto.todo.util.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTaskLocalBottomSheet(var todo: TodoEntity? = null, var onSave: ((status: String) -> Unit)? = null) : BaseBottomSheet() {

    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RoomViewModel by viewModel()

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
            dismiss()
        }
    }

    private fun simpan() {

        val body = TodoEntity(
                name = binding.edtTitle.getString(),
                deskripsi = binding.edtDeskripsi.getString()
        )

        viewModel.create(body)
    }

    private fun update() {
        val body = TodoEntity(
                id = todo?.id ?: 0,
                binding.edtTitle.getString(),
                binding.edtDeskripsi.getString()
        )
        viewModel.update(body)
    }

    private fun setupUI() {
        todo?.let {
            binding.edtTitle.setText(it.name)
            binding.edtDeskripsi.setText(it.deskripsi)
        }
    }

    companion object {
        fun newInstance(todo: TodoEntity? = null, onSave: ((status: String) -> Unit)? = null) = AddTaskLocalBottomSheet(todo) {
            onSave?.invoke(it)
        }

        const val TAG = "AddTaskBottomSheet"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}