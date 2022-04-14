package com.tisto.todo.ui.retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inyongtisto.myhelper.extension.*
import com.inyongtisto.myhelper.util.EditTextSearchListener
import com.tisto.todo.core.data.source.remote.network.State
import com.tisto.todo.core.model.Todo
import com.tisto.todo.databinding.FragmentHomeBinding
import com.tisto.todo.ui.base.BaseFragment
import com.tisto.todo.ui.retrofit.adapter.TodoAdapter
import com.tisto.todo.ui.retrofit.bottomSheet.AddTaskBottomSheet
import com.tisto.todo.util.defultError
import org.koin.androidx.viewmodel.ext.android.viewModel

class RetrofitFragment : BaseFragment() {

    private val viewModel: RetrofitViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var adapter = TodoAdapter()
    private var listData = listOf<Todo>()

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
            AddTaskBottomSheet.newInstance {
                getData()
            }.show(childFragmentManager, AddTaskBottomSheet.TAG)
        }
    }

    private fun init() {
        binding.apply {
            swipeRefresh.setDefaultColor()
            swipeRefresh.setOnRefreshListener {
                getData()
            }
        }

        EditTextSearchListener(binding.lySearch.edtSearch, 100) { s ->
            val list = listData.filter { it.name.lowercase().contains(s.lowercase()) }
            adapter.addItem(list)
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
            AddTaskBottomSheet.newInstance(it) {
                getData()
            }.show(childFragmentManager, AddTaskBottomSheet.TAG)
        }
    }

    private fun getData() {
        viewModel.getAll().observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    binding.lyData.pd.toGone()
                    binding.swipeRefresh.isRefreshing = false
                    val body = it.body ?: listOf()
                    listData = body
                    adapter.addItem(body)
                    binding.lyData.tvError.visible(body.isEmpty())
                }
                State.ERROR -> {
                    binding.lyData.pd.toGone()
                    binding.swipeRefresh.isRefreshing = false
                    binding.lyData.tvError.setErrors {
                        getData()
                    }
                }
                State.LOADING -> {
                    binding.swipeRefresh.isRefreshing = true
                }
            }
        }
    }

    private fun onDelete(todo: Todo, index: Int) {
        viewModel.delete(todo).observe(viewLifecycleOwner) {
            when (it.state) {
                State.SUCCESS -> {
                    progress.dismiss()
                    toastSuccess("Task berhasil dihapus")
                    adapter.removeAt(index)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}