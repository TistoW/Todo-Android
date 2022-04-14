package com.tisto.todo.ui.room.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.inyongtisto.myhelper.extension.logs
import com.inyongtisto.myhelper.extension.popUpMenu
import com.inyongtisto.myhelper.extension.showInfoDialog
import com.inyongtisto.myhelper.extension.visible
import com.tisto.todo.core.data.source.local.entity.TodoEntity
import com.tisto.todo.core.model.Todo
import com.tisto.todo.databinding.ItemTodoBinding

class TodoLocalAdapter(
    var onClick: ((TodoEntity) -> Unit)? = null,
    var onDelete: ((data: TodoEntity, index: Int) -> Unit)? = null
) : RecyclerView.Adapter<TodoLocalAdapter.ViewHolder>() {

    var data = ArrayList<TodoEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemTodoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: List<TodoEntity>) {
        data.clear()
        data.addAll(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAt(i: Int) {
        data.removeAt(i)
        notifyItemRemoved(i)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val itemBinding: ItemTodoBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            with(itemBinding) {
                val context = root.context
                val item = data[position]
                tvName.text = item.name
                tvDeskripsi.visible(!item.deskripsi.isNullOrEmpty())
                tvDeskripsi.text = item.deskripsi

                layout.setOnClickListener {
                    onClick?.invoke(item)
                }

                btnMenu.setOnClickListener { view ->
                    context.popUpMenu(view, listOf("Hapus", "Detail")) {
                        when (it) {
                            "Hapus" -> {
                                onDelete?.invoke(item, adapterPosition)
                            }
                            "Detail" -> {
                                onClick?.invoke(item)
                            }
                        }
                    }
                }
            }
        }
    }
}