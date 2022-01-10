package com.example.receipeapplication.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.receipeapplication.R
import com.example.receipeapplication.adapters.IngredientsAdapter
import com.example.receipeapplication.databinding.FragmentIngredientsBinding
import com.example.receipeapplication.models.Result
import com.example.receipeapplication.util.Constants.Companion.RECIPE_RESULT_KEY

// TODO: Rename parameter arguments, choose names that match

class IngredientsFragment : Fragment() {
    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecyclerView()
        myBundle?.extendedIngredients?.let{mAdapter.setData(it)}
        return binding.root
    }

    private fun setupRecyclerView(){

        binding.ingredientsRecyclerview.adapter = mAdapter
        binding.ingredientsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}