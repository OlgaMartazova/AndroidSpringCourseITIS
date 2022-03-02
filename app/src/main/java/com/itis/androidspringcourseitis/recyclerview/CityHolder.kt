package com.itis.androidspringcourseitis.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.androidspringcourseitis.data.model.list.City
import com.itis.androidspringcourseitis.databinding.ItemWeatherBinding

class CityHolder(
    private val binding: ItemWeatherBinding,
    private val selectCity: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var city: City? = null

    init{
        itemView.setOnClickListener {
            city?.id?.also(selectCity)
        }
    }

    fun bind(item: City) {
        city = item
        with(binding) {
            tvName.text = item.name
            tvTemp.text = item.main.temp.toString()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            selectCity: (Int) -> Unit
        ) = CityHolder(
            ItemWeatherBinding.inflate(
                LayoutInflater
                    .from(parent.context),
                parent,
                false
            ),
            selectCity
        )
    }
}
