package com.tisto.todo.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.inyongtisto.myhelper.extension.*
import com.inyongtisto.myhelper.util.EditTextSearchListener
import com.tisto.todo.core.data.source.local.entity.TodoEntity
import com.tisto.todo.core.data.source.remote.network.State
import com.tisto.todo.core.model.Todo
import com.tisto.todo.databinding.FragmentDashboardBinding
import com.tisto.todo.databinding.FragmentHomeBinding
import com.tisto.todo.ui.base.BaseFragment
import com.tisto.todo.ui.retrofit.RetrofitViewModel
import com.tisto.todo.ui.retrofit.adapter.TodoAdapter
import com.tisto.todo.ui.retrofit.bottomSheet.AddTaskBottomSheet
import com.tisto.todo.ui.room.adapter.TodoLocalAdapter
import com.tisto.todo.ui.room.bottomSheet.AddTaskLocalBottomSheet
import com.tisto.todo.util.defultError
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.log

class RoomFragment : BaseFragment() {

    private val viewModel: RoomViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var adapter = TodoLocalAdapter()
    private var listData = listOf<TodoEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupAdapter()
        getData()
        init()
        mainButton()
        return binding.root
    }

    private fun mainButton() {
        binding.btnAdd.setOnClickListener {
            AddTaskLocalBottomSheet.newInstance {
                getData()
            }.show(childFragmentManager, AddTaskLocalBottomSheet.TAG)
        }
    }

    private fun init() {
        binding.apply {
            swipeRefresh.setDefaultColor()
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
            }
        }

        EditTextSearchListener(binding.lySearch.edtSearch, 100) { s ->
            viewModel.search(s).observeOnce(viewLifecycleOwner) {
                adapter.addItem(it)
            }
        }.onClear = {
            adapter.addItem(listData)
        }
    }

    private fun setupAdapter() {
        binding.lyData.rvData.layoutManager = verticalLayoutManager()
        binding.lyData.rvData.adapter = adapter
        adapter.onDelete = { item, index ->
            showConfirmDialog("Delete?", "Apakah anda yakin?", "Delete") {
                onDelete(item, index)
            }
        }
        adapter.onClick = {
            AddTaskLocalBottomSheet.newInstance(it) {
                getData()
            }.show(childFragmentManager, AddTaskLocalBottomSheet.TAG)
        }
    }

    private fun getData() {
        viewModel.getAll().observe(viewLifecycleOwner) {
            binding.lyData.pd.toGone()
            val body = it ?: listOf()
            listData = body
            adapter.addItem(body)
            binding.lyData.tvError.visible(body.isEmpty())
        }
    }

    private fun onDelete(todo: TodoEntity, index: Int) {
        viewModel.delete(todo)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}