package com.karan.findout.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karan.findout.domain.entity.ItemModel
import com.karan.findout.domain.usecase.SearchRestaurantUseCase
import com.karan.findout.utils.*
import com.karan.findout.utils.ApiConstants.QUERY
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.launch

class HomeViewModel @Inject constructor(
    private val searchUseCase: SearchRestaurantUseCase
) : ViewModel() {

    private var page = 1
    private var searchText: String? = null
    private var hasMoreResults = false
    private var cuisine: String? = null

    // loading directly observed by data binding
    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean> = _loading

    // current state of sort
    private val _sort = MutableLiveData<Sort>().apply { value = Sort.NONE }
    val sort: LiveData<Sort> = _sort

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    // single live event for one time messages to user
    val snackBarEvent = SingleLiveEvent<String>()

    private fun updateResults() = viewModelScope.launch {
        _loading.postValue(true)

        searchUseCase(buildParams(), page, cuisine).let { result ->
            when (result) {
                is Result.Success -> {
                    _loading.postValue(false)
                    hasMoreResults = result.data.first
                    val list = result.data.second

                    if (list.isEmpty()) {
                        _viewState.postValue(ViewState.NoResults)
                    } else {
                        _viewState.postValue(ViewState.Success(cuisine, list))
                    }
                }
                is Result.Error -> {
                    when (val e = result.exception) {
                        is IOException -> {
                            _viewState.postValue(ViewState.NoInternet)
                            _loading.postValue(false)
                        }
                        else -> {
                            _loading.postValue(false)
                            snackBarEvent.postValue(e.message ?: SOMETHING_WENT_WRONG)
                        }
                    }
                }
            }
        }
    }

    fun filterByCuisine(cuisine: String?) {
        if (this.cuisine != cuisine) {
            this.cuisine = cuisine
            page = 1
            updateResults()
        }
    }

    fun sortBy(sort: Sort) {
        if (_sort.value != sort) {
            _sort.value = sort
            page = 1
            updateResults()
        }
    }

    fun search(query: String) {
        this.searchText = query
        page = 1
        updateResults()
    }

    fun loadMoreResults() {
        if (hasMoreResults && !loading.value!!) {
            page++
            updateResults()
        }
    }

    private fun buildParams(): MutableMap<String, String> {
        val query = mutableMapOf(
            ApiConstants.LATITUDE to LAT,
            ApiConstants.LONGITUDE to LNG
        )
        query.putAll(_sort.value!!.params)
        searchText?.let { query[QUERY] = it }
        return query
    }
}

sealed class ViewState {
    class Success(val cuisine: String?, val items: List<ItemModel>) : ViewState()
    object NoInternet : ViewState()
    object NoResults : ViewState()
}
