package com.example.pokemongallery

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ImageDetailsActivity : AppCompatActivity() {

    var imageDetailsFragment = ImageDetailsFragment()
    var index = 0
    var rating = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        if (savedInstanceState != null) {
            Log.d("SAVED", savedInstanceState.getFloat("rating").toString())
            rating = savedInstanceState.getFloat("rating")
            (intent.getSerializableExtra("pokemonImage") as PokemonImage).rating = rating
            index = savedInstanceState.getInt("index")
        }


        imageDetailsFragment = ImageDetailsFragment.newInstance(
            intent.getIntExtra("index", 0),
            intent.getSerializableExtra("pokemonImage"))

//        supportFragmentManager.beginTransaction().remove(imageDetailsFragment).commit()
//        supportFragmentManager.beginTransaction().add(R.id.fragmentFrame, imageDetailsFragment).commit()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentFrame, imageDetailsFragment).commit()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState!!.putFloat("rating", imageDetailsFragment.rating)
        outState.putInt("index", imageDetailsFragment.index)
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("index", imageDetailsFragment.index)
        intent.putExtra("rating", imageDetailsFragment.rating)
        setResult(Activity.RESULT_OK, intent)
        finish()
        super.onBackPressed()
    }

}
