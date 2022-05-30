package com.example.instabugintern2022.mainactivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.instabugintern2022.R
import com.example.instabugintern2022.databinding.ActivityMainBinding


class MainActivity constructor(private val presenter: MainPresenter = MainPresenter()) :
    AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter.onAttached(this)
        onClickAddNewHeaderBtn()
        setupSpinner()
    }

    private fun onClickAddNewHeaderBtn() {
        binding.btnAddNewHeader.setOnClickListener(View.OnClickListener {
            presenter.onClickAddNewHeaderBtn()
        })
    }

    private fun setupSpinner() {
        binding.spinnerRequestType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    presenter.onSpinnerSelectedItem(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun checkUserInternetConnection(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)
        if (Build.VERSION.SDK_INT < 23) {
            val networkInfo = connectivityManager.getActiveNetworkInfo()
            networkInfo?.let { return true } ?: return false
        } else {
            val network = connectivityManager.getActiveNetwork();
            network?.let {
                val nc = connectivityManager.getNetworkCapabilities(network);
                return (nc?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true || nc?.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                ) == true);
            }
        }
        return false
    }

    override fun addNewHeaderField() {
        val headerItem = layoutInflater.inflate(R.layout.header_item, null)
        binding.headerLayout.addView(headerItem)
    }

    override fun goneBodyViewVisibility() {
        binding.bodyLayout.visibility = View.GONE
    }

    override fun visibleBodyViewVisibility() {
        binding.bodyLayout.visibility = View.VISIBLE
    }
}