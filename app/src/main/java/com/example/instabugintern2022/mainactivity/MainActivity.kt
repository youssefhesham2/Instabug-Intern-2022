package com.example.instabugintern2022.mainactivity

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import com.example.data.datasources.DatabaseHelper
import com.example.data.repositories.MainRepositoryImpl
import com.example.domain.entities.HeaderEntity
import com.example.domain.entities.ResponseEntity
import com.example.domain.usecases.MainUseCase
import com.example.instabugintern2022.History.HistoryActivity
import com.example.instabugintern2022.R
import com.example.instabugintern2022.databinding.ActivityMainBinding
import com.example.instabugintern2022.displayactivity.DisplayActivity
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), MainView {
    private lateinit var presenter: MainPresenter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter =
            MainPresenter(MainUseCase(MainRepositoryImpl(databaseHelper = DatabaseHelper(this))))

        presenter.onAttached(this)
        setupOnClickAddNewParamBtn()
        setupOnClickAddNewHeaderBtn()
        setupSpinner()
        setupOnClickSendRequestBtn()
        setupOnClickHistoryBtn()
    }

    private fun setupOnClickAddNewParamBtn() {
        binding.btnAddNewParam.setOnClickListener(View.OnClickListener {
            presenter.onClickAddNewParamBtn()
        })
    }

    private fun setupOnClickAddNewHeaderBtn() {
        binding.btnAddNewHeader.setOnClickListener(View.OnClickListener {
            presenter.onClickAddNewHeaderBtn()
        })
    }

    private fun setupSpinner() {
        binding.spinnerRequestType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    presenter.onSpinnerSelectedItem(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun setupOnClickSendRequestBtn() {
        binding.btnSendRequest.setOnClickListener {
            presenter.onClickSendRequestBtn()
        }
    }

    override fun getUserInternetConnectionState(): Boolean {
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

    override fun getParamListFromLayout(): ArrayList<HeaderEntity> {
        val params = ArrayList<HeaderEntity>()
        binding.paramForm.forEach {
            val value = it.findViewById<EditText>(R.id.tv_header_value)
            val key = it.findViewById<EditText>(R.id.tv_header_key)
            params.add(HeaderEntity(key.text.toString(), value.text.toString()))
        }
        return params
    }

    override fun getHeaderListFromLayout(): ArrayList<HeaderEntity> {
        val headers = ArrayList<HeaderEntity>()
        binding.headerForm.forEach {
            val value = it.findViewById<EditText>(R.id.tv_header_value)
            val key = it.findViewById<EditText>(R.id.tv_header_key)
            headers.add(HeaderEntity(key.text.toString(), value.text.toString()))
        }
        return headers
    }

    private fun setupOnClickHistoryBtn() {
        binding.fabHistory.setOnClickListener {
            presenter.onClickHistoryBtn()
        }
    }

    override fun goneParamViewVisibility() {
        binding.paramLayout.visibility = View.GONE
    }

    override fun visibleParamViewVisibility() {
        binding.paramLayout.visibility = View.VISIBLE
    }

    override fun addNewParamField() {
        val paramItem = layoutInflater.inflate(R.layout.header_item, null)
        binding.paramForm.addView(paramItem)
        paramItem.findViewById<ImageView>(R.id.img_delete_header_field).setOnClickListener {
            binding.paramForm.removeView(paramItem)
        }
    }

    override fun addNewHeaderField() {
        val headerItem = layoutInflater.inflate(R.layout.header_item, null)
        binding.headerForm.addView(headerItem)
        headerItem.findViewById<ImageView>(R.id.img_delete_header_field).setOnClickListener {
            binding.headerForm.removeView(headerItem)
        }
    }

    override fun getUrFromUrlView(): String {
        return binding.edtUrl.text.toString()
    }

    override fun getBodyFromBodyView(): String {
        return binding.edtBody.text.toString()
    }

    override fun goneBodyViewVisibility() {
        binding.bodyLayout.visibility = View.GONE
    }

    override fun visibleBodyViewVisibility() {
        binding.bodyLayout.visibility = View.VISIBLE
    }

    override fun progressShow() {
        binding.btnSendRequest.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun progressDismiss() {
        binding.progressBar.visibility = View.GONE
        binding.btnSendRequest.visibility = View.VISIBLE
    }

    override fun snackbar(message: String, color: Int) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(this, color))
            .show()
    }

    override fun intentToDisplayActivity(responseEntity: ResponseEntity) {
        val intent = Intent(baseContext, DisplayActivity::class.java)
        intent.putExtra("RESPONSE_ENTITY", responseEntity)
        startActivity(intent)
    }

    override fun intentToHistoryActivity(responseEntityList: ArrayList<ResponseEntity>) {
        val intent = Intent(baseContext, HistoryActivity::class.java)
        intent.putExtra("RESPONSES_HISTORY", responseEntityList)
        startActivity(intent)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}