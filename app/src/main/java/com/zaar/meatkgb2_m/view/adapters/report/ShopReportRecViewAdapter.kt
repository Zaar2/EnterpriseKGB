package com.zaar.meatkgb2_m.view.adapters.report

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zaar.meatkgb2_m.R
import com.zaar.meatkgb2_m.data.RecordPreVSD
import com.zaar.meatkgb2_m.data.RecordShopReportOut

open class ShopReportRecViewAdapter(
    private val dailyRecords: List<RecordShopReportOut>? = null,
    private val periodRecords: List<RecordShopReportOut>? = null,
    private val itemOnClickListener: ItemOnClickListener? = null,
    ) : RecyclerView.Adapter<ShopReportRecViewAdapter.MyViewHolder>() {

    interface ItemOnClickListener {
        fun onItemClick(item: RecordShopReportOut)
    }

    class MyViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        val productTextView: TextView = itemView.findViewById(R.id.tv_name)
        val countTextView: TextView = itemView.findViewById(R.id.tv_value)
        val timeProduced: TextView by lazy {
            itemView.findViewById(R.id.tv_timeProduce)
        }
        val countOfDays: TextView by lazy {
            itemView.findViewById(R.id.tv_countOfDays)
        }
        val me: TextView = itemView.findViewById(R.id.tv_me)
    }

    override fun getItemCount(): Int {
        return (dailyRecords ?: periodRecords)?.size ?: 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val idLayout: Int =
            if (periodRecords != null)
                R.layout.item_list_view_report_for_period
            else R.layout.item_list_view_report_for_day
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(idLayout, parent, false)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        tvName.gravity = Gravity.START
        itemView.isClickable = true
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val record: RecordShopReportOut? =
            dailyRecords?.get(position) ?: periodRecords?.get(position)
        record?.also { rec: RecordShopReportOut ->
            holder.productTextView.text = rec.productName
            holder.countTextView.text = rec.count
            holder.me.text = rec.me
            if (dailyRecords != null)
                holder.timeProduced.text = rec.timeProduced
            if (periodRecords != null) {
                holder.countOfDays.text = rec.countDays
            }
            itemOnClickListener?.apply {
                holder.itemView.setOnClickListener {
                    onItemClick(
                        rec
                    )
                }
            }
        }
    }
}