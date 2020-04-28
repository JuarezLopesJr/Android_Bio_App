package com.example.animals.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.animals.R
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
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
}
