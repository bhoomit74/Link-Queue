package com.bsoft.linkqueue.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsoft.linkqueue.R
import com.bsoft.linkqueue.databinding.ItemListBinding
import com.bsoft.linkqueue.room.LinkItem

class ListItemAdapter(var linkList : ArrayList<LinkItem>,var itemClickListener: ClickListener) : RecyclerView.Adapter<ListItemAdapter.MyViewHolder>() {
    private var binding : ItemListBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_list,parent,false)
        return MyViewHolder(binding!!)

    }

    override fun getItemCount(): Int {
        return linkList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setUpData(linkList[position],itemClickListener)
    }

    fun updateList(listItem: List<LinkItem>){
        linkList.clear()
        linkList.addAll(listItem)
        notifyDataSetChanged()
    }

    class MyViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root){

        fun setUpData(linkItem: LinkItem,itemClickListener: ClickListener){
            binding.apply {
                this.linkItem = linkItem
                this.clickListener = itemClickListener
                executePendingBindings()
            }
        }
    }
}
