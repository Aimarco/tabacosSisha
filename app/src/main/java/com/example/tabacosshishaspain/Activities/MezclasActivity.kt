package com.example.tabacosshishaspain.Activities

import Data.DataDbHelper
import Models.MarcaTabaco
import Models.MezclaTabaco
import Models.SaboresTabaco
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabacosshishaspain.R
import com.example.tabacosshishaspain.recyclerView.MezclasAdapter
import com.example.tabacosshishaspain.recyclerView.SaboresAdapter

class MezclasActivity : AppCompatActivity() {
    lateinit var mRecyclerView : RecyclerView
    val mAdapter : MezclasAdapter = MezclasAdapter()
    lateinit var listaMezclas : MutableList<MezclaTabaco>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mezclasview)
        // Get the Intent that started this activity and extract the string
        val  intent: Intent = getIntent();

        val db = DataDbHelper(this)
        listaMezclas=db.getMezclas()
        setUpRecyclerView()

    }

    fun setUpRecyclerView(){

        mRecyclerView = findViewById(R.id.recyclerViewMezclas) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this,1)
        mAdapter.RecyclerAdapter(listaMezclas, this)
        mRecyclerView.adapter = mAdapter

    }


}