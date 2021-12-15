package com.example.receipeapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.receipeapplication.adapters.PagerAdapter
import com.example.receipeapplication.R
import com.example.receipeapplication.data.database.entities.FavoritesEntity
import com.example.receipeapplication.ui.fragments.ingredients.IngredientsFragment
import com.example.receipeapplication.ui.fragments.instructions.InstructionsFragment
import com.example.receipeapplication.ui.fragments.overview.OverviewFragment
import com.example.receipeapplication.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")


        val resultBundle = Bundle()
        resultBundle.putParcelable("recipeBundle", args.result)

        val pagerAdapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )


        viewPager2.apply {
            adapter = pagerAdapter
        }
        TabLayoutMediator(tabLayout, viewPager2){
            tab, position->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }else if(item.itemId == R.id.save_to_favorites_menu){
            saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {

        val favoritesEntity =
            FavoritesEntity(
                0, args.result
            )
        mainViewModel.insertFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe saved.")
    }

    private fun showSnackBar(message: String) {

        Snackbar.make(detailsLayout, message, Snackbar.LENGTH_SHORT)
            .setAction("Okay"){}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {

        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}