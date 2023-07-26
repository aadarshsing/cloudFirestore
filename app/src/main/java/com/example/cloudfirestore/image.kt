package com.example.cloudfirestore

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cloudfirestore.databinding.ActivityImageBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class image : AppCompatActivity() {
    private lateinit var binding: ActivityImageBinding
    private lateinit var firestore: FirebaseFirestore
    private var list = mutableListOf<String>()
    private lateinit var adapter: imageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = imageAdapter(list)
        binding.recyclerView.adapter = adapter
        getImages()


    }

    private fun getImages() {
        firestore.collection("images")
            .get().addOnSuccessListener {
                for (i in it){
                    list.add((i.data["pic"].toString()))
                }
                adapter.notifyDataSetChanged()
            }
    }
}