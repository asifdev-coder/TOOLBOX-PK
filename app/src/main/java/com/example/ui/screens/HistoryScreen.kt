package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.outlined.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.ToolRegistry
import com.example.data.local.ToolPreferences
import com.example.ui.theme.*

@Composable
fun HistoryScreen(
  toolPreferences: ToolPreferences,
  onNavigateToTool: (Int) -> Unit,
  onExploreTools: () -> Unit,
  modifier: Modifier = Modifier
) {
  val historyIds by toolPreferences.recentlyUsed.collectAsState()
  val favorites by toolPreferences.favorites.collectAsState()

  val recentTools = remember(historyIds) {
    historyIds.mapNotNull { ToolRegistry.getToolById(it) }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark)
      .padding(horizontal = 16.dp, vertical = 16.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column {
        Text(
          text = "Recently Used",
          style = MaterialTheme.typography.headlineLarge,
          color = TextPrimary
        )
        Text(
          text = "${recentTools.size} tools launched on this device",
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondary
        )
      }

      if (recentTools.isNotEmpty()) {
        IconButton(
          onClick = { toolPreferences.clearHistory() },
          modifier = Modifier.testTag("clear_history_button")
        ) {
          Icon(
            imageVector = Icons.Filled.DeleteSweep,
            contentDescription = "Clear history",
            tint = TextSecondary
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    if (recentTools.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Outlined.History,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(56.dp)
          )
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "No history recorded yet",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Tools you launch will automatically show up here for quick re-use.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(
            onClick = onExploreTools,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent, contentColor = BackgroundDark)
          ) {
            Text("Discover Tools", fontWeight = FontWeight.Bold)
          }
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(recentTools) { tool ->
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
}
