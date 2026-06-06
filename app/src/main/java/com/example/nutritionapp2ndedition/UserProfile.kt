package com.example.nutritionapp2ndedition

import okhttp3.Response

data class UserProfile(
    val name: String = "",
    val age: String = "",
    val weight: String = "",
    val height: String = "",
    val goals: String = "",
    val context: String = ""
)

fun parseUserProfile(response: String): UserProfile {
    fun extract(key: String): String {
        val lines = response.lines()
        val startIndex = lines.indexOfFirst { it.trim().startsWith(key) }
        if (startIndex == -1) return ""

        val firstLine = lines[startIndex].removePrefix(key).trim()
        val subsequent = lines.drop(startIndex + 1)
            .takeWhile { it.isNotBlank() && !it.contains(":") }
            .joinToString(" ")

        return if (subsequent.isBlank()) firstLine
        else "$firstLine $subsequent".trim()
    }

    return UserProfile(
        name = extract("NAME:"),
        age = extract("AGE:"),
        weight = extract("WEIGHT:"),
        height = extract("HEIGHT:"),
        goals = extract("GOALS:"),
        context = extract("CONTEXT:")
    )
}