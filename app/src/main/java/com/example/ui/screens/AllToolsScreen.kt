package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ToolRegistry
import com.example.data.local.ToolPreferences
import com.example.data.model.ToolCategory
import com.example.ui.theme.*

@Composable
fun AllToolsScreen(
  toolPreferences: ToolPreferences,
  initialCategory: ToolCategory? = null,
  onNavigateToTool: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedCategory by remember { mutableStateOf<ToolCategory?>(initialCategory) }
  var searchQuery by remember { mutableStateOf("") }
  val favorites by toolPreferences.favorites.collectAsState()

  val filteredTools = remember(selectedCategory, searchQuery) {
    ToolRegistry.searchTools(searchQuery, selectedCategory)
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark)
  ) {
    // Top Bar / Title
    Column(modifier = Modifier.padding(16.dp)) {
      Text(
        text = "All Tools Catalog",
        style = MaterialTheme.typography.headlineLarge,
        color = TextPrimary
      )
      Text(
        text = "Browse ${ToolRegistry.allTools.size} offline tools across 9 categories",
        style = MaterialTheme.typography.bodySmall,
        color = TextSecondary
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Search Field
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Filter tools by name, keyword or #ID...", color = TextMuted, fontSize = 13.sp) },
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null, tint = PrimaryAccent) },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(Icons.Filled.Clear, contentDescription = "Clear", tint = TextSecondary)
            }
          }
        },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("all_tools_search_field"),
        colors = OutlinedTextFieldDefaults.colors(
          focusedBorderColor = PrimaryAccent,
          unfocusedBorderColor = BorderGreen,
          focusedContainerColor = CardBackground,
          unfocusedContainerColor = CardBackground,
          cursorColor = PrimaryAccent
        ),
        shape = RoundedCornerShape(12.dp)
      )
    }

    // Category Tabs / Chips
    LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      item {
        FilterChip(
          selected = selectedCategory == null,
          onClick = { selectedCategory = null },
          label = { Text("All (155)") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = PrimaryAccent,
            selectedLabelColor = BackgroundDark,
            containerColor = CardBackground,
            labelColor = TextPrimary
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedCategory == null,
            borderColor = BorderGreen,
            selectedBorderColor = PrimaryAccent
          ),
          modifier = Modifier.testTag("filter_all_chip")
        )
      }

      items(ToolCategory.values()) { category ->
        val count = ToolRegistry.getToolsByCategory(category).size
        FilterChip(
          selected = selectedCategory == category,
          onClick = { selectedCategory = if (selectedCategory == category) null else category },
          label = { Text("${category.displayName} ($count)") },
          colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = PrimaryAccent,
            selectedLabelColor = BackgroundDark,
            containerColor = CardBackground,
            labelColor = TextPrimary
          ),
          border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selectedCategory == category,
            borderColor = BorderGreen,
            selectedBorderColor = PrimaryAccent
          ),
          modifier = Modifier.testTag("filter_chip_${category.id}")
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Count indicator
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = "Showing ${filteredTools.size} of 155 tools",
        style = MaterialTheme.typography.labelSmall,
        color = TextMuted
      )
    }

    // List of Tools
    LazyColumn(
      modifier = Modifier.fillMaxSize(),
      contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      items(filteredTools) { tool ->
        ToolItemCard(
          tool = tool,
          isFavorite = favorites.contains(tool.id),
          onToggleFavorite = { toolPreferences.toggleFavorite(tool.id) },
          onClick = { onNavigateToTool(tool.id) }
        )
      }
    }
  }
}
