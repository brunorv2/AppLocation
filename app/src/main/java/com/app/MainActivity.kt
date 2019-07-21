package com.app

import android.annotation.SuppressLint
import android.content.Intent

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle

import android.widget.CompoundButton

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var servicio: Intent


    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // servicio encapsulado en un intent
        servicio = Intent(this, Servicio::class.java)

        // solo por comodidad uso un switch .. nada q ver con la logica
        boton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            //inicia/para el servicio
            if (isChecked){

                startService(servicio)

            }
            else {
                stopService(servicio)

            }
        })
    }




}
