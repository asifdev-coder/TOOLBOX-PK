package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ToolRegistry
import com.example.data.local.ToolPreferences
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context matches ToolBox PK`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ToolBox PK", appName)
  }

  @Test
  fun `tool preferences toggle favorite works`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val prefs = ToolPreferences(context)
    val testId = 41 // Word Counter

    val initial = prefs.isFavorite(testId)
    prefs.toggleFavorite(testId)
    assertEquals(!initial, prefs.isFavorite(testId))

    // Toggle back
    prefs.toggleFavorite(testId)
    assertEquals(initial, prefs.isFavorite(testId))
  }
}
