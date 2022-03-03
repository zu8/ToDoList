package com.alzu.android.todolist.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResultListener
import com.alzu.android.todolist.Item

import com.alzu.android.todolist.databinding.FragmentItemDetailsBinding

class ItemDetailsFragment: DialogFragment() {
    private lateinit var binding: FragmentItemDetailsBinding
    private var curItem: Item? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("WhatExactlyItem") { _, bundle ->
            bundle.let { curItem = it.getParcelable("item") }
            binding.detailsTV.text = curItem?.title ?: "something go wrong..."
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailsBinding.inflate(layoutInflater)
        return binding.root
    }
}