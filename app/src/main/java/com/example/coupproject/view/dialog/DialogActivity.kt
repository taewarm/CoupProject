package com.example.coupproject.view.dialog

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.coupproject.BuildConfig
import com.example.coupproject.R
import com.example.coupproject.databinding.ActivityDialogBinding
import com.google.firebase.storage.FirebaseStorage

class DialogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.bind(layoutInflater.inflate(R.layout.activity_dialog, null))
        var width = 0
        var height = 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = windowManager.currentWindowMetrics
            val insets =
                windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val navi =
                windowMetrics.windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            width = windowMetrics.bounds.width() - insets.left - insets.right
            height = windowMetrics.bounds.height() - navi.top + navi.bottom
        } else {
            val windowMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(windowMetrics)
            width = windowMetrics.widthPixels
            height = windowMetrics.heightPixels
        }
        binding.root.layoutParams = ViewGroup.LayoutParams(width, height)
        Log.i(TAG, intent.getStringExtra("fileName").toString())
        val reference = FirebaseStorage.getInstance()
            .getReferenceFromUrl("${BuildConfig.FIREBASE_URI_KEY}images/${intent.getStringExtra("fileName")}")
//        reference.getBytes(1024 * 1024).addOnSuccessListener {
//            Log.i(TAG, "$it.")
//        }.addOnFailureListener { Log.e(TAG, it.message, it) }
        reference.downloadUrl.addOnSuccessListener {
            Glide.with(this).load(it).into(binding.dialogImage)
        }.addOnFailureListener { Log.e(TAG, it.message, it) }
        setContentView(binding.root)
    }

    companion object {
        const val TAG = "DialogActivity"
    }
}