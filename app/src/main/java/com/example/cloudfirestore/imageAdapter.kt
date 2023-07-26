package com.example.cloudfirestore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudfirestore.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class imageAdapter(private var list: List<String>):RecyclerView.Adapter<imageAdapter.imageViewholder>() {
    class imageViewholder(var binding: ListItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): imageViewholder {
       return imageViewholder( ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: imageViewholder, position: Int) {
        with(holder.binding){
            with(list[position]){
                Picasso.get().load(this).into(imageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}