package com.firefly.alphamusicsongbook.util

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*

object LocaleHelper {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun setLocale(context: Context, language: String): Context {
        saveLanguagePref(context, language)

        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    fun getLanguage(context: Context): String {
        return getLanguagePref(context) ?: "en"
    }

    private fun getLanguagePref(context: Context): String? {
        val preferences = getPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, "en")
    }

    private fun saveLanguagePref(context: Context, language: String) {
        val preferences = getPreferences(context)
        preferences.edit().putString(SELECTED_LANGUAGE, language).apply()
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    }
}
