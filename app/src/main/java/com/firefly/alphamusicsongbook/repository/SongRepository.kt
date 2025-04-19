package com.firefly.alphamusicsongbook.repository

import android.content.Context
import com.firefly.alphamusicsongbook.model.Song
import com.firefly.alphamusicsongbook.util.TransliterationHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class SongRepository(private val context: Context) {

    private var hindiSongs: List<Song> = emptyList()
    private var englishSongs: List<Song> = emptyList()

    init {
        loadSongs()
    }

    private fun loadSongs() {
        try {
            // Load Hindi songs
            context.assets.open("hindi_songs.json").use { inputStream ->
                val json = inputStream.bufferedReader().use { it.readText() }
                val typeToken = object : TypeToken<List<Song>>() {}.type
                hindiSongs = Gson().fromJson(json, typeToken)
            }

            // Load English songs
            context.assets.open("english_songs.json").use { inputStream ->
                val json = inputStream.bufferedReader().use { it.readText() }
                val typeToken = object : TypeToken<List<Song>>() {}.type
                englishSongs = Gson().fromJson(json, typeToken)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun getHindiSongs(): List<Song> {
        return hindiSongs.sortedBy { it.title }
    }

    fun getEnglishSongs(): List<Song> {
        return englishSongs.sortedBy { it.title }
    }

    fun getSongById(id: Int, language: String): Song? {
        return when (language.lowercase()) {
            "hindi" -> hindiSongs.find { it.id == id }
            "english" -> englishSongs.find { it.id == id }
            else -> null
        }
    }

    fun searchSongs(query: String, language: String): List<Song> {
        if (query.isBlank()) {
            return when (language.lowercase()) {
                "hindi" -> getHindiSongs()
                "english" -> getEnglishSongs()
                else -> emptyList()
            }
        }

        return when (language.lowercase()) {
            "hindi" -> {
                hindiSongs.filter { song ->
                    // Direct match by title
                    val directMatch = song.title.contains(query, ignoreCase = true)

                    // If no direct match and query is in Latin script (English), try transliteration matching
                    val transliterationMatch = if (!directMatch && isLatinScript(query)) {
                        TransliterationHelper.matchesTransliteration(song.title, query)
                    } else false

                    directMatch || transliterationMatch
                }.sortedBy { it.title }
            }
            "english" -> {
                englishSongs.filter {
                    it.title.contains(query, ignoreCase = true)
                }.sortedBy { it.title }
            }
            else -> emptyList()
        }
    }

    private fun isLatinScript(text: String): Boolean {
        // Simple check to see if text is likely in Latin script (English)
        val latinCharRange = 'A'..'z'
        return text.all { it in latinCharRange || it.isWhitespace() || it.isDigit() }
    }
}
