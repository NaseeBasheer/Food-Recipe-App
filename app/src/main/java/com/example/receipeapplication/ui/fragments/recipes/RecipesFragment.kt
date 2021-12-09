package com.example.receipeapplication.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receipeapplication.viewmodels.MainViewModel
import com.example.receipeapplication.R
import com.example.receipeapplication.adapters.RecipesAdapter
import com.example.receipeapplication.databinding.FragmentRecipesBinding
import com.example.receipeapplication.util.Constants.Companion.API_KEY
import com.example.receipeapplication.util.NetworkListener
import com.example.receipeapplication.util.NetworkResult
import com.example.receipeapplication.util.observeOnce
import com.example.receipeapplication.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private val args by navArgs<RecipesFragmentArgs>()

    private lateinit var networkListener: NetworkListener


    private var _binding: FragmentRecipesBinding? = null
    private val binding get() =_binding!!

    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding =  FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()
        recipesViewModel.readBackOnline.observe(viewLifecycleOwner,{
            recipesViewModel.backOnline =  it
        })


       lifecycleScope.launch {
           networkListener = NetworkListener()
           networkListener.checkNetworkAvailability(requireContext())
               .collect { status->
                   Log.d("NetworkListener", status.toString())
                   recipesViewModel.networkStatus = status
                   recipesViewModel.showNetworkStatus()
                   readDatabase()
               }
       }


        binding.recipesFab.setOnClickListener{
            if(recipesViewModel.networkStatus){
                findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
            }else{
                recipesViewModel.showNetworkStatus()
            }

        }
        return binding.root
    }

    private fun setupRecyclerView(){
        binding.recyclerview.adapter =mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if(database.isNotEmpty() && !args.backFromBottomSheet){
                    Log.d("Recipes Fragment","Read database called")
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                }
                else{
                    requestApiData()
                }
            })
        }
    }

    private fun loadDataFromCache(){
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, {database->
                if(database.isNotEmpty()){
                    mAdapter.setData(database[0].foodRecipe)
                }

            })
        }
    }

    private fun requestApiData(){
        Log.d("Recipes Fragment","Request API data called")

        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner){response->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let{mAdapter.setData(it)}
                }
                is NetworkResult.Error ->{
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }

        }
    }


    private fun showShimmerEffect(){
        binding.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect(){
        binding.recyclerview.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}