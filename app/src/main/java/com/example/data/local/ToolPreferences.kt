package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToolPreferences(context: Context) {

  private val prefs: SharedPreferences =
    context.getSharedPreferences("toolbox_pk_prefs", Context.MODE_PRIVATE)

  private val _favorites = MutableStateFlow<Set<Int>>(emptySet())
  val favorites: StateFlow<Set<Int>> = _favorites.asStateFlow()

  private val _recentlyUsed = MutableStateFlow<List<Int>>(emptyList())
  val recentlyUsed: StateFlow<List<Int>> = _recentlyUsed.asStateFlow()

  init {
    loadPreferences()
  }

  private fun loadPreferences() {
    val savedFavs = prefs.getStringSet(KEY_FAVORITES, emptySet()) ?: emptySet()
    _favorites.value = savedFavs.mapNotNull { it.toIntOrNull() }.toSet()

    val savedHistory = prefs.getString(KEY_HISTORY, "") ?: ""
    if (savedHistory.isNotBlank()) {
      _recentlyUsed.value = savedHistory.split(",").mapNotNull { it.trim().toIntOrNull() }
    } else {
      _recentlyUsed.value = emptyList()
    }
  }

  fun toggleFavorite(toolId: Int) {
    val current = _favorites.value.toMutableSet()
    if (current.contains(toolId)) {
      current.remove(toolId)
    } else {
      current.add(toolId)
    }
    _favorites.value = current
    prefs.edit().putStringSet(KEY_FAVORITES, current.map { it.toString() }.toSet()).apply()
  }

  fun isFavorite(toolId: Int): Boolean = _favorites.value.contains(toolId)

  fun recordToolUsed(toolId: Int) {
    val current = _recentlyUsed.value.toMutableList()
    current.remove(toolId)
    current.add(0, toolId)
    val trimmed = current.take(25)
    _recentlyUsed.value = trimmed
    prefs.edit().putString(KEY_HISTORY, trimmed.joinToString(",")).apply()
  }

  fun clearHistory() {
    _recentlyUsed.value = emptyList()
    prefs.edit().remove(KEY_HISTORY).apply()
  }

  companion object {
    private const val KEY_FAVORITES = "fav_tools_set"
    private const val KEY_HISTORY = "recent_tools_csv"

    @Volatile
    private var INSTANCE: ToolPreferences? = null

    fun getInstance(context: Context): ToolPreferences {
      return INSTANCE ?: synchronized(this) {
        INSTANCE ?: ToolPreferences(context.applicationContext).also { INSTANCE = it }
      }
    }
  }
}
