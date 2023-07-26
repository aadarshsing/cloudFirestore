package com.example.cloudfirestore

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cloudfirestore.databinding.ActivityImageBinding
import com.example.cloudfirestore.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storageRef: StorageReference
    private var imageUri :Uri ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firestore = FirebaseFirestore.getInstance()
        storageRef = FirebaseStorage.getInstance().reference.child("image")
        binding.imageView.setOnClickListener {
            resultLauncher.launch("image/*")
        }
        binding.uploadBtn.setOnClickListener {
            uploadImage()
        }
        binding.showAllBtn.setOnClickListener {
            startActivity(Intent(this, image::class.java))
        }


    }

    private fun uploadImage() {
        binding.progressBar.visibility = View.VISIBLE
        storageRef =storageRef.child(System.currentTimeMillis().toString())
        imageUri?.let {
            storageRef.putFile(it).addOnCompleteListener { task->
                if(task.isSuccessful){
                    storageRef.downloadUrl.addOnSuccessListener { uri->
                        val map = HashMap<String,Any>()
                        map["pic"] = uri.toString()
                        firestore.collection("images").add(map).addOnCompleteListener {firestoreTask->
                            if (firestoreTask.isSuccessful){
                                Snackbar.make(binding.root, "uploaded successfully", Snackbar.LENGTH_SHORT).show()
                            }else{
                                Snackbar.make(binding.root, firestoreTask.exception?.message.toString(), Snackbar.LENGTH_SHORT).show()
                            }
                            binding.progressBar.visibility = View.GONE
                            binding.imageView.setImageResource(R.drawable.vector)

                        }

                    }
                }else{
                    Snackbar.make(binding.root, task.exception?.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()){
        imageUri = it
        binding.imageView.setImageURI(it)

    }

}

