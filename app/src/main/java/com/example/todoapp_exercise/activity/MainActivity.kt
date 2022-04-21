package com.example.todoapp_exercise.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todoapp_exercise.R
import com.example.todoapp_exercise.database.FirebaseRealtime
import com.example.todoapp_exercise.databinding.ActivityMainBinding
import com.example.todoapp_exercise.model.ToDoItemModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebase = FirebaseRealtime()


        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //Add navigation
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.addFragment, R.id.homeFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navMenu.setupWithNavController(navController)
    }
}