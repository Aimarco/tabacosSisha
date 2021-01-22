package com.example.tabacosshishaspain.Activities

import Data.DataDbHelper
import Models.MarcaTabaco
import Models.MezclaTabaco
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabacosshishaspain.R
import com.example.tabacosshishaspain.recyclerView.RecyclerAdapter

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var mRecyclerView : RecyclerView
    val mAdapter : RecyclerAdapter = RecyclerAdapter()
    lateinit var listaTabacos : MutableList<MarcaTabaco>
    lateinit var listaMezclas : MutableList<MezclaTabaco>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val db = DataDbHelper(this)
        listaTabacos = db.getTabacos()
        val imgbtnmezclas = findViewById<ImageButton>(R.id.imgbtnmezclas)
        listaMezclas = db.getMezclas()
        System.out.println("NUMERO DE MEZCLAS: "+ listaMezclas.size)
        setUpRecyclerView()
    }


    fun setUpRecyclerView(){

        mRecyclerView = findViewById(R.id.recyclerViewTabacos) as RecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = GridLayoutManager(this,2)
        mAdapter.RecyclerAdapter(listaTabacos, this)
        mRecyclerView.adapter = mAdapter

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun runMezclasIntent(context : Context){

        val intent = Intent(context, MezclasActivity::class.java)
        context.startActivity(intent)

    }
}