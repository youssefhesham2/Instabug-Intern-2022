package com.example.instabugintern2022.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.entities.ResponseRequestDataEntity
import com.example.domain.entities.ResponseEntity
import com.example.instabugintern2022.R

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private var responseEntityList = ArrayList<ResponseEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.responses_history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val responseEntity: ResponseEntity = responseEntityList[position]
        var requestType: String
        requestType = if (responseEntity.requestType == 0) "GET"
        else "POST"

        holder.requestType.text = requestType
        holder.url.text = responseEntity.responseBody
        holder.requestCode.text = responseEntity.responseCode.toString()
    }

    override fun getItemCount(): Int {
        return responseEntityList.size
    }

    fun setResponseEntityList(list: ArrayList<ResponseEntity>) {
        responseEntityList = list
        notifyDataSetChanged()
    }


    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val requestType: TextView = itemView.findViewById(R.id.tv_request_type)
        val url: TextView = itemView.findViewById(R.id.tv_url)
        val requestCode: TextView = itemView.findViewById(R.id.tv_request_code)
    }
}