package com.example.preferencesdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.preferencesdatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    //datastore object
    private lateinit var dataStore: DataStore<Preferences>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //
        dataStore = createDataStore(name = "settings")

        binding.btnRead.setOnClickListener {
            lifecycleScope.launch {
              val value = read(binding.etReadkey.text.toString())
                binding.tvReadValue.text = value ?: "No Value Found"
            }
        }

        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {
                save(
                    binding.etSaveKey.text.toString(),
                    binding.etSaveValue.text.toString()
                )
            }
        }
    }

//save into data store
    private suspend fun save(key:String, value:String){
        val dataStoreKey = preferencesKey<String>(key)
    dataStore.edit { settings->
        settings[dataStoreKey] = value
    }
    }


    //save into data store
    private suspend fun read(key:String):String?{
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}





























