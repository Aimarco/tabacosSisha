package com.example.tabacosshishaspain.recyclerView

import Models.MarcaTabaco
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

    var tabacos: MutableList<MarcaTabaco>  = ArrayList()
    lateinit var context: Context
    fun RecyclerAdapter(superheros : MutableList<MarcaTabaco>, context: Context){
        this.tabacos = superheros
        this.context = context
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = tabacos.get(position)
        holder.bind(item, context)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.cardtabacos, parent, false))
    }
    override fun getItemCount(): Int {
        return tabacos.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreTabaco = view.findViewById(R.id.nombreTabaco) as TextView
        val imagenTabaco = view.findViewById(R.id.imgTabaco) as ImageButton
        fun bind(tabaco: MarcaTabaco, context: Context){
            nombreTabaco.text = tabaco.nombre
            imagenTabaco.loadUrl(tabaco.imagen)
            imagenTabaco.setOnClickListener(View.OnClickListener { Toast.makeText(context, tabaco.nombre, Toast.LENGTH_SHORT).show() })
        }
        fun ImageView.loadUrl(url: Int) {
            Picasso.with(context).load(url).into(this)
        }
    }
}