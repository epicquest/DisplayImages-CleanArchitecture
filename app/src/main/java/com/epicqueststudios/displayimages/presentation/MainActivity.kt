package com.epicqueststudios.displayimages.presentation

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.epicqueststudios.displayimages.MainViewModel
import com.epicqueststudios.displayimages.R
import com.epicqueststudios.displayimages.base.BaseActivity
import com.epicqueststudios.displayimages.base.getApplicationComponent
import com.epicqueststudios.displayimages.databinding.ActivityMainBinding
import com.epicqueststudios.displayimages.withFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
  //  lateinit var mainViewModelFactory: MainViewModel.Factory
    lateinit var mainViewModelFactory: MainViewModel.Factory
    private val mainViewModel: MainViewModel by viewModels {
        withFactory(mainViewModelFactory)
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        mainViewModel.downloadImages(false)
    }

    override fun injectDependencies() {
        getApplicationComponent()?.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}