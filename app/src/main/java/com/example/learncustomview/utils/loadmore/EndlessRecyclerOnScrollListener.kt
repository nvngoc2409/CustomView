package com.example.learncustomview.utils.loadmore

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Linh on 13,June,2022
 */
abstract class EndlessRecyclerOnScrollListener (private val _linearLayoutManager : LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var _previousTotal = 0 // The total number of items in the dataset after the last load
    private var _loading = true // True if we are still waiting for the last set of data to load.
    private var _currentPage : Int = 0
    private var _reachedMax = false
    private val _visibleThresholds = 0

    override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = _linearLayoutManager.itemCount
        val firstVisibleItem =_linearLayoutManager.findFirstVisibleItemPosition()
        if (_loading){
            if (totalItemCount > _previousTotal){
                _loading = false
                _previousTotal = totalItemCount
            }
        }

        if(!_loading && totalItemCount - visibleItemCount <= firstVisibleItem + _visibleThresholds){
            if (!_reachedMax){
                _currentPage++
                loadMore(_currentPage)
                _loading = true
            }
        }

    }

    abstract fun loadMore(currentPage: Int)

    fun reset(){
        _previousTotal = 0
        _currentPage = 0
    }

    fun reachedMax (reachedMax: Boolean){
        this._reachedMax = reachedMax
    }

}