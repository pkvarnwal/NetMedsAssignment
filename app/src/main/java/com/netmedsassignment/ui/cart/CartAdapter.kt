package com.netmedsassignment.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.netmedsassignment.R
import com.netmedsassignment.database.entities.CartItem
import kotlinx.android.synthetic.main.row_item.view.*

class CartAdapter(var context: Context, var datas: List<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(datas[position], position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //To show data on each item
        fun bindData(responseData: CartItem, position: Int) {
            itemView.text_id.text = responseData.itemId
            itemView.text_name.text = responseData.itemName
            itemView.text_price.text = responseData.minPrice.toString()
            itemView.checkbox.visibility = View.GONE

        }
    }
}