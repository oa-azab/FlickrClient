package com.example.flickerclient.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.example.flickerclient.R
import com.example.flickerclient.ui.adapter.ImagesAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "[onCreate]")

        // create presenter
        presenter = MainPresenter(this)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        imagesRv.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "[onStart]")
        presenter.start()
    }

    override fun setAdapter(adapter: ImagesAdapter) {
        Log.d(TAG, "[setAdapter]")
        imagesRv.adapter = adapter
    }

    override fun showData() {
        Log.d(TAG, "[showData]")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
