package com.firefly.alphamusicsongbook

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firefly.alphamusicsongbook.adapter.SongAdapter
import com.firefly.alphamusicsongbook.model.Song
import com.firefly.alphamusicsongbook.repository.SongRepository

class SongsListActivity : BaseActivity() {

    private lateinit var repository: SongRepository
    private lateinit var adapter: SongAdapter
    private lateinit var language: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)

        language = intent.getStringExtra("language") ?: "english"
        repository = SongRepository(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = if (language == "hindi") getString(R.string.hindi_songs) else getString(R.string.english_songs)
            setDisplayHomeAsUpEnabled(true)
        }

        recyclerView = findViewById(R.id.recyclerView)
        emptyView = findViewById(R.id.tvEmpty)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val songs = if (language == "hindi") repository.getHindiSongs() else repository.getEnglishSongs()
        adapter = SongAdapter(songs) { song -> navigateToSongDetail(song) }
        recyclerView.adapter = adapter

        updateEmptyViewVisibility(songs)

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                performSearch(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                performSearch(newText ?: "")
                return true
            }
        })
    }

    private fun performSearch(query: String) {
        val filteredSongs = if (query.isBlank()) {
            if (language == "hindi") repository.getHindiSongs() else repository.getEnglishSongs()
        } else {
            repository.searchSongs(query, language)
        }

        adapter.updateSongs(filteredSongs)
        updateEmptyViewVisibility(filteredSongs)
    }

    private fun updateEmptyViewVisibility(songs: List<Song>) {
        if (songs.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }

    private fun navigateToSongDetail(song: Song) {
        val intent = Intent(this, SongDetailActivity::class.java)
        intent.putExtra("songId", song.id)
        intent.putExtra("language", language)
        startActivity(intent)
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
