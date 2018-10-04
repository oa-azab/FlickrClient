package com.example.flickerclient.ui.detail

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.flickerclient.R
import com.example.flickerclient.data.Image
import com.example.flickerclient.data.source.local.ImagesDatabase
import com.example.flickerclient.util.AppExecutors
import com.example.flickerclient.util.Const.EXTRA_IMAGE_ID
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * This activity is responsible to show single image in fullscreen mode
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var currentImage: Image
    private var uiVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // hide systemUi
        hideSystemUI()

        // setup toolbar margin top
        setupToolbarMarginTop()

        // get Image id
        val imageId = intent.getStringExtra(EXTRA_IMAGE_ID)
        if (imageId == null) {
            Toast.makeText(this, "Invalid Image id", Toast.LENGTH_SHORT).show()
            finish()
        }

        // get Image and display it
        val imagesDao = ImagesDatabase.getInstance(this).imagesDao()
        AppExecutors.diskIO.execute {
            currentImage = imagesDao.getImage(imageId)
            if (currentImage != null)
                AppExecutors.mainThread.execute {
                    Picasso.get()
                            .load(currentImage.getUrl())
                            .placeholder(R.drawable.img_placeholder)
                            .into(imageFullscreen)
                }
        }

        // set click listeners
        backBtn.setOnClickListener { onBackPressed() }
        imageFullscreen.setOnClickListener { toggleSystemUI() }
    }

    private fun setupToolbarMarginTop() {
        ViewCompat.setOnApplyWindowInsetsListener(toolbar) { _, insets ->
            val statusBarSize = insets.systemWindowInsetTop
            Log.d(TAG, "statusBarSize= $statusBarSize")
            val params = toolbar.layoutParams as ConstraintLayout.LayoutParams
            params.topMargin = statusBarSize
            toolbar.layoutParams = params
            return@setOnApplyWindowInsetsListener insets
        }
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun showSystemUI() {
        // Shows the system bars by removing all the flags
        // except for the ones that make the content appear under the system bars.
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    private fun toggleSystemUI() {
        if (uiVisible) {
            hideSystemUI()
            toolbar.visibility = View.GONE
        } else {
            showSystemUI()
            toolbar.visibility = View.VISIBLE
        }
        uiVisible = !uiVisible
    }

    companion object {
        private const val TAG = "DetailActivity"
    }
}
