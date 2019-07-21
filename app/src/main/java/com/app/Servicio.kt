package com.app

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.location.Location
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*
import android.app.NotificationManager
import android.R
import android.app.NotificationChannel
import android.app.Service
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat


class Servicio : Service() {

    // fused location
    private lateinit var fusedLocation: FusedLocation

    // para que omita la verificacion de los permisos
    @SuppressLint("MissingPermission")
    // equivalente al constructor del servicio
    override fun onCreate() {


        super.onCreate()

        fusedLocation = FusedLocation(this)

    }


    // para hacer binding con servicios .. cosa q no usamos
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    // inicio del sistela luego de la onCreate
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
// verifica que usamos android O o superior por lo de las limitaciones de servicio
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // para eludir las limitaciones  se crea un servicio de fondo con NotificationChannel
            val name = "nombre"
            val descriptionText = "descripcion"
            val importance = NotificationManager.IMPORTANCE_DEFAULT


            val channel = NotificationChannel("myapp", name, importance).apply {
                description = descriptionText
            }
            // Registra el canal al gestor de notificaciones del sistema
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)

            // crea la notificacion
            val mBuilder = NotificationCompat.Builder(this, "myapp")
                .setSmallIcon(android.R.drawable.sym_def_app_icon) // notification icon
                .setContentTitle("titulo") // title for notification
                .setContentText("texto")
                .setAutoCancel(false)
                .setOngoing(true)
                .build()
            // inicia la notificacion como servicio de fondo
            startForeground(1, mBuilder)


        }

        inicia()
        return Service.START_NOT_STICKY
    }

    // inicia el rastreo
    public fun inicia() {
        fusedLocation.inicia()
    }

    // lo para
    public fun parar() {
        fusedLocation.parar()
    }

    // destructor del servicio
    override fun onDestroy() {
        super.onDestroy()
        parar()
    }

}
