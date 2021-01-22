package com.example.tabacosshishaspain.Activities

import Data.DataDbHelper
import Models.MarcaTabaco
import Models.SaboresTabaco
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabacosshishaspain.R
import com.example.tabacosshishaspain.recyclerView.RecyclerAdapter
import com.example.tabacosshishaspain.recyclerView.SaboresAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.io.Serializable

class listaSaboresActivity : AppCompatActivity() {
    lateinit var mRecyclerView : RecyclerView
    val mAdapter : SaboresAdapter = SaboresAdapter()
lateinit var listaSabores : MutableList<SaboresTabaco>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listasabores)
        // Get the Intent that started this activity and extract the string
       val  intent: Intent = getIntent();
        val sabor :MarcaTabaco  = intent.getSerializableExtra("EXTRA_SABOR") as MarcaTabaco

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.txtSabor)
            textView.text = sabor?.nombre

        val db = DataDbHelper(this)
        //db.insertTabacos()
        listaSabores = db.getSabores(sabor.id)
        setUpRecyclerView()
    }

    fun setUpRecyclerView(){

        mRecyclerView = findViewById(R.id.recyclerViewSabores) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this,1)
        mAdapter.RecyclerAdapter(listaSabores, this)
        mRecyclerView.adapter = mAdapter

    }
}