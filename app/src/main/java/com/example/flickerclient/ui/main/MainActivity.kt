package com.example.flickerclient.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.support.design.widget.Snackbar
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.flickerclient.R
import com.example.flickerclient.data.Image
import com.example.flickerclient.data.source.local.ImagesDatabase
import com.example.flickerclient.ui.detail.DetailActivity
import com.example.flickerclient.ui.main.adapter.ImagesAdapter
import com.example.flickerclient.util.Const.AUTO_UPDATE_INTERVAL
import com.example.flickerclient.util.Const.EXTRA_IMAGE_ID
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This activity is responsible for observing ui data
 * from ImagesViewModel and updating ui
 * also triggering action in ImagesViewModel
 */
class MainActivity : AppCompatActivity(), ImagesAdapter.ImageClickedCallback {

    private lateinit var model: ImagesViewModel
    private lateinit var counter: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "[onCreate]")

        // get ViewModel
        getViewModel()

        setupToolbar()
        setupCounter()
        setupRecyclerView()
        setupSwipeToRefresh()

        // auto update on first run
        model.refresh()
    }

    private fun setupToolbar() = setSupportActionBar(toolbar)


    private fun setupCounter() {
        counter = object : CountDownTimer(AUTO_UPDATE_INTERVAL, AUTO_UPDATE_INTERVAL) {
            override fun onFinish() {
                Log.d(TAG, "Counter: onFinish")
                Toast.makeText(this@MainActivity, "Auto updating images..", Toast.LENGTH_SHORT).show()
                model.refresh()
                counter.start()
            }

            override fun onTick(p0: Long) {}
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "[onStart]")
        counter.start()
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "[onStop]")
        counter.cancel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.refresh -> {
                model.refresh()
                true
            }
            R.id.delete -> {
                model.delete()
                true
            }
            else -> false
        }
    }

    private fun getViewModel() {
        val imagesDao = ImagesDatabase.getInstance(this).imagesDao()
        model = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ImagesViewModel(application, imagesDao) { showMessage(it) } as T
            }
        }).get(ImagesViewModel::class.java)
    }

    private fun setupRecyclerView() {
        // set layout manager based on screen orientation
        if (isPortrait()) imagesRv.layoutManager = LinearLayoutManager(this)
        else imagesRv.layoutManager = GridLayoutManager(this, 2)

        // observe images from room
        val adapter = ImagesAdapter(this)
        imagesRv.adapter = adapter
        model.images.observe(this, Observer<PagedList<Image>> {
            adapter.submitList(it)
        })
    }

    private fun setupSwipeToRefresh() {
        swipeRefresh.setOnRefreshListener { model.refresh() }

        model.refreshStateLoading.observe(this, Observer<Boolean> {
            swipeRefresh.isRefreshing = it!!
        })
    }

    override fun onImageClicked(image: Image) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_IMAGE_ID, image.id)
        }
        startActivity(intent)
    }

    private fun showMessage(msg: String) = Snackbar.make(swipeRefresh, msg, Snackbar.LENGTH_SHORT).show()

    private fun isPortrait(): Boolean = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    companion object {
        private const val TAG = "MainActivity"
    }
}
