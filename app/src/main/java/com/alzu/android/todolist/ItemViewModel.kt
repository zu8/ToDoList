package com.alzu.android.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ItemViewModel: ViewModel() {
    val itemsListToDo: MutableLiveData<MutableList<Item>> by lazy {
        MutableLiveData<MutableList<Item>>()
    }
    init{
        itemsListToDo.value = ItemRepo.getItemsToDo()
    }
    val itemsListDone: MutableLiveData<MutableList<Item>> by lazy {
        MutableLiveData<MutableList<Item>>()
    }
    init{
        itemsListDone.value = ItemRepo.getItemsDone()
    }
}