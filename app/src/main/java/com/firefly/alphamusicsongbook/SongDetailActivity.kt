package com.firefly.alphamusicsongbook

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.firefly.alphamusicsongbook.repository.SongRepository

class SongDetailActivity : BaseActivity() {

    private lateinit var repository: SongRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)

        val songId = intent.getIntExtra("songId", -1)
        val language = intent.getStringExtra("language") ?: "english"

        repository = SongRepository(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.back)
            setDisplayHomeAsUpEnabled(true)
        }

        val tvSongTitle = findViewById<TextView>(R.id.tvSongTitle)
        val tvLyrics = findViewById<TextView>(R.id.tvLyrics)

        val song = repository.getSongById(songId, language)
        if (song != null) {
            tvSongTitle.text = song.title
            tvLyrics.text = song.lyrics
        } else {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
