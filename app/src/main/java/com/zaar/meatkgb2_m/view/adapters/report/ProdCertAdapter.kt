package com.zaar.meatkgb2_m.view.adapters.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zaar.meatkgb2_m.R
import com.zaar.meatkgb2_m.data.RecordPreVSD

open class ProdCertAdapter(
    private val items: List<RecordPreVSD>,
    private var itemOnClickListener: ItemOnClickListener
): RecyclerView.Adapter<ProdCertAdapter.MyViewHolder>() {

    fun getItems():List<RecordPreVSD>{
        return items
    }

    interface ItemOnClickListener {
        fun onItemClick(checkBox: CheckBox?, item: RecordPreVSD)
    }

    class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val tvDateProduced: TextView = itemView.findViewById(R.id.tv_date_produced_listView)
        val tvNameProduct: TextView = itemView.findViewById(R.id.tv_name_product_listView)
        val tvCount: TextView = itemView.findViewById(R.id.tv_count_listView)
        val tvTimeProduced: TextView = itemView.findViewById(R.id.tv_time_produced_listView)
        val checkboxVsdCreated: CheckBox = itemView.findViewById(R.id.checkbox_prodCertIsCreated)
        val tvIdRecord: TextView = itemView.findViewById(R.id.tv_id_listView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_view_records_for_shops,
            parent, false
        )
        itemView.isClickable = true
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val preVsd = items[position]
        holder.tvDateProduced.text = preVsd.date_produced
        holder.tvNameProduct.text = preVsd.productName
        holder.tvCount.text = preVsd.m_count.toString()
        holder.tvTimeProduced.text = preVsd.time_produced
        holder.checkboxVsdCreated.isChecked = preVsd.vsd_isCreated
        holder.tvIdRecord.text = preVsd.id.toString()
        holder.itemView.setOnClickListener { v: View? ->
            itemOnClickListener.onItemClick(
                v?.findViewById(R.id.checkbox_prodCertIsCreated),
                preVsd
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}