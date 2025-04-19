package com.firefly.alphamusicsongbook.util

/**
 * Helper class to provide basic transliteration between English and Hindi
 * This is a simplified version - for production, you might use more complete libraries
 */
object TransliterationHelper {

    private val hindiToEnglishMap = mapOf(
        'अ' to "a", 'आ' to "aa", 'इ' to "i", 'ई' to "ee", 'उ' to "u", 'ऊ' to "oo",
        'ए' to "e", 'ऐ' to "ai", 'ओ' to "o", 'औ' to "au", 'क' to "k", 'ख' to "kh",
        'ग' to "g", 'घ' to "gh", 'ङ' to "ng", 'च' to "ch", 'छ' to "chh", 'ज' to "j",
        'झ' to "jh", 'ञ' to "ny", 'ट' to "t", 'ठ' to "th", 'ड' to "d", 'ढ' to "dh",
        'ण' to "n", 'त' to "t", 'थ' to "th", 'द' to "d", 'ध' to "dh", 'न' to "n",
        'प' to "p", 'फ' to "ph", 'ब' to "b", 'भ' to "bh", 'म' to "m", 'य' to "y",
        'र' to "r", 'ल' to "l", 'व' to "v", 'श' to "sh", 'ष' to "sh", 'स' to "s",
        'ह' to "h", "क्ष" to "ksh", "त्र" to "tr", "ज्ञ" to "gy", 'ा' to "a", 'ि' to "i",
        'ी' to "ee", 'ु' to "u", 'ू' to "oo", 'े' to "e", 'ै' to "ai", 'ो' to "o",
        'ौ' to "au", '्' to "", 'ं' to "n", 'ः' to "h", 'ँ' to "n"
    )

    private val englishToHindiMap = mutableMapOf<String, List<String>>().apply {
        // Reverse the hindiToEnglishMap to create English to Hindi mapping
        // But this is many-to-one, so we store a list of possible Hindi characters
        hindiToEnglishMap.forEach { (hindi, english) ->
            val hindiList = getOrDefault(english, emptyList())
            put(english, hindiList + hindi.toString())
        }

        // Add some common transliterations
        put("t", listOf("त", "ट"))
        put("d", listOf("द", "ड"))
        put("n", listOf("न", "ण", "ं"))
        put("s", listOf("स", "श"))
        put("a", listOf("अ", "आ", "ा"))
        put("i", listOf("इ", "ि"))
        put("u", listOf("उ", "ु"))
    }

    /**
     * Checks if a Hindi title matches an English search query
     */
    fun matchesTransliteration(hindiTitle: String, englishQuery: String): Boolean {
        // Convert Hindi title to potential English transliteration
        val englishVersion = hindiToEnglish(hindiTitle).lowercase()

        // Check if the English query is a substring of the transliterated title
        return englishVersion.contains(englishQuery.lowercase())
    }

    /**
     * Simple Hindi to English transliteration
     */
    private fun hindiToEnglish(hindi: String): String {
        val result = StringBuilder()
        var i = 0
        while (i < hindi.length) {
            val char = hindi[i]
            // Try to find a mapping for this Hindi character
            val english = hindiToEnglishMap[char] ?: char.toString()
            result.append(english)
            i++
        }
        return result.toString()
    }
}
