package com.example.suantony.suantony_takehome.ui.main

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suantony.suantony_takehome.R
import com.example.suantony.suantony_takehome.utils.endless_scroll.EndlessRecyclerViewScrollListener
import com.example.suantony.suantony_takehome.utils.viewmodel.ViewModelFactory
import com.example.suantony.suantony_takehome.utils.vo.Status
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    private var loadDataFromSearchText = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var searchText = ""
        val linearLayoutManager = LinearLayoutManager(this)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
        userAdapter = UserAdapter()

        et_search.setOnKeyListener(View.OnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                hideKeyboard(this)
                searchText = et_search.text.toString()
                loadDataFromSearchText = true
                userAdapter.clearUsers()
                scrollListener.resetState()
                loadSearchUsersDataWithPage(
                    searchText,
                    scrollListener.startingIndexPage,
                    scrollListener.startingIndexPerPage
                )
            }
            false
        })

        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun loadMore(page: Int, perPage: Int, view: RecyclerView) {
                loadDataFromSearchText = false
                loadSearchUsersDataWithPage(searchText, page, perPage)
            }
        }

        with(rv_users) {
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            addOnScrollListener(scrollListener)
            this.adapter = userAdapter
        }
    }

    fun loadSearchUsersDataWithPage(username: String, page: Int, perPage: Int) {
        viewModel.setData(username, page.toString(), perPage.toString())
        viewModel.getSearchUsersWithPage().observe(this, Observer { users ->
            if (users != null) {
                when (users.status) {
                    Status.LOADING -> {
                        if (loadDataFromSearchText) {
                            progress_bar.visibility = View.VISIBLE
                        } else {
                            progress_bar_scroll.visibility = View.VISIBLE
                        }
                        tv_response.visibility = View.GONE
                    }

                    Status.SUCCESS -> {
                        hideProgressBar()
                        tv_response.visibility = View.GONE
                        userAdapter.setUsers(users.data)
                        userAdapter.notifyDataSetChanged()
                    }

                    Status.EMPTY -> {
                        if (loadDataFromSearchText) {
                            tv_response.text = users.message
                            tv_response.visibility = View.VISIBLE
                        }
                        hideProgressBar()
                        scrollListener.noDataToLoad()

                    }

                    Status.ERROR -> {
                        if (loadDataFromSearchText) {
                            tv_response.text = users.message
                            tv_response.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(this, users.message, Toast.LENGTH_SHORT).show()
                        }
                        hideProgressBar()
                        scrollListener.holdState()
                    }
                }
            }
        })
    }


    fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideProgressBar() {
        progress_bar.visibility = View.GONE
        progress_bar_scroll.visibility = View.GONE
    }
}