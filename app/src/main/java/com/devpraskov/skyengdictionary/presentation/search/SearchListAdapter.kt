package com.devpraskov.skyengdictionary.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devpraskov.skyengdictionary.R
import com.devpraskov.skyengdictionary.models.SearchUiModel
import kotlinx.android.synthetic.main.search.view.*

class SearchListAdapter(val onClick: (SearchUiModel) -> Unit) :
    ListAdapter<SearchUiModel, SearchListAdapter.SearchViewHolder>(
        object : DiffUtil.ItemCallback<SearchUiModel>() {
            override fun areItemsTheSame(oldItem: SearchUiModel, newItem: SearchUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SearchUiModel,
                newItem: SearchUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SearchViewHolder(inflater.inflate(R.layout.search, parent, false))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: SearchUiModel) {
            with(itemView) {
                setOnClickListener { onClick.invoke(model) }
                textView.text = model.word
            }
        }
    }
}