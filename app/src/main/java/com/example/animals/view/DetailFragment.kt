package com.example.animals.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.animals.R
import com.example.animals.model.AnimalModel
import com.example.animals.utils.getProgressDrawable
import com.example.animals.utils.loadImage
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_animal.animalImage
import kotlinx.android.synthetic.main.item_animal.animalName


class DetailFragment : Fragment() {

    var animal: AnimalModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    // Retrieving the animal information passed by the Navigation method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        // setting the values in item_animal.xml layout file
        context?.let {
            animalImage.loadImage(animal?.imageUrl, getProgressDrawable(it))
        }

        animalName.text = animal?.name
        animalLocation.text = animal?.location
        animalLifeSpan.text = animal?.lifeSpan
        animalDiet.text = animal?.diet

        animal?.imageUrl?.let {
            setupBackgroundColor(it)
        }
    }

    private fun setupBackgroundColor(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Palette.from(resource)
                        .generate() { palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?: 0
                            animalFragDetailLayout.setBackgroundColor(intColor)
                        }
                }
            })
    }
}


/* // binding the fragment navigation xml files
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        *//* in this listener, put the direction which it'll navigate, in this detail file i'll navigate
            to list fragment, that's why i'm passing actionGoToList and setting the btnList button
         *//*
        btnList.setOnClickListener {
            val action = DetailFragmentDirections.actionGoToList()
            Navigation.findNavController(it).navigate(action)
        }
    }*/