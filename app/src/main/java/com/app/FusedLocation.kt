package com.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener

class FusedLocation  {

    // Peticion de ubicacion junto a metadatos
    private  var request: LocationRequest
    // cliente de google play Locations services
    private  var fusedClient: FusedLocationProviderClient
    // objeto q guarda la referencia al evento del rastreo
    private  var metodo: LocationCallback

    @SuppressLint("MissingPermission")
    constructor(context: Context) {

        request = LocationRequest()
        // obtiene la instancia del servicio de localizacion del sistema
        fusedClient = LocationServices.getFusedLocationProviderClient(context)

        // configuramos la peticion
        request = LocationRequest.create()?.apply {
            interval = 10000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }!!


        // configuramos el metodo
        metodo = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                for (loc in locationResult.locations) {

                    //Logica de la aplicacion
                    Toast.makeText(
                        context, loc.longitude.toString() +
                                " " + loc.latitude + " Speed:" +
                                loc.speed +
                                "Angulo: " + loc.bearing, Toast.LENGTH_LONG
                    ).show()
                    Log.d("ubi", "2")
                }
            }
        }

    }
    @SuppressLint("MissingPermission")
    public fun inicia(){
        fusedClient.requestLocationUpdates(request,metodo,null)
    }
    @SuppressLint("MissingPermission")
    public fun parar(){
        fusedClient.removeLocationUpdates( metodo)
    }


}