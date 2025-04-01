package com.example.drivenext.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.drivenext.R

class SettingsAdapter(
    private val items: List<SettingsItem>,
    private val onItemClick: (SettingsItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_PROFILE = 0
        const val TYPE_OPTION = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_PROFILE else TYPE_OPTION
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_PROFILE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_setting_profile, parent, false)
            ProfileViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_setting_option, parent, false)
            OptionViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is ProfileViewHolder) {
            holder.bind(item as SettingsItem.Profile)
            holder.itemView.setOnClickListener { onItemClick(item) }
        } else if (holder is OptionViewHolder) {
            holder.bind(item as SettingsItem.Option)
            holder.itemView.setOnClickListener { onItemClick(item) }
        }
    }

    class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name = view.findViewById<TextView>(R.id.text_name)
        private val email = view.findViewById<TextView>(R.id.text_email)
        private val avatar = view.findViewById<ImageView>(R.id.image_avatar)

        fun bind(item: SettingsItem.Profile) {
            name.text = item.name
            email.text = item.email

            Glide.with(itemView.context)
                .load(item.avatarPath) // путь из БД
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .circleCrop()
                .into(avatar)
        }
    }

    class OptionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val icon = view.findViewById<ImageView>(R.id.image_icon)
        private val text = view.findViewById<TextView>(R.id.text_option)

        fun bind(item: SettingsItem.Option) {
            icon.setImageResource(item.iconRes)
            text.text = item.label
        }
    }
}
