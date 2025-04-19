package com.firefly.alphamusicsongbook

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firefly.alphamusicsongbook.util.LocaleHelper

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val language = LocaleHelper.getLanguage(newBase)
        super.attachBaseContext(LocaleHelper.setLocale(newBase, language))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val language = LocaleHelper.getLanguage(this)
        LocaleHelper.setLocale(this, language)
    }

    fun setLanguage(language: String) {
        LocaleHelper.setLocale(this, language)
        recreate()
    }
}
