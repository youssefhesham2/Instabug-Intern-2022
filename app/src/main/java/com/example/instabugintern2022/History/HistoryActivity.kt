package com.example.instabugintern2022.History

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.domain.entities.ResponseEntity
import com.example.instabugintern2022.R
import com.example.instabugintern2022.adapters.HistoryAdapter
import com.example.instabugintern2022.databinding.ActivityDisplayBinding
import com.example.instabugintern2022.databinding.ActivityHistoryBinding
import com.example.instabugintern2022.displayactivity.DisplayPresenter

class HistoryActivity : AppCompatActivity(), HistoryView {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var presenter: HistoryPresenter
    private val adapter = HistoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = HistoryPresenter()

        presenter.onAttached(this)
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvResponseHistory.adapter = adapter
    }

    override fun getBundleData(): ArrayList<ResponseEntity> {
        return intent.getSerializableExtra("RESPONSES_HISTORY") as ArrayList<ResponseEntity>
    }

    override fun notifyDataSetChanged(responseEntityList: ArrayList<ResponseEntity>) {
        adapter.setResponseEntityList(responseEntityList)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }
}