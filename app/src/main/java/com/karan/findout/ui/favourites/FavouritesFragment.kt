package com.karan.findout.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.karan.findout.data.model.Restaurant
import com.karan.findout.databinding.FragmentFavouritesBinding
import com.karan.findout.ui.base.BaseFragment
import com.karan.findout.ui.home.RestaurantAdapter
import javax.inject.Inject

class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: FavouritesViewModel by viewModels { factory }
    private val adapter = RestaurantAdapter(null, ::onRestaurantClick)

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFavouritesBinding =
        FragmentFavouritesBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireBinding {
            backBtn.setOnClickListener { findNavController().navigateUp() }
            favouriteList.adapter = adapter
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.favourites.observe(viewLifecycleOwner, Observer {
            requireBinding {
                if (it.isNullOrEmpty()) {
                    noFavourites.visibility = View.VISIBLE
                } else {
                    noFavourites.visibility = View.GONE
                }
                adapter.submitList(it)
            }
        })
    }

    private fun onRestaurantClick(restaurant: Restaurant) {
        findNavController().navigate(FavouritesFragmentDirections.actionShowDetails(restaurant))
    }
}
