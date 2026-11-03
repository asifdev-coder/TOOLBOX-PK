package com.example

import com.example.data.ToolRegistry
import com.example.data.model.ToolCategory
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {

  @Test
  fun `tool registry contains exactly 155 tools`() {
    val total = ToolRegistry.allTools.size
    assertEquals(155, total)
  }

  @Test
  fun `tool registry contains every id from 1 to 155 without gaps or duplicates`() {
    val ids = ToolRegistry.allTools.map { it.id }.toSet()
    assertEquals(155, ids.size)
    for (i in 1..155) {
      assertTrue("Missing tool ID $i", ids.contains(i))
    }
  }

  @Test
  fun `tool categories distribution is correct`() {
    assertEquals(20, ToolRegistry.getToolsByCategory(ToolCategory.IMAGE).size)
    assertEquals(20, ToolRegistry.getToolsByCategory(ToolCategory.PDF).size)
    assertEquals(20, ToolRegistry.getToolsByCategory(ToolCategory.TEXT).size)
    assertEquals(20, ToolRegistry.getToolsByCategory(ToolCategory.DEVELOPER).size)
    assertEquals(20, ToolRegistry.getToolsByCategory(ToolCategory.CALCULATOR).size)
    assertEquals(15, ToolRegistry.getToolsByCategory(ToolCategory.STUDENT).size)
    assertEquals(15, ToolRegistry.getToolsByCategory(ToolCategory.SEO).size)
    assertEquals(15, ToolRegistry.getToolsByCategory(ToolCategory.QR_DESIGN).size)
    assertEquals(10, ToolRegistry.getToolsByCategory(ToolCategory.FILE).size)
  }

  @Test
  fun `search tools by keyword works`() {
    val results = ToolRegistry.searchTools("qr")
    assertTrue(results.isNotEmpty())
    assertTrue(results.any { it.title.contains("QR", ignoreCase = true) })
  }
}
