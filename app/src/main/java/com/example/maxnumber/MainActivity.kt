package com.example.maxnumber

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.RemoteConfigConstants
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewResultado: TextView = findViewById(R.id.txt_resultado)
        val btnCLick: Button = findViewById(R.id.btn_click)

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
        }
        remoteConfig.setConfigSettingsAsync(configSettings)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(ContentValues.TAG, "Config params updated: $updated")
                    Toast.makeText(this, "Está acessando o FireBase",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Não está acessando o FireBase",
                        Toast.LENGTH_SHORT).show()
                }
            }

        val getMaxNumValue: String = Firebase.remoteConfig.getString("max_number");
        val getMinNumValue: String = Firebase.remoteConfig.getString("min_number");

        textViewResultado.text= getMinNumValue.toString()

        if(getMaxNumValue == ""){
            Toast.makeText(this, "Valores vazios",
                Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Valores do firebase não estão vazios",
                Toast.LENGTH_SHORT).show()
        }

        btnCLick.setOnClickListener {
            val myRandomValues = Random.nextInt(getMinNumValue.toInt(), getMaxNumValue.toInt())
            textViewResultado.text= myRandomValues.toString()
        }

    }
}