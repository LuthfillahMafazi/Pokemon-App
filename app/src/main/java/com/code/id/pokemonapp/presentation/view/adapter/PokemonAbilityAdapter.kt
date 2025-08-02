package com.code.id.pokemonapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.code.id.pokemonapp.databinding.ItemTextBinding
import com.code.id.pokemonapp.domain.model.AbilitiesData

class PokemonAbilityAdapter: ListAdapter<AbilitiesData, PokemonAbilityAdapter.ViewHolder>(COMPARATOR) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class ViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: AbilitiesData) {
            binding.apply {
                tvName.text = data.ability?.name

                itemView.setOnClickListener {
//                    onSelected.invoke(data)
                }
            }
        }
    }

    override fun submitList(list: MutableList<AbilitiesData>?) {
        super.submitList(list?.map { it.copy() })
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<AbilitiesData>() {
            override fun areItemsTheSame(oldItem: AbilitiesData, newItem: AbilitiesData): Boolean {
                return oldItem.ability?.name == newItem.ability?.name
            }

            override fun areContentsTheSame(
                oldItem: AbilitiesData,
                newItem: AbilitiesData
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}