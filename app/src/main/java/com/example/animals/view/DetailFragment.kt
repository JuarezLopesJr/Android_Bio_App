package com.example.animals.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.animals.R
import com.example.animals.databinding.FragmentDetailBinding
import com.example.animals.model.AnimalModel
import com.example.animals.model.AnimalPalette


class DetailFragment : Fragment() {

    var animal: AnimalModel? = null

    /* this FragmentDetailBinding comes from fragment_detail
        <data>
        <variable
                name="animal"
                type="com.example.animals.model.AnimalModel"/>
    </data>
    */
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // connecting the layout fragment_detail.xml with the view and binding the data
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail, container,
            false
        )

        return dataBinding.root
    }

    // Retrieving the animal information passed by the Navigation method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            animal = DetailFragmentArgs.fromBundle(it).animal
        }

        // setting the values in item_animal.xml layout file
        /*
        This code was replaced by the loadImage function in Util.kt, using data binding annotation
        @BindingAdapter("android:imageUrl"), the value passed will be used in fragment_detail as
        a ImageView identifier to pass the image
        context?.let {
            dataBinding.animalFragDetailLayout.animalImage
                .loadImage(animal?.imageUrl, getProgressDrawable(it))
        }*/

        /*
        Not using DataBinding
        animalName.text = animal?.name
        animalLocation.text = animal?.location
        animalLifeSpan.text = animal?.lifeSpan
        animalDiet.text = animal?.diet*/
        // Using the Palette to dynamically set the background
        animal?.imageUrl?.let {
            setupBackgroundColor(it)
        }
        /* binding the animal variable name in fragment_detail.xml to the AnimalModel*/
        dataBinding.animal = animal
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
                            dataBinding.palette = AnimalPalette(intColor)

                            /* this code was replaced with the data biding syntax above
                            dataBinding.animalFragDetailLayout.setBackgroundColor(intColor)*/
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