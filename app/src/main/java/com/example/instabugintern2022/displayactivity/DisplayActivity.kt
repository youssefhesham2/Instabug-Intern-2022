package com.example.instabugintern2022.displayactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.domain.entities.ResponseEntity
import com.example.instabugintern2022.R
import com.example.instabugintern2022.databinding.ActivityDisplayBinding
import com.example.instabugintern2022.databinding.ActivityMainBinding

class DisplayActivity : AppCompatActivity(), DisplayView {
    private lateinit var binding: ActivityDisplayBinding
    private lateinit var presenter: DisplayPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = DisplayPresenter()

        presenter.onAttached(this)
    }

    override fun getBundleData(): ResponseEntity {
        return intent.getSerializableExtra("RESPONSE_ENTITY") as ResponseEntity
    }

    override fun setStatusView(status: String) {
        binding.tvStatus.text = status
    }

    override fun setRequestHeaderView(requestHeader: String) {
        binding.tvRequestHeaders.text = requestHeader
    }

    override fun setResponseHeaderView(responseHeader: String) {
        binding.tvResponseHeaders.text = responseHeader
    }

    override fun setQueryParamView(queryParam: String) {
        binding.tvQueryParam.text = queryParam
    }

    override fun changeQueryParamViewTitleTo(title: String) {
        binding.tvQueryParamTitle.text = title
    }

    override fun setResponseBodyView(responseBody: String) {
        binding.tvResponseBody.text = responseBody
    }
}