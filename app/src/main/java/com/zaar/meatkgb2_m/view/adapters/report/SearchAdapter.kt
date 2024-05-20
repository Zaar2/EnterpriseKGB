package com.zaar.meatkgb2_m.view.adapters.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zaar.meatkgb2_m.R
import com.zaar.meatkgb2_m.data.RecordSearchOut

class SearchAdapter(
    private val items: List<RecordSearchOut>,
): RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {
    class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val tvIdRecord: TextView = itemView.findViewById(R.id.tv_id_listView)
        val tvDateProduced: TextView = itemView.findViewById(R.id.tv_date_produced_listView)
        val tvNameProduct: TextView = itemView.findViewById(R.id.tv_name_product_listView)
        val tvCount: TextView = itemView.findViewById(R.id.tv_count_listView)
        val tvTimeProduced: TextView = itemView.findViewById(R.id.tv_time_produced_listView)
        val tvNameUser: TextView = itemView.findViewById(R.id.tv_worker_listView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_view_records_for_search,
            parent, false
        )
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.tvIdRecord.text = item.id.toString()
        holder.tvDateProduced.text = item.dateProduced
        holder.tvNameProduct.text = item.nameProduct
        holder.tvCount.text = item.count.toString()
        holder.tvTimeProduced.text = item.timeProduced.toString()
        holder.tvNameUser.text = item.nameWorker
    }
}