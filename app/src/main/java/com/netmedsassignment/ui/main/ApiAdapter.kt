package com.netmedsassignment.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.netmedsassignment.R
import com.netmedsassignment.database.entities.ApiItem
import kotlinx.android.synthetic.main.row_item.view.*

class ApiAdapter(var context: Context,  var testApiDatas: ArrayList<ApiItem>, var onItemSaveListener: OnItemSaveListener) : RecyclerView.Adapter<ApiAdapter.ViewHolder>() {

    interface OnItemSaveListener{
        fun onItemChecked(isChecked: Boolean, data: ApiItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = testApiDatas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(testApiDatas[position], position)
    }

    fun updateData(datas: List<ApiItem>) {
        testApiDatas.clear()
        testApiDatas.addAll(datas)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(responseData: ApiItem, position: Int) {
            itemView.text_id.text = responseData.itemId
            itemView.text_name.text = responseData.itemName
            itemView.text_price.text = responseData.minPrice.toString()
            itemView.checkbox.isChecked = responseData.isSelected

            itemView.checkbox.setOnClickListener {
                val apiItem = responseData.copy(isSelected = itemView.checkbox.isChecked)
                testApiDatas[position] = apiItem
                notifyItemChanged(position)
                onItemSaveListener.onItemChecked(itemView.checkbox.isChecked, apiItem)
            }
        }
    }
}