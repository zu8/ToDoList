package com.alzu.android.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.alzu.android.todolist.databinding.ActivityMainBinding
import com.alzu.android.todolist.fragments.ItemListFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null){
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<ItemListFragment>(R.id.fragmentCV)
            }
        }
    }
}