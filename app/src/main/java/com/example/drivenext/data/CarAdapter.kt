package com.example.drivenext.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.drivenext.R

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val model: TextView = view.findViewById(R.id.text_model)
        val brand: TextView = view.findViewById(R.id.text_brand)
        val price: TextView = view.findViewById(R.id.text_price)
        val pricePerDay: TextView = view.findViewById(R.id.text_price_per_day)
        val transmission: TextView = view.findViewById(R.id.text_transmission)
        val fuel: TextView = view.findViewById(R.id.text_fuel)
        val image: ImageView = view.findViewById(R.id.image_car)
        val bookButton: Button = view.findViewById(R.id.btn_book)
        val detailButton: Button = view.findViewById(R.id.btn_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_car, parent, false)
        return CarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.model.text = car.model
        holder.brand.text = car.brand
        holder.price.text = "${car.pricePerDay}Р"
        holder.transmission.text = car.transmission
        holder.fuel.text = car.fuelType
        holder.image.setImageResource(car.imageResId)

        // Кнопки можно обработать позже
        holder.bookButton.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Забронировано: ${car.model}", Toast.LENGTH_SHORT).show()
        }

        holder.detailButton.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Детали: ${car.model}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = cars.size
}
