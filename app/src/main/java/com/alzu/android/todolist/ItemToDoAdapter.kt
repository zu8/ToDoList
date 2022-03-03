package com.alzu.android.todolist

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alzu.android.todolist.databinding.ItemToDoBinding
import com.alzu.android.todolist.helper.ItemTouchHelperAdapter
import com.alzu.android.todolist.helper.ItemTouchHelperViewHolder
import java.util.*

class ItemToDoAdapter(private val onClick: (Item) -> Unit):
    RecyclerView.Adapter<ItemToDoAdapter.ItemToDoHolder>(),
    ItemTouchHelperAdapter {
    private var itemList = mutableListOf<Item>()

    class ItemToDoHolder(itemView: View, val onClick: (Item) -> Unit):
        RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder{
        private val binding = ItemToDoBinding.bind(itemView)
        private var currentItem: Item? = null

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(item: Item) = with (binding){
            currentItem = item
            titleListTv.text = item.title
            if (item.description.isEmpty() || item.description.isBlank()) {
                describTv.visibility = View.GONE
            }
            else {
                describTv.visibility = View.VISIBLE
                describTv.text = item.description
            }
            dateBtn.text = item.itemDate
            if (item.itemDate.startsWith("today")){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dateBtn.setTextAppearance(R.style.MyBtnStyleBlue)
                }
            }
            btnStatus.setOnClickListener{
                Toast.makeText(it.context,"controller to move in future", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemToDoHolder {
        return ItemToDoHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.item_to_do,parent,false),onClick
        )
    }

    override fun onBindViewHolder(holder: ItemToDoHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun refreshItems(itemList: MutableList<Item>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(itemList, i, i + 1)
            }
        }
        else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(itemList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition,toPosition)
    }

    override fun onItemDismiss(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }
}