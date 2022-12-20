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
    companion object {
        private const val MaxNumber = "max_number"
        private const val MinNumber = "min_number"

    }
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
                    Toast.makeText(this, getString(R.string.access_firebase) ,
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, getString(R.string.no_access_firebase),
                        Toast.LENGTH_SHORT).show()
                }
            }

        val getMaxNumValue: String = Firebase.remoteConfig.getString(MaxNumber);
        val getMinNumValue: String = Firebase.remoteConfig.getString(MinNumber);

        textViewResultado.text= getMinNumValue.toString()

        if(getMaxNumValue == ""){
            Toast.makeText(this, getString(R.string.empty_values),
                Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,getString(R.string.not_empty_values) ,
                Toast.LENGTH_SHORT).show()
        }

        btnCLick.setOnClickListener {
            val myRandomValues = Random.nextInt(getMinNumValue.toInt(), getMaxNumValue.toInt())
            textViewResultado.text= myRandomValues.toString()
        }

    }
}