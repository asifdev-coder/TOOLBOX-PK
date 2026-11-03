package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.ToolRegistry
import com.example.data.local.ToolPreferences
import com.example.data.model.Tool
import com.example.data.model.ToolCategory
import com.example.ui.components.ToolBoxHeader
import com.example.ui.theme.*
import com.example.ui.tools.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolDetailScreen(
  toolId: Int,
  toolPreferences: ToolPreferences,
  onBack: () -> Unit,
  onNavigateToTool: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }

  val tool = remember(toolId) { ToolRegistry.getToolById(toolId) }
  val favorites by toolPreferences.favorites.collectAsState()
  val isFav = favorites.contains(toolId)

  LaunchedEffect(toolId) {
    toolPreferences.recordToolUsed(toolId)
  }

  if (tool == null) {
    Box(
      modifier = modifier.fillMaxSize().background(BackgroundDark),
      contentAlignment = Alignment.Center
    ) {
      Text("Tool #$toolId not found.", color = TextSecondary)
    }
    return
  }

  val relatedTools = remember(tool) {
    ToolRegistry.getToolsByCategory(tool.category).filter { it.id != tool.id }.take(3)
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = tool.title,
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
        },
        navigationIcon = {
          IconButton(
            onClick = onBack,
            modifier = Modifier.testTag("back_button")
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = TextPrimary
            )
          }
        },
        actions = {
          IconButton(
            onClick = { toolPreferences.toggleFavorite(tool.id) },
            modifier = Modifier.testTag("detail_fav_button")
          ) {
            Icon(
              imageVector = if (isFav) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
              contentDescription = if (isFav) "Remove favorite" else "Add favorite",
              tint = if (isFav) MaterialTheme.colorScheme.error else TextSecondary
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = CardBackground,
          titleContentColor = TextPrimary
        )
      )
    },
    containerColor = BackgroundDark,
    modifier = modifier
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Header Info
      item {
        ToolBoxHeader(
          title = tool.title,
          category = tool.category,
          description = tool.description,
          toolId = tool.id,
          isFavorite = isFav,
          onToggleFavorite = { toolPreferences.toggleFavorite(tool.id) },
          modifier = Modifier.padding(horizontal = 16.dp)
        )
      }

      // Main Interactive Tool View
      item {
        when {
          tool.id in 1..40 -> ImagePdfToolScreen(tool = tool)
          tool.id in 41..60 -> TextToolScreen(tool = tool)
          tool.id in 61..80 -> DeveloperToolScreen(tool = tool)
          tool.id in 81..100 -> CalculatorToolScreen(tool = tool)
          tool.id in 101..115 -> StudentToolScreen(tool = tool)
          tool.id in 116..130 -> SeoMarketingToolScreen(tool = tool)
          tool.id in 131..145 -> QrDesignToolScreen(tool = tool)
          tool.id in 146..155 -> FileToolScreen(tool = tool)
          else -> TextToolScreen(tool = tool)
        }
      }

      // Related Tools Section
      if (relatedTools.isNotEmpty()) {
        item {
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "Related in ${tool.category.displayName}",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 16.dp)
          )
        }

        items(relatedTools) { relTool ->
          Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            ToolItemCard(
              tool = relTool,
              isFavorite = favorites.contains(relTool.id),
              onToggleFavorite = { toolPreferences.toggleFavorite(relTool.id) },
              onClick = { onNavigateToTool(relTool.id) }
            )
          }
        }
      }
    }
  }
}
