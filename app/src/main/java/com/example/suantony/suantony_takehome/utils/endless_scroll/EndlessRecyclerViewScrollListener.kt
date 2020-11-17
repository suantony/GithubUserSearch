package com.example.suantony.suantony_takehome.utils.endless_scroll

import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerViewScrollListener(private val layoutManager: RecyclerView.LayoutManager) :
    RecyclerView.OnScrollListener() {
    private var currentPage = 1
    private var isScrolling = false
    private var noDataToLoad = false
    var startingIndexPage = 1
    val startingIndexPerPage = 20


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
            isScrolling = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        var firstVisibleItemPosition = 0
        val currentItem = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount

        if (layoutManager is LinearLayoutManager) {
            firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        }

        if (isScrolling && (currentItem + firstVisibleItemPosition == totalItemCount)) {
            if (dy > 0) {
                if (!noDataToLoad) {
                    isScrolling = false
                    currentPage++
                    loadMore(currentPage, startingIndexPerPage, recyclerView)
                }
            }
        }
    }

    fun resetState() {
        this.currentPage = this.startingIndexPage
        this.noDataToLoad = false
    }

    //used when load data error
    fun holdState() {
        if (currentPage > 1) {
            this.currentPage--
        }
    }

    fun noDataToLoad() {
        this.noDataToLoad = true
    }

    abstract fun loadMore(page: Int, perPage: Int, view: RecyclerView)
}