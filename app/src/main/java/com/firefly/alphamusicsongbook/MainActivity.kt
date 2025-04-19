package com.firefly.alphamusicsongbook

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.cardview.widget.CardView
import com.firefly.alphamusicsongbook.util.LocaleHelper

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<CardView>(R.id.cardHindi).setOnClickListener {
            navigateToSongsList("hindi")
        }

        findViewById<CardView>(R.id.cardEnglish).setOnClickListener {
            navigateToSongsList("english")
        }
    }

    private fun navigateToSongsList(language: String) {
        val intent = Intent(this, SongsListActivity::class.java)
        intent.putExtra("language", language)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_language -> {
                showLanguagePopup()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLanguagePopup() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        val popupMenu = PopupMenu(this, toolbar)
        popupMenu.menuInflater.inflate(R.menu.language_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_english -> {
                    setLanguage("en")
                    true
                }
                R.id.action_hindi -> {
                    setLanguage("hi")
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}
