package com.example.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.R
import com.example.animals.databinding.ItemAnimalBinding
import com.example.animals.model.AnimalModel

class AnimalListAdapter(private val animalList: ArrayList<AnimalModel>) :
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {

    class AnimalViewHolder(var view: ItemAnimalBinding) : RecyclerView.ViewHolder(view.root)

    fun updateAnimalList(newAnimalList: List<AnimalModel>) {
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged() // this function refresh the whole view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil
            .inflate<ItemAnimalBinding>(inflater, R.layout.item_animal, parent, false)

        return AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
//        holder.view.animalName.text = animalList[position].name
        // using the loadImage extension function to load images in the screen
        /*holder.view.animalImage
            .loadImage(animalList[position].imageUrl, getProgressDrawable(holder.view.context))*/

        // animal is from <variable name="animal"/> inside item_animal.xml
        holder.view.animal = animalList[position]
        holder.view.listener = this // replacing the code below

        /*holder.view.animalLayout.setOnClickListener {
            val action = ListFragmentDirections
                .actionGoToDetail(animalList[position])

            Navigation.findNavController(holder.view).navigate(action)
        }*/
    }

    /* implementing the function from AnimalClickListener interface
    replacing the code above */
    override fun onClick(v: View) {
        for (animal in animalList) {
            if (v.tag == animal.name) {
                val action = ListFragmentDirections
                    .actionGoToDetail(animal)

                Navigation.findNavController(v).navigate(action)
            }
        }
    }
}