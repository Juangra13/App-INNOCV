package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.business.model.User
import com.example.myapplication.constants.GeneralConstants
import com.example.myapplication.databinding.ItemUserBinding

class UserAdapter(private val users: ArrayList<User>) : RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(layoutInflater.inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int = users.size
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
    }
}

class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemUserBinding.bind(view)

    fun bind(user: User) {
        user.apply {
            binding.tvName.text =
                if (name.isNullOrEmpty()) GeneralConstants.UNDEFINED_TEXT else name
            binding.tvName.tag = id
            binding.tvDateBirthday.text = birthdate
        }
    }
}