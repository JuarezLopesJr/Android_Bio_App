package com.example.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.R
import com.example.animals.model.AnimalModel
import com.example.animals.utils.getProgressDrawable
import com.example.animals.utils.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<AnimalModel>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    class AnimalViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    fun updateAnimalList(newAnimalList: List<AnimalModel>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged() // this function refresh the whole view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)

        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalName.text = animalList[position].name
        // using the loadImage extension function to load images in the screen
        holder.view.animalImage
            .loadImage(animalList[position].imageUrl, getProgressDrawable(holder.view.context))

        holder.view.animalLayout.setOnClickListener {
            val action = ListFragmentDirections
                .actionGoToDetail(animalList[position])

            Navigation.findNavController(holder.view).navigate(action)
        }
    }


}