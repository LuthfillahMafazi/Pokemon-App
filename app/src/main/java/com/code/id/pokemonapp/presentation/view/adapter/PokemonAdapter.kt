package com.code.id.pokemonapp.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.code.id.pokemonapp.databinding.ItemTextBinding
import com.code.id.pokemonapp.domain.model.PokemonItem

class PokemonAdapter(val onSelected: (PokemonItem) -> Unit) :
    ListAdapter<PokemonItem, PokemonAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<PokemonItem>() {
            override fun areItemsTheSame(oldItem: PokemonItem, newItem: PokemonItem): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(
                oldItem: PokemonItem,
                newItem: PokemonItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ViewHolder(val binding: ItemTextBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(data: PokemonItem) {
            binding.apply {
                tvName.text = data.name

                itemView.setOnClickListener {
                    onSelected.invoke(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    override fun submitList(list: MutableList<PokemonItem>?) {
        super.submitList(list?.map { it.copy() })
    }
}