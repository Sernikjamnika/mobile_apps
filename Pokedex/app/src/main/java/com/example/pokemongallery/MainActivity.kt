package com.example.pokemongallery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // gallery column count
    private val SPAN_COUNT = 3

    private val imageList = ArrayList<PokemonImage>()

    lateinit var galleryAdapter: GalleryImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // init adapter
        galleryAdapter = GalleryImageAdapter(imageList)
        // init recyclerview
        recyclerView.layoutManager = GridLayoutManager(this, SPAN_COUNT)
        recyclerView.adapter = galleryAdapter
        if (savedInstanceState == null){
            loadImages()
        } else {
            for (image in savedInstanceState.getSerializable("images") as ArrayList<PokemonImage>) {
                imageList.add(image)
            }
        }
    }
    private fun loadImages() {
        val pokemonUrls = resources.getStringArray(R.array.pokemonImageUrls)
        val pokemonDesc = resources.getStringArray(R.array.pokemonDescriptions)
        val pokemonNames = resources.getStringArray(R.array.pokemonNames)
        for (index in 0 until pokemonUrls.size){
            imageList.add(PokemonImage(
                pokemonUrls[index],
                pokemonDesc[index],
                pokemonNames[index],
                0f))
        }
        galleryAdapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putSerializable("images", imageList)
        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2137 && resultCode == Activity.RESULT_OK){
            val rating = data!!.getFloatExtra("rating", 0f)
            val image =  imageList[data.getIntExtra("index", 0)]
            if (image.rating != rating){
                image.rating = rating
                Toast.makeText(this, "${image.name}\'s rating has changed", Toast.LENGTH_SHORT).show()
                imageList.sortByDescending { pokemonImage -> pokemonImage.rating }
                galleryAdapter.notifyDataSetChanged()
            }
        }
    }

    fun startDetailActivity(index: Int){
        val intent = Intent(this, ImageDetailsActivity::class.java)
        intent.putExtra("index", index)
        intent.putExtra("pokemonImage", imageList[index])
        startActivityForResult(intent, 2137)
    }


}
