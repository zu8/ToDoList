package com.alzu.android.todolist.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.*
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.alzu.android.todolist.*
import com.alzu.android.todolist.databinding.FragmentItemListBinding
import com.alzu.android.todolist.helper.SimpleItemTouchHelperCallback


class ItemListFragment: Fragment() {
    private lateinit var binding: FragmentItemListBinding
    private val itemsList: ItemViewModel by activityViewModels()
    private var flag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("EXPAND", flag)
        super.onSaveInstanceState(outState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val expandBtn = buttonExpandInitialization(binding,savedInstanceState)
        val adapter1 = ItemToDoAdapter { item -> adapterOnClick(item) }
        val adapter2 = ItemDoneAdapter()
        binding.itemsToDo.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsToDo.adapter = adapter1
        binding.itemsDone.layoutManager = LinearLayoutManager(requireContext())
        binding.itemsDone.adapter = adapter2
        itemsList.itemsListToDo.observe(activity as LifecycleOwner) {
            adapter1.refreshItems(it)
        }
        itemsList.itemsListDone.observe(activity as LifecycleOwner) {
            adapter2.refreshItems(it)
        }
        val callback = SimpleItemTouchHelperCallback(adapter1)
        val helper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.itemsToDo)
        expandBtn.setOnClickListener {
            buttonBehavior(expandBtn)
        }
        binding.AddBtn.setOnClickListener{
            ItemAddFragment().show(parentFragmentManager,"")
        }

    }

    /* when RecyclerView item is clicked. */
    private fun adapterOnClick(item: Item) {
        var bund = Bundle()
        bund.putParcelable("item",item)
        setFragmentResult("WhatExactlyItem",bund)
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ItemDetailsFragment>(R.id.fragmentCV)
            addToBackStack(null)
        }
    }

    private fun buttonExpandInitialization(binding: FragmentItemListBinding,bundle: Bundle?): ToggleButton{
        binding.collapsingBtn.setButtonDrawable(R.drawable.ic_key_down)
        binding.collapsingBtn.textOff = getString(R.string.show)
        binding.collapsingBtn.textOn = getString(R.string.hide)
        binding.collapsingBtn.isChecked = false
        bundle?.let{
            binding.collapsingBtn.isChecked = it.getBoolean("EXPAND")
            buttonBehavior(binding.collapsingBtn)
        }
        return binding.collapsingBtn
    }

    private fun buttonBehavior(btn: ToggleButton){
        val lparams : ConstraintLayout.LayoutParams = btn.layoutParams
                as ConstraintLayout.LayoutParams
        if (btn.isChecked){
            flag = true
            lparams.verticalBias = 0.1F
            btn.layoutParams = lparams
            btn.setButtonDrawable(R.drawable.ic_key_up)
            btn.textOn = getString(R.string.hide)
        }
        else{
            flag = false
            btn.textOff = getString(R.string.show)
            btn.setButtonDrawable(R.drawable.ic_key_down)
            lparams.verticalBias = 1F
            btn.layoutParams = lparams
        }
    }
}