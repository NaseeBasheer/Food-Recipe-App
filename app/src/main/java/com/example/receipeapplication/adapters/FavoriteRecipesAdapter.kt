package com.example.receipeapplication.adapters

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.receipeapplication.R
import com.example.receipeapplication.data.database.entities.FavoritesEntity
import com.example.receipeapplication.databinding.FavoriteRecipesRowLayoutBinding
import com.example.receipeapplication.ui.fragments.favorites.FavoriteRecipesFragmentDirections
import com.example.receipeapplication.util.RecipesDiffUtil
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.favorite_recipes_row_layout.view.*
import kotlinx.android.synthetic.main.fragment_favorite_recipes.view.*

class FavoriteRecipesAdapter(private val requireActivity: FragmentActivity): RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(), ActionMode.Callback{
    private var favoriteRecipes = emptyList<FavoritesEntity>()
    private var multiSelection = false
    private lateinit var mActionMode: ActionMode
    private var myViewHolders = arrayListOf<MyViewHolder>()
    private var selectedRecipes = arrayListOf<FavoritesEntity>()

    class MyViewHolder(private val binding: FavoriteRecipesRowLayoutBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoritesEntity: FavoritesEntity){
            binding.favoritesEntity = favoritesEntity
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteRecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        val currentRecipe = favoriteRecipes[position]
        holder.bind(currentRecipe)

        //single click listener
        holder.itemView.favoriteRecipesRowLayout.setOnClickListener {
            if(multiSelection){
                applySelection(holder, currentRecipe)
            }
            else{
                val action = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToDetailsActivity(
                    currentRecipe.result)
                holder.itemView.findNavController().navigate(action)
            }



        }

        //long click listener
        holder.itemView.favoriteRecipesRowLayout.setOnLongClickListener {
            if(!multiSelection){
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, currentRecipe)
                true
            }else{
                multiSelection = false
                false
            }

        }
    }

    override fun getItemCount(): Int {
        return favoriteRecipes.size
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoritesEntity){
        if(selectedRecipes.contains(currentRecipe)){
            selectedRecipes.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)
            applyActionModeTitle()
        }else{
            selectedRecipes.add(currentRecipe)
            changeRecipeStyle(holder, R.color.cardBackgroundLightColor, R.color.colorPrimary)
            applyActionModeTitle()
        }
    }
    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int){
        holder.itemView.favoriteRecipesRowLayout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )
        holder.itemView.favorite_row_cardView.setStrokeColor(
            ContextCompat.getColor(requireActivity, strokeColor)
        )
    }
    private fun applyActionModeTitle(){
        when(selectedRecipes.size){
            0->{
                mActionMode.finish()
            }
            1->{
                mActionMode.title = "${selectedRecipes.size} item selected"
            }
            else->{
                mActionMode.title = "${selectedRecipes.size} items selected"
            }
        }
    }



    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.favorites_contextual_menu, menu)
        mActionMode = actionMode!!
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        myViewHolders.forEach{holder->
            changeRecipeStyle(holder, R.color.cardBackgroundColor, R.color.strokeColor)

        }
        multiSelection = false
        selectedRecipes.clear()
        applyStatusBarColor(R.color.statusBarColor)

    }
    private fun applyStatusBarColor(color: Int){
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }
    fun setData(newFavoriteRecipes: List<FavoritesEntity>){
        val favoriteRecipesDiffUtil = RecipesDiffUtil(favoriteRecipes,  newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }
}