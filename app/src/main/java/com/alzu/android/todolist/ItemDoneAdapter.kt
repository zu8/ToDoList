package com.alzu.android.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alzu.android.todolist.databinding.ItemDoneBinding

class ItemDoneAdapter: RecyclerView.Adapter<ItemDoneAdapter.ItemDoneHolder>() {
    var itemList: List<Item> = ArrayList()

    class ItemDoneHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemDoneBinding.bind(view)
        fun bind(item: Item) = with(binding){
            titleListDoneTv.text = item.title
            titleListDoneTv.paintFlags = titleListDoneTv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            if (item.description == "") describeDoneTv.visibility = View.GONE
            else {
                describeDoneTv.visibility = View.VISIBLE
                describeDoneTv.text = item.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemDoneHolder {
        return ItemDoneHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_done,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ItemDoneHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun refreshItems(itemList: List<Item>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}