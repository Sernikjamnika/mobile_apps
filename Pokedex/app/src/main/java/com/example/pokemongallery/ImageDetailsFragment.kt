package com.example.pokemongallery

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_image_details.*
import java.io.Serializable


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ImageDetailsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ImageDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ImageDetailsFragment : Fragment() {

    var index: Int = 0
    var rating: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.d("RATING_one", rating.toString())
        if (arguments != null) {
            Log.d("hjamnik", "ARGUMENTS")
            val image = arguments!!.get("pokemonImage") as PokemonImage
            index = arguments!!.get("index") as Int

            rating = image.rating
            Picasso.get().load(image.url).into(fullImage)
            description.text = image.description
            name.text = image.name
        }

        Log.d("RATING_two", rating.toString())
        ratingBar.rating = rating

        ratingBar.setOnRatingBarChangeListener{ _, rating, _ ->
            Log.d("PRE_CHANGE", this.rating.toString())
            this.rating = rating
            Log.d("POST_CHANGE", this.rating.toString())

        }

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment ImageDetailsFragment.
         */
        @JvmStatic
        fun newInstance(index: Int, image: Serializable): ImageDetailsFragment{
            val fragment = ImageDetailsFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            bundle.putSerializable("pokemonImage", image)
            fragment.arguments = bundle
            return fragment
        }
    }
}
