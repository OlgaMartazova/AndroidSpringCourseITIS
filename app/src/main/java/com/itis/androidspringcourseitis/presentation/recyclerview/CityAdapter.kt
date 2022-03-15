package com.itis.androidspringcourseitis.presentation.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.itis.androidspringcourseitis.domain.entity.Weather

class CityAdapter(
    private val list: ArrayList<Weather>,
    private val glide: RequestManager,
    private val selectCity: (Int) -> Unit
) : RecyclerView.Adapter<CityHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityHolder = CityHolder.create(parent, glide, selectCity)

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}