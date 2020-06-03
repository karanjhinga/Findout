package com.karan.findout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.karan.findout.R
import com.karan.findout.data.model.Restaurant
import com.karan.findout.databinding.FragmentHomeBinding
import com.karan.findout.ui.base.BaseFragment
import com.karan.findout.utils.Sort
import com.karan.findout.utils.Tags
import com.karan.findout.utils.showSnackBar
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by activityViewModels { factory }
    private val adapter = RestaurantAdapter(::onCuisineClick, ::onRestaurantClick)

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        requireBinding {
            lifecycleOwner = this@HomeFragment
            vm = viewModel

            search.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.search(search.text.toString())
                }
                false
            }

            favourite.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionViewFavourites())
            }

            sortBtn.setOnClickListener {
                SortBottomSheetDialog(factory).show(parentFragmentManager, Tags.SORT_DIALOG)
            }

            sortBtn.setOnCloseIconClickListener {
                viewModel.sortBy(Sort.NONE)
            }

            cuisineFilter.setOnCloseIconClickListener {
                viewModel.filterByCuisine(null)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            requireBinding {
                when (state) {
                    is ViewState.NoInternet -> {
                        restaurantList.visibility = View.GONE
                        noDataIllustration.setImageResource(R.drawable.no_connection)
                        noDataMessage.setText(R.string.internet_error)
                        noDataGrp.visibility = View.VISIBLE
                    }
                    is ViewState.NoResults -> {
                        restaurantList.visibility = View.GONE
                        noDataIllustration.setImageResource(R.drawable.no_search_results)
                        noDataMessage.setText(R.string.no_results)
                        noDataGrp.visibility = View.VISIBLE
                    }
                    is ViewState.Success -> {
                        if (state.cuisine == null) {
                            cuisineFilter.visibility = View.GONE
                        } else {
                            cuisineFilter.text = state.cuisine
                            cuisineFilter.visibility = View.VISIBLE
                        }

                        noDataGrp.visibility = View.GONE
                        restaurantList.visibility = View.VISIBLE
                        adapter.submitList(state.items)
                    }
                }
            }
        })

        viewModel.snackBarEvent.observe(viewLifecycleOwner, Observer {
            showSnackBar(it)
        })

        viewModel.sort.observe(viewLifecycleOwner, Observer { sort ->
            requireBinding {
                sortBtn.setText(sort.text)
                sortBtn.isCloseIconVisible = (sort != Sort.NONE)
            }
        })
    }

    private fun setupRecyclerView() {
        requireBinding {
            restaurantList.adapter = adapter

            restaurantList.setOnScrollChangeListener { _, _, _, _, _ ->
                (restaurantList.layoutManager as LinearLayoutManager).apply {
                    if (findLastCompletelyVisibleItemPosition() == itemCount - 1) {
                        viewModel.loadMoreResults()
                    }
                }
            }
        }
    }

    private fun onCuisineClick(cuisine: String) {
        viewModel.filterByCuisine(cuisine)
    }

    private fun onRestaurantClick(restaurant: Restaurant) {
        findNavController().navigate(HomeFragmentDirections.actionShowDetails(restaurant))
    }
}
