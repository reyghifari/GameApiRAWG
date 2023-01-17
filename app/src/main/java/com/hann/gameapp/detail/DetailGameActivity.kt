package com.hann.gameapp.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.hann.core.domain.model.Game
import com.hann.gameapp.R
import com.hann.gameapp.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var statusFavorite: Boolean = false
    private lateinit var detailGame: Game
    private lateinit var actionTitle: MenuItem
    private lateinit var actionFavorite: MenuItem
    private val detailGameViewModel: DetailGameViewModel by viewModel()

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailGame = intent.getParcelableExtra<Game>(EXTRA_DATA)!!
        showDetailGame(detailGame)
    }

    private fun showDetailGame(detailGame: Game?) {
        detailGame?.let {
            supportActionBar?.title = null
            binding.content.tvTitleGame.text = detailGame.name
            binding.content.tvRealesedDate.text = "Realease date ${detailGame.released}"
            binding.content.tvPlaytime.text = detailGame.playtime
            binding.content.tvRating.text = detailGame.rating.toString()
            Glide.with(this)
                .load(detailGame.background_image)
                .into(binding.ivDetailImage)

            statusFavorite = detailGame.isFavorite
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            actionFavorite.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
        } else {
            actionFavorite.icon = ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        actionTitle = menu.findItem(R.id.action_title)
        actionFavorite = menu.findItem(R.id.action_favorite)
        actionTitle.title = detailGame.name
        setStatusFavorite(statusFavorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home ->{
                onBackPressed()
                true
            }
            R.id.action_title -> {
                true
            }
            R.id.action_favorite -> {
                doFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun doFavorite() {
        statusFavorite = !statusFavorite
        detailGameViewModel.setGameFavorite(detailGame, statusFavorite)
        setStatusFavorite(statusFavorite)
    }
}