package com.example.tabacosshishaspain.Activities

import Data.DataDbHelper
import Models.MarcaTabaco
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tabacosshishaspain.R

class MezclasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listasabores)
        // Get the Intent that started this activity and extract the string
        val  intent: Intent = getIntent();
        val sabor : MarcaTabaco = intent.getSerializableExtra("EXTRA_SABOR") as MarcaTabaco

        // Capture the layout's TextView and set the string as its text
        val textView = findViewById<TextView>(R.id.txtSabor)
        textView.text = sabor?.nombre

        val db = DataDbHelper(this)
        //db.getMezclas()

    }

}