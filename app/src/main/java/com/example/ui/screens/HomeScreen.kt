package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ToolRegistry
import com.example.data.local.ToolPreferences
import com.example.data.model.Tool
import com.example.data.model.ToolCategory
import com.example.ui.components.getCategoryIcon
import com.example.ui.theme.*

@Composable
fun HomeScreen(
  toolPreferences: ToolPreferences,
  onNavigateToTool: (Int) -> Unit,
  onNavigateToCategory: (ToolCategory) -> Unit,
  onNavigateToAllTools: () -> Unit,
  onNavigateToInfo: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  var searchQuery by remember { mutableStateOf("") }
  val favorites by toolPreferences.favorites.collectAsState()
  val recentlyUsedIds by toolPreferences.recentlyUsed.collectAsState()

  val searchResults = remember(searchQuery) {
    if (searchQuery.isBlank()) emptyList() else ToolRegistry.searchTools(searchQuery)
  }

  val recentTools = remember(recentlyUsedIds) {
    recentlyUsedIds.mapNotNull { ToolRegistry.getToolById(it) }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(BackgroundDark),
    contentPadding = PaddingValues(bottom = 32.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    // 1. Hero Section
    item {
      HeroHeaderSection(
        searchQuery = searchQuery,
        onSearchQueryChange = { searchQuery = it }
      )
    }

    // If searching, show search results directly
    if (searchQuery.isNotBlank()) {
      item {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
          Text(
            text = "Search Results (${searchResults.size} found)",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary
          )
        }
      }

      if (searchResults.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Icon(Icons.Filled.SearchOff, contentDescription = null, tint = TextMuted, modifier = Modifier.size(48.dp))
              Spacer(modifier = Modifier.height(8.dp))
              Text("No tools matching \"$searchQuery\"", color = TextSecondary)
            }
          }
        }
      } else {
        items(searchResults) { tool ->
          Box(modifier = Modifier.padding(horizontal = 16.dp)) {
            ToolItemCard(
              tool = tool,
              isFavorite = favorites.contains(tool.id),
              onToggleFavorite = { toolPreferences.toggleFavorite(tool.id) },
              onClick = { onNavigateToTool(tool.id) }
            )
          }
        }
      }
    } else {
      // 2. Quick Stats Banner
      item {
        StatsBanner(onExploreClick = onNavigateToAllTools)
      }

      // 3. Recently Used (if any)
      if (recentTools.isNotEmpty()) {
        item {
          SectionTitle(title = "Recently Used", subtitle = "Jump back into your recent tools")
          Spacer(modifier = Modifier.height(10.dp))
          LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            items(recentTools) { tool ->
              RecentToolChip(
                tool = tool,
                onClick = { onNavigateToTool(tool.id) }
              )
            }
          }
        }
      }

      // 4. Categories Grid
      item {
        SectionTitle(
          title = "Tool Categories",
          subtitle = "Browse all 155 tools organized into 9 categories",
          actionText = "View All (155)",
          onActionClick = onNavigateToAllTools
        )
        Spacer(modifier = Modifier.height(12.dp))
        Column(
          modifier = Modifier.padding(horizontal = 16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          ToolCategory.values().toList().chunked(3).forEach { rowCategories ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              rowCategories.forEach { cat ->
                CategoryGridItem(
                  category = cat,
                  toolCount = ToolRegistry.getToolsByCategory(cat).size,
                  onClick = { onNavigateToCategory(cat) },
                  modifier = Modifier.weight(1f)
                )
              }
              // Fill remaining space if odd count
              if (rowCategories.size < 3) {
                repeat(3 - rowCategories.size) {
                  Spacer(modifier = Modifier.weight(1f))
                }
              }
            }
          }
        }
      }

      // 5. Popular Tools
      item {
        SectionTitle(title = "Popular & Featured Tools", subtitle = "Most frequently used utilities")
        Spacer(modifier = Modifier.height(12.dp))
        Column(
          modifier = Modifier.padding(horizontal = 16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          ToolRegistry.popularTools.take(8).forEach { tool ->
            ToolItemCard(
              tool = tool,
              isFavorite = favorites.contains(tool.id),
              onToggleFavorite = { toolPreferences.toggleFavorite(tool.id) },
              onClick = { onNavigateToTool(tool.id) }
            )
          }
        }
      }

      // 6. Why ToolBox PK
      item {
        WhyToolBoxPkSection()
      }

      // 7. Footer & Information Links
      item {
        FooterSection(onNavigateToInfo = onNavigateToInfo)
      }
    }
  }
}

@Composable
private fun HeroHeaderSection(
  searchQuery: String,
  onSearchQueryChange: (String) -> Unit
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(
        Brush.verticalGradient(
          listOf(
            SecondaryBackground,
            BackgroundDark
          )
        )
      )
      .padding(horizontal = 16.dp, vertical = 24.dp)
  ) {
    Column {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Image(
            painter = painterResource(id = R.drawable.toolbox_logo),
            contentDescription = "ToolBox PK Logo",
            modifier = Modifier
              .size(46.dp)
              .clip(RoundedCornerShape(10.dp))
              .border(1.dp, PrimaryAccent, RoundedCornerShape(10.dp))
          )
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "ToolBox PK",
              style = MaterialTheme.typography.headlineLarge,
              color = TextPrimary,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = "155+ Free Tools All in One Place",
              style = MaterialTheme.typography.bodySmall,
              color = PrimaryAccent
            )
          }
        }

        Surface(
          color = Color(0x2600C878),
          shape = RoundedCornerShape(20.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryAccent)
        ) {
          Text(
            text = "v1.0 Ready",
            style = MaterialTheme.typography.labelSmall,
            color = PrimaryAccent,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      Text(
        text = "Every utility you need.\nZero cloud tracking.",
        style = MaterialTheme.typography.displayMedium.copy(fontSize = 24.sp, lineHeight = 30.sp),
        color = TextPrimary,
        fontWeight = FontWeight.Bold
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Search input
      OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        placeholder = { Text("Search 155+ tools (e.g. compress, json, emi, qr)...", color = TextMuted, fontSize = 13.sp) },
        leadingIcon = {
          Icon(Icons.Filled.Search, contentDescription = null, tint = PrimaryAccent)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { onSearchQueryChange("") }) {
              Icon(Icons.Filled.Clear, contentDescription = "Clear search", tint = TextSecondary)
            }
          }
        },
        singleLine = true,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("home_search_bar"),
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
  }
}

@Composable
private fun StatsBanner(onExploreClick: () -> Unit) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .clickable { onExploreClick() },
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      StatItem(value = "155+", label = "Total Tools")
      Box(modifier = Modifier.width(1.dp).height(32.dp).background(BorderGreen))
      StatItem(value = "9", label = "Categories")
      Box(modifier = Modifier.width(1.dp).height(32.dp).background(BorderGreen))
      StatItem(value = "100%", label = "On-Device")
      Box(modifier = Modifier.width(1.dp).height(32.dp).background(BorderGreen))
      StatItem(value = "0", label = "Ads / Logins")
    }
  }
}

@Composable
private fun StatItem(value: String, label: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = value,
      style = MaterialTheme.typography.titleLarge.copy(
        fontFamily = JetBrainsMonoFamily,
        fontWeight = FontWeight.Bold
      ),
      color = PrimaryAccent
    )
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = TextSecondary
    )
  }
}

@Composable
private fun SectionTitle(
  title: String,
  subtitle: String? = null,
  actionText: String? = null,
  onActionClick: (() -> Unit)? = null
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        style = MaterialTheme.typography.headlineMedium,
        color = TextPrimary
      )
      if (subtitle != null) {
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondary
        )
      }
    }

    if (actionText != null && onActionClick != null) {
      TextButton(
        onClick = onActionClick,
        colors = ButtonDefaults.textButtonColors(contentColor = PrimaryAccent)
      ) {
        Text(actionText, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelLarge)
      }
    }
  }
}

@Composable
private fun CategoryGridItem(
  category: ToolCategory,
  toolCount: Int,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .height(98.dp)
      .clickable { onClick() }
      .testTag("category_card_${category.id}"),
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .size(32.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SecondaryBackground),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = getCategoryIcon(category),
            contentDescription = null,
            tint = PrimaryAccent,
            modifier = Modifier.size(18.dp)
          )
        }
        Text(
          text = "$toolCount",
          style = MaterialTheme.typography.labelSmall.copy(fontFamily = JetBrainsMonoFamily),
          color = TextSecondary
        )
      }

      Text(
        text = category.displayName,
        style = MaterialTheme.typography.labelLarge.copy(fontSize = 11.5.sp),
        color = TextPrimary,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

@Composable
fun ToolItemCard(
  tool: Tool,
  isFavorite: Boolean,
  onToggleFavorite: () -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .testTag("tool_card_${tool.id}"),
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(SecondaryBackground)
          .border(1.dp, BorderGreen, RoundedCornerShape(10.dp)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = getCategoryIcon(tool.category),
          contentDescription = null,
          tint = PrimaryAccent,
          modifier = Modifier.size(22.dp)
        )
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = tool.title,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
          )
          if (tool.badge != null) {
            Spacer(modifier = Modifier.width(6.dp))
            Surface(
              color = Color(0x2600C878),
              shape = RoundedCornerShape(4.dp)
            ) {
              Text(
                text = tool.badge,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp),
                color = PrimaryAccent,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp),
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = tool.description,
          style = MaterialTheme.typography.bodySmall,
          color = TextSecondary,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )
      }

      IconButton(
        onClick = onToggleFavorite,
        modifier = Modifier.size(36.dp).testTag("fav_btn_${tool.id}")
      ) {
        Icon(
          imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
          contentDescription = "Toggle favorite",
          tint = if (isFavorite) Color(0xFFFF4D4D) else TextMuted,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

@Composable
private fun RecentToolChip(
  tool: Tool,
  onClick: () -> Unit
) {
  Surface(
    color = CardBackground,
    shape = RoundedCornerShape(10.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    modifier = Modifier.clickable { onClick() }
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = getCategoryIcon(tool.category),
        contentDescription = null,
        tint = PrimaryAccent,
        modifier = Modifier.size(16.dp)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(
        text = tool.title,
        style = MaterialTheme.typography.labelLarge,
        color = TextPrimary
      )
    }
  }
}

@Composable
private fun WhyToolBoxPkSection() {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    shape = RoundedCornerShape(14.dp)
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Text("Why ToolBox PK?", style = MaterialTheme.typography.headlineMedium, color = TextPrimary)
      Spacer(modifier = Modifier.height(4.dp))
      Text("Built with security, speed and privacy at the foundation", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
      Spacer(modifier = Modifier.height(14.dp))

      WhyPoint(icon = Icons.Filled.Lock, title = "100% Private On-Device Processing", desc = "All text transformations, images, and calculations are computed locally in your device memory.")
      Spacer(modifier = Modifier.height(10.dp))
      WhyPoint(icon = Icons.Filled.FlashOn, title = "Zero Lag, Zero Queue", desc = "No external API bottlenecks, rate limits or server queues.")
      Spacer(modifier = Modifier.height(10.dp))
      WhyPoint(icon = Icons.Filled.NoAccounts, title = "No Registration Required", desc = "Open the app and start using any of the 155 tools instantly. No email or password needed.")
      Spacer(modifier = Modifier.height(10.dp))
      WhyPoint(icon = Icons.Filled.MoneyOff, title = "Completely Free", desc = "No paywalls or hidden subscriptions for core features.")
    }
  }
}

@Composable
private fun WhyPoint(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, desc: String) {
  Row(verticalAlignment = Alignment.Top) {
    Box(
      modifier = Modifier
        .size(32.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(CardBackground)
        .border(1.dp, BorderGreen, RoundedCornerShape(8.dp)),
      contentAlignment = Alignment.Center
    ) {
      Icon(icon, contentDescription = null, tint = PrimaryAccent, modifier = Modifier.size(16.dp))
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column {
      Text(title, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
      Text(desc, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
    }
  }
}

@Composable
private fun FooterSection(onNavigateToInfo: (String) -> Unit) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    HorizontalDivider(color = BorderGreen)
    Spacer(modifier = Modifier.height(16.dp))

    Text("ToolBox PK", style = MaterialTheme.typography.titleLarge, color = TextPrimary, fontWeight = FontWeight.Bold)
    Text("155+ Free Online & Offline Tools Suite", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
    Spacer(modifier = Modifier.height(12.dp))

    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.padding(vertical = 4.dp)
    ) {
      Text("About Us", color = PrimaryAccent, style = MaterialTheme.typography.labelLarge, modifier = Modifier.clickable { onNavigateToInfo("about") })
      Text("Privacy Policy", color = PrimaryAccent, style = MaterialTheme.typography.labelLarge, modifier = Modifier.clickable { onNavigateToInfo("privacy") })
      Text("Terms", color = PrimaryAccent, style = MaterialTheme.typography.labelLarge, modifier = Modifier.clickable { onNavigateToInfo("terms") })
    }

    Row(
      horizontalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.padding(vertical = 4.dp)
    ) {
      Text("Disclaimer", color = PrimaryAccent, style = MaterialTheme.typography.labelLarge, modifier = Modifier.clickable { onNavigateToInfo("disclaimer") })
      Text("FAQ", color = PrimaryAccent, style = MaterialTheme.typography.labelLarge, modifier = Modifier.clickable { onNavigateToInfo("faq") })
      Text("Contact Us", color = PrimaryAccent, style = MaterialTheme.typography.labelLarge, modifier = Modifier.clickable { onNavigateToInfo("contact") })
    }

    Spacer(modifier = Modifier.height(12.dp))
    Text("© 2026 ToolBox PK. All rights reserved.", style = MaterialTheme.typography.bodySmall, color = TextMuted)
  }
}
