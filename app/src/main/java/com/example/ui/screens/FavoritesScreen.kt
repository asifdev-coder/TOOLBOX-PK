package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.ToolRegistry
import com.example.data.local.ToolPreferences
import com.example.ui.theme.*

@Composable
fun FavoritesScreen(
  toolPreferences: ToolPreferences,
  onNavigateToTool: (Int) -> Unit,
  onExploreTools: () -> Unit,
  modifier: Modifier = Modifier
) {
  val favorites by toolPreferences.favorites.collectAsState()

  val favoriteTools = remember(favorites) {
    favorites.mapNotNull { ToolRegistry.getToolById(it) }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark)
      .padding(horizontal = 16.dp, vertical = 16.dp)
  ) {
    Text(
      text = "Your Favorites",
      style = MaterialTheme.typography.headlineLarge,
      color = TextPrimary
    )
    Text(
      text = "${favoriteTools.size} bookmarked tools for instant access",
      style = MaterialTheme.typography.bodySmall,
      color = TextSecondary
    )

    Spacer(modifier = Modifier.height(16.dp))

    if (favoriteTools.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f),
        contentAlignment = Alignment.Center
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
            imageVector = Icons.Outlined.BookmarkBorder,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(56.dp)
          )
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "No favorite tools yet",
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Tap the heart icon on any tool card to bookmark it here.",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(
            onClick = onExploreTools,
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent, contentColor = BackgroundDark)
          ) {
            Text("Browse All Tools", fontWeight = FontWeight.Bold)
          }
        }
      }
    } else {
      LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(favoriteTools) { tool ->
          ToolItemCard(
            tool = tool,
            isFavorite = true,
            onToggleFavorite = { toolPreferences.toggleFavorite(tool.id) },
            onClick = { onNavigateToTool(tool.id) }
          )
        }
      }
    }
  }
}
