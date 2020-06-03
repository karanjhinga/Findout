package com.karan.findout.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.karan.findout.R
import com.karan.findout.databinding.FragmentRestaurantDetailBinding
import com.karan.findout.domain.usecase.AddToFavouriteUseCase
import com.karan.findout.domain.usecase.IsFavouriteUseCase
import com.karan.findout.domain.usecase.RemoveFromFavouriteUseCase
import com.karan.findout.ui.base.BaseFragment
import com.karan.findout.utils.showSnackBar
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RestaurantDetailFragment : BaseFragment<FragmentRestaurantDetailBinding>() {

    @Inject
    lateinit var isFavouriteUseCase: IsFavouriteUseCase

    @Inject
    lateinit var removeFromFavouriteUseCase: RemoveFromFavouriteUseCase

    @Inject
    lateinit var addToFavouriteUseCase: AddToFavouriteUseCase

    private val args: RestaurantDetailFragmentArgs by navArgs()

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRestaurantDetailBinding =
        FragmentRestaurantDetailBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireBinding {
            restaurant = args.restaurant

            zomatoBtn.setOnClickListener {
                val action = Uri.parse(args.restaurant.deepLink)
                val zomatoIntent = Intent(Intent.ACTION_VIEW, action)
                if (zomatoIntent.resolveActivity(requireContext().packageManager) != null) {
                    startActivity(zomatoIntent)
                } else {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.restaurant.zomatoLink)))
                }
            }

            val photos = restaurant?.photos?.map { it.photo }
            if (photos.isNullOrEmpty()) {
                morePhotos.visibility = View.GONE
            } else {
                val photoAdapter = PhotoAdapter()
                photoList.adapter = photoAdapter
                photoAdapter.submitList(photos)
            }

            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }

            favourite.setOnClickListener {
                if (favouritesCb.isChecked) {
                    removeFromFavourite()
                } else {
                    addToFavourite()
                }
            }
        }
    }

    private fun removeFromFavourite() {
        lifecycleScope.launch(IO) {
            removeFromFavouriteUseCase(args.restaurant.id)
            withContext(Main) {
                showSnackBar(getString(R.string.removed_from_favourites))
                binding?.favouritesCb?.isChecked = false
            }
        }
    }

    private fun addToFavourite() {
        lifecycleScope.launch(IO) {
            addToFavouriteUseCase(args.restaurant)
            withContext(Main) {
                showSnackBar(getString(R.string.added_to_favourites))
                binding?.favouritesCb?.isChecked = true
            }
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch(IO) {
            val isFavourite = isFavouriteUseCase(args.restaurant.id)
            withContext(Main) {
                binding?.favouritesCb?.isChecked = isFavourite
            }
        }
    }
}
