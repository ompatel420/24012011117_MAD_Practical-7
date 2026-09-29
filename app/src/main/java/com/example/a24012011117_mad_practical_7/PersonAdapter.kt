package com.example.a24012011117_mad_practical_7

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a24012011117_mad_practical_7.databinding.ItemPersonBinding

class PersonAdapter(
    private val persons: ArrayList<Person>,
    private val onDelete: (Person) -> Unit
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = persons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = persons[position]
        with(holder.binding) {
            textName.text = person.name
            textPhone.text = person.phoneNo
            textEmail.text = person.emailId
            textAddress.text = person.address

            buttonDelete.setOnClickListener {
                val pos = holder.bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onDelete(persons[pos])
                    persons.removeAt(pos)
                    notifyItemRemoved(pos)
                }
            }
        }
    }
}