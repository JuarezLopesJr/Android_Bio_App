package com.example.animals.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.animals.R
import com.example.animals.model.AnimalModel
import com.example.animals.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {
    // instantiating from ListViewModel.kt
    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    private val animalListDataObserver = Observer<List<AnimalModel>> { list ->
        list?.let {
            animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }

    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            listError.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        listError.visibility = if (isError) View.VISIBLE else View.GONE
        if (isError) {
            loadingView.visibility = View.GONE
            animalList.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*this method controls the view model lifecycle for the live data and persist the state
            when the app rotates. the file is ListViewModel.kt
        */
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        // observe live data variables
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)
        viewModel.refresh()
        // defining the layout display for the app
        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()

            refreshLayout.isRefreshing = false
        }
    }
}


/*// binding the fragment navigation xml files
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       super.onViewCreated(view, savedInstanceState)
       *//* in this listener, put the direction which it'll navigate, in this list file i'll navigate
            to detail fragment, that's why i'm passing actionGoToDetail and setting the btnDetail button
         *//*
        btnDetail.setOnClickListener {
            val action = ListFragmentDirections.actionGoToDetail()
            Navigation.findNavController(it).navigate(action)
        }
    }*/