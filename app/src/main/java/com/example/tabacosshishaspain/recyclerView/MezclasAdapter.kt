package com.example.tabacosshishaspain.recyclerView

import Models.MezclaTabaco
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tabacosshishaspain.R
import com.squareup.picasso.Picasso


class MezclasAdapter : RecyclerView.Adapter<MezclasAdapter.ViewHolder>(){

    var mezclas: MutableList<MezclaTabaco>  = ArrayList()
    lateinit var context: Context
    fun RecyclerAdapter(mezclas : MutableList<MezclaTabaco>, context: Context){
        this.mezclas = mezclas
        this.context = context
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mezclas.get(position)
        holder.bind(item, context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.mezclasrecyclerview, parent, false))
    }
    override fun getItemCount(): Int {
        return mezclas.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val marca1 = view.findViewById(R.id.txtMarca1) as TextView
        val sabor1 = view.findViewById(R.id.txtSabor1) as TextView
        val porc1 = view.findViewById(R.id.txtporc1) as TextView
        val marca2 = view.findViewById(R.id.txtMarca2) as TextView
        val sabor2 = view.findViewById(R.id.txtSabor2) as TextView
        val porc2 = view.findViewById(R.id.txtporc2) as TextView
        fun bind(mezcla: MezclaTabaco, context: Context){
            marca1.text = mezcla.tabaco1
            marca2.text = mezcla.tabaco2
            sabor1.text = mezcla.sabor1
            sabor2.text = mezcla.sabor2
            porc1.text = mezcla.porcentaje1.toString()+"%"
            porc2.text = mezcla.porcentaje2.toString()+"%"
        }
        fun ImageView.loadUrl(url: Int) {
            Picasso.with(context).load(url).into(this)
        }
    }
}