package com.alzu.android.todolist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(val title: String,
                val description: String,
                val itemDate: String,
                val isDone: Boolean) : Parcelable
