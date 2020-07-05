package com.devpraskov.skyengdictionary.presentation.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SimpleItemAnimator
import com.devpraskov.skyengdictionary.R
import com.devpraskov.skyengdictionary.models.SearchUiModel
import com.devpraskov.skyengdictionary.presentation.details.DetailsActivity
import com.devpraskov.skyengdictionary.utils.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()
    private val adapter = SearchListAdapter(::onItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        initListeners()
        initObservers()
    }

    private fun initView() {
        statusBarColor(getColorCompat(R.color.first_color))
        swipeRefreshLayout?.isEnabled = false
        searchEditText?.requestFocus()
        recyclerView?.apply {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            setHasFixedSize(true)
            adapter = this@SearchActivity.adapter
        }
    }

    private fun initListeners() {
        searchEditText?.debounceAfterTextChanged(lifecycleScope) {
            if (it.isEmpty()) adapter.submitList(listOf())
            else viewModel.search(it)
        }
        searchEditText?.enterClick { if (it.isNotEmpty()) viewModel.search(it) }
        recyclerView?.viewTreeObserver?.addOnScrollChangedListener {
            header?.isSelected = recyclerView?.canScrollVertically(-1) ?: false
        }
        searchEditText?.doAfterTextChanged {
            if (!it?.toString().isNullOrEmpty()) clearIcon.showAnimateAlpha()
            else clearIcon.hideAnimateAlpha()
        }
        clearIcon?.onClick {
            searchEditText?.setText("")
            searchEditText?.clearFocus()
            adapter.submitList(listOf())
            hideSoftKeyboard(searchEditText)
        }
    }

    private fun onItemClick(model: SearchUiModel) {
        DetailsActivity.start(this, model)
    }

    private fun initObservers() {
        viewModel.getLiveData().observe(this, Observer { action ->
            when (action) {
                is SearchAction.Success -> {
                    adapter.submitList(action.model)
                    notFound?.visibility(action.model.isEmpty())
                    swipeRefreshLayout?.isRefreshing = false
                }
                is SearchAction.Error -> {
                    Snackbar.make(mainContainer, action.error, Snackbar.LENGTH_LONG).show()
                    swipeRefreshLayout?.isRefreshing = false
                }
                is SearchAction.Loading -> {
                    swipeRefreshLayout?.isRefreshing = true
                }
            }
        })
    }
}
