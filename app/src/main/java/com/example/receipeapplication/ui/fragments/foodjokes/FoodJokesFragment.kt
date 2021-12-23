package com.example.receipeapplication.ui.fragments.foodjokes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.protobuf.Empty
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.receipeapplication.R
import com.example.receipeapplication.databinding.FragmentFoodJokesBinding
import com.example.receipeapplication.util.Constants.Companion.API_KEY
import com.example.receipeapplication.util.NetworkResult
import com.example.receipeapplication.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokesFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding: FragmentFoodJokesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodJokesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel


        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner, {response->
            when(response){
                is NetworkResult.Success ->{
                    binding.foodJokeTextView.text = response.data?.text
                }
                is NetworkResult.Error ->{
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }is NetworkResult.Loading ->{
                Log.d("FoodJokeFragment", "Loading")
                    //binding.progressBar.visibility = VISIBLE
                }
            }
        })

        return binding.root
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readFoodJoke.observe(viewLifecycleOwner,{database->
                if(database.isNullOrEmpty() && database != null ){
                    binding.foodJokeTextView.text = database[0].text
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}