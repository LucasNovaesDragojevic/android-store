package com.store.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.store.R
import com.store.ui.fragment.PaymentFragment
import com.store.ui.fragment.ProductDetailsFragment
import com.store.ui.fragment.ProductListFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}