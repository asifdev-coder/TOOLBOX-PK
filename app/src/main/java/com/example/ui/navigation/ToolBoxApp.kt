package com.example.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.local.ToolPreferences
import com.example.data.model.ToolCategory
import com.example.ui.screens.*
import com.example.ui.theme.*

sealed class Screen {
  object Home : Screen()
  data class AllTools(val category: ToolCategory? = null) : Screen()
  object Favorites : Screen()
  object History : Screen()
  data class ToolDetail(val toolId: Int) : Screen()
  data class Info(val pageType: String) : Screen()
}

enum class BottomTab(
  val title: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val tag: String
) {
  HOME("Home", Icons.Filled.Home, Icons.Outlined.Home, "tab_home"),
  TOOLS("All Tools", Icons.Filled.GridView, Icons.Outlined.GridView, "tab_tools"),
  FAVORITES("Favorites", Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder, "tab_favorites"),
  HISTORY("History", Icons.Filled.History, Icons.Outlined.History, "tab_history")
}

@Composable
fun ToolBoxApp() {
  val context = LocalContext.current
  val toolPreferences = remember { ToolPreferences.getInstance(context) }

  var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
  var currentTab by remember { mutableStateOf(BottomTab.HOME) }
  val screenStack = remember { mutableStateListOf<Screen>(Screen.Home) }

  fun navigateTo(screen: Screen) {
    screenStack.add(screen)
    currentScreen = screen
  }

  fun navigateBack() {
    if (screenStack.size > 1) {
      screenStack.removeAt(screenStack.size - 1)
      currentScreen = screenStack.last()
      // update tab highlight if applicable
      when (currentScreen) {
        is Screen.Home -> currentTab = BottomTab.HOME
        is Screen.AllTools -> currentTab = BottomTab.TOOLS
        is Screen.Favorites -> currentTab = BottomTab.FAVORITES
        is Screen.History -> currentTab = BottomTab.HISTORY
        else -> {}
      }
    }
  }

  val showBottomNav = currentScreen !is Screen.ToolDetail && currentScreen !is Screen.Info

  Scaffold(
    containerColor = BackgroundDark,
    contentWindowInsets = WindowInsets.safeDrawing,
    bottomBar = {
      if (showBottomNav) {
        NavigationBar(
          containerColor = CardBackground,
          contentColor = TextPrimary,
          tonalElevation = 8.dp,
          modifier = Modifier
            .border(width = 1.dp, color = BorderGreen)
            .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
          BottomTab.values().forEach { tab ->
            val isSelected = currentTab == tab
            NavigationBarItem(
              selected = isSelected,
              onClick = {
                currentTab = tab
                val targetScreen = when (tab) {
                  BottomTab.HOME -> Screen.Home
                  BottomTab.TOOLS -> Screen.AllTools()
                  BottomTab.FAVORITES -> Screen.Favorites
                  BottomTab.HISTORY -> Screen.History
                }
                screenStack.clear()
                screenStack.add(targetScreen)
                currentScreen = targetScreen
              },
              icon = {
                Icon(
                  imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                  contentDescription = tab.title
                )
              },
              label = {
                Text(tab.title)
              },
              colors = NavigationBarItemDefaults.colors(
                selectedIconColor = BackgroundDark,
                selectedTextColor = PrimaryAccent,
                indicatorColor = PrimaryAccent,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
              ),
              modifier = Modifier.testTag(tab.tag)
            )
          }
        }
      }
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      when (val screen = currentScreen) {
        is Screen.Home -> {
          HomeScreen(
            toolPreferences = toolPreferences,
            onNavigateToTool = { id -> navigateTo(Screen.ToolDetail(id)) },
            onNavigateToCategory = { cat ->
              currentTab = BottomTab.TOOLS
              navigateTo(Screen.AllTools(cat))
            },
            onNavigateToAllTools = {
              currentTab = BottomTab.TOOLS
              navigateTo(Screen.AllTools())
            },
            onNavigateToInfo = { page -> navigateTo(Screen.Info(page)) }
          )
        }

        is Screen.AllTools -> {
          AllToolsScreen(
            toolPreferences = toolPreferences,
            initialCategory = screen.category,
            onNavigateToTool = { id -> navigateTo(Screen.ToolDetail(id)) }
          )
        }

        is Screen.Favorites -> {
          FavoritesScreen(
            toolPreferences = toolPreferences,
            onNavigateToTool = { id -> navigateTo(Screen.ToolDetail(id)) },
            onExploreTools = {
              currentTab = BottomTab.TOOLS
              navigateTo(Screen.AllTools())
            }
          )
        }

        is Screen.History -> {
          HistoryScreen(
            toolPreferences = toolPreferences,
            onNavigateToTool = { id -> navigateTo(Screen.ToolDetail(id)) },
            onExploreTools = {
              currentTab = BottomTab.TOOLS
              navigateTo(Screen.AllTools())
            }
          )
        }

        is Screen.ToolDetail -> {
          ToolDetailScreen(
            toolId = screen.toolId,
            toolPreferences = toolPreferences,
            onBack = { navigateBack() },
            onNavigateToTool = { id -> navigateTo(Screen.ToolDetail(id)) }
          )
        }

        is Screen.Info -> {
          InfoScreen(
            pageType = screen.pageType,
            onBack = { navigateBack() }
          )
        }
      }
    }
  }
}
