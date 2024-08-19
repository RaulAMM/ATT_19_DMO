package com.example.att_19.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.att_19.R
import com.example.att_19.data.repository.UserRepository
import com.example.att_19.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = UserRepository(applicationContext)
        val myFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, myFactory).get(MainViewModel::class.java)

        setupObservers()
        setupListeners()

    }

    private fun setupListeners(){
        binding.buttonSave.setOnClickListener {
            val str = binding.editName.text.toString()
            viewModel.save(str)
        }
    }

    private fun setupObservers(){
        lifecycleScope.launch {
            viewModel.username.collect{
                if(it.isNullOrBlank()){
                    binding.textNessage.text= "Bem-vindo usuario"
                    binding.buttonSave.text= "salvar"
                }else{
                    binding.textNessage.text = "Bem-vindo de volta, $it "
                    binding.buttonSave.text= "alterar"
                }
            }
        }

    }
}