package com.devpraskov.skyengdictionary.presentation.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devpraskov.skyengdictionary.R
import com.devpraskov.skyengdictionary.models.MeaningUi
import com.devpraskov.skyengdictionary.utils.getColorCompat
import com.devpraskov.skyengdictionary.utils.visibilityGone
import kotlinx.android.synthetic.main.details_layout.view.*

class DetailsListAdapter() : ListAdapter<MeaningUi, DetailsListAdapter.DetailsViewHolder>(
    object : DiffUtil.ItemCallback<MeaningUi>() {
        override fun areItemsTheSame(oldItem: MeaningUi, newItem: MeaningUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MeaningUi, newItem: MeaningUi): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DetailsViewHolder(inflater.inflate(R.layout.details_layout, parent, false))
    }

    override fun onBindViewHolder(holder: DetailsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(model: MeaningUi) {
            with(itemView) {
                word.text = model.text
                note.text = model.note
                note?.visibilityGone(model.note.isNotEmpty())
                when (model.frequencyPercent) {
                    in 34..66 ->
                        mediumFrequency?.setBackgroundColor(context.getColorCompat(R.color.first_color))
                    in 67..100 -> {

                        mediumFrequency?.setBackgroundColor(context.getColorCompat(R.color.first_color))
                        highFrequency?.setBackgroundColor(context.getColorCompat(R.color.first_color))
                    }
                    else -> {}
                }
            }
        }
    }
}