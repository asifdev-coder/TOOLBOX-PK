package com.example.ui.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Tool
import com.example.ui.components.*
import com.example.ui.theme.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun SeoMarketingToolScreen(
  tool: Tool,
  modifier: Modifier = Modifier
) {
  var inputA by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        116 -> "ToolBox PK — 155+ Free Online & Offline Tools Suite"
        117 -> "Access 155+ free high performance utilities for images, PDFs, text formatting, developer code, and calculations. 100% private and runs on your device."
        118 -> "ToolBox PK provides free online tools for everyone. These tools help developers, students and creators build faster. Explore our free offline tools today."
        119 -> "technology, web development, coding, productivity"
        120 -> "Android App Development"
        121 -> "Complete guide to mastering Kotlin and Jetpack Compose in 2026."
        122 -> "android, jetpack compose, kotlin, tutorial, mobile development"
        123 -> "Excited to launch ToolBox PK!\n\n155+ free tools all in one app.\nNo login needed.\nTry it today! #tech #coding"
        124 -> "Software Engineer & Open Source Enthusiast"
        125 -> "ToolBox PK — Free Developer & Student Tools"
        126 -> "How To Build Fast Android Apps With Jetpack Compose & Kotlin"
        127 -> "https://toolboxpk.com"
        128 -> "https://toolboxpk.com/sitemap.xml"
        129 -> "https://toolboxpk.com/\nhttps://toolboxpk.com/tools\nhttps://toolboxpk.com/about"
        130 -> "fast secure tools offline privacy android web tools fast performance privacy"
        else -> "ToolBox PK SEO"
      }
    )
  }

  var utmSource by remember { mutableStateOf("twitter") }
  var utmMedium by remember { mutableStateOf("social") }
  var utmCampaign by remember { mutableStateOf("spring_launch") }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    when (tool.id) {
      116 -> {
        // Meta Title Checker
        ToolBoxTextField(
          value = inputA,
          onValueChange = { inputA = it },
          label = "Meta Title",
          singleLine = true
        )

        val charCount = inputA.length
        val status = when {
          charCount in 50..60 -> "Optimal Length (50-60 characters)"
          charCount < 50 -> "Too Short ($charCount / 60 characters) — add relevant keywords"
          else -> "Too Long ($charCount / 60 characters) — will be truncated by Google"
        }

        Text(
          text = status,
          style = MaterialTheme.typography.bodySmall,
          color = if (charCount in 40..65) PrimaryAccent else StatusWarning,
          fontWeight = FontWeight.Bold
        )

        // Google SERP Snippet Preview Box
        Card(
          colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
          border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text("Google Search Result Preview", style = MaterialTheme.typography.labelSmall, color = TextMuted)
            Spacer(modifier = Modifier.height(6.dp))
            Text("https://toolboxpk.com › tools", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
            Text(
              text = if (charCount > 60) inputA.take(57) + "..." else inputA,
              style = MaterialTheme.typography.titleMedium,
              color = Color(0xFF8AB4F8)
            )
            Text(
              text = "Access 155+ free utilities for images, PDFs, text, developer code, and calculations. Runs offline...",
              style = MaterialTheme.typography.bodySmall,
              color = TextSecondary
            )
          }
        }
      }

      117 -> {
        // Meta Description Checker
        ToolBoxTextField(
          value = inputA,
          onValueChange = { inputA = it },
          label = "Meta Description",
          minLines = 4
        )

        val charCount = inputA.length
        val status = when {
          charCount in 140..160 -> "Optimal Length (140-160 characters)"
          charCount < 140 -> "Short ($charCount / 160 characters)"
          else -> "Too Long ($charCount / 160 characters) — likely truncated on mobile"
        }

        Text(
          text = status,
          style = MaterialTheme.typography.bodySmall,
          color = if (charCount in 130..165) PrimaryAccent else StatusWarning,
          fontWeight = FontWeight.Bold
        )

        ToolBoxResultCard(
          title = "SERP Description Analysis",
          resultText = "Character Count: $charCount / 160\nEstimated Mobile Truncation: ${if (charCount > 120) "Truncated on small screens" else "Safe"}\nEstimated Desktop Truncation: ${if (charCount > 160) "Truncated with '...'" else "Fully visible"}"
        )
      }

      118 -> {
        // Keyword Density Checker
        ToolBoxTextField(
          value = inputA,
          onValueChange = { inputA = it },
          label = "Content Text to Analyze",
          minLines = 5
        )

        val densityAnalysis = remember(inputA) {
          analyzeKeywordDensity(inputA)
        }

        ToolBoxResultCard(
          title = "Keyword Density Breakdown",
          resultText = densityAnalysis,
          isCode = true
        )
      }

      127 -> {
        // UTM Link Builder
        ToolBoxTextField(value = inputA, onValueChange = { inputA = it }, label = "Website Base URL", singleLine = true)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          ToolBoxTextField(value = utmSource, onValueChange = { utmSource = it }, label = "Campaign Source (utm_source)", modifier = Modifier.weight(1f), singleLine = true)
          ToolBoxTextField(value = utmMedium, onValueChange = { utmMedium = it }, label = "Medium (utm_medium)", modifier = Modifier.weight(1f), singleLine = true)
        }
        ToolBoxTextField(value = utmCampaign, onValueChange = { utmCampaign = it }, label = "Campaign Name (utm_campaign)", singleLine = true)

        val utmUrl = remember(inputA, utmSource, utmMedium, utmCampaign) {
          buildUtmUrl(inputA, utmSource, utmMedium, utmCampaign)
        }

        ToolBoxResultCard(
          title = "Generated Campaign URL",
          resultText = utmUrl,
          isCode = true
        )
      }

      else -> {
        // Generic SEO generators (119 - 130)
        ToolBoxTextField(
          value = inputA,
          onValueChange = { inputA = it },
          label = getSeoLabel(tool.id),
          minLines = 4
        )

        val output = remember(tool.id, inputA) {
          processSeoTool(tool.id, inputA)
        }

        ToolBoxResultCard(
          title = "${tool.title} Output",
          resultText = output,
          isCode = tool.id in listOf(126, 128, 129)
        )
      }
    }
  }
}

private fun getSeoLabel(toolId: Int): String = when (toolId) {
  119 -> "Keywords or Niche (comma separated)"
  120 -> "Video Topic / Subject"
  121 -> "Video Title & Core Summary"
  122 -> "Video Topic or Focus Keywords"
  123 -> "Raw Caption Draft"
  124 -> "Your Role / Skills / Vibe"
  125 -> "Page Title & Content"
  126 -> "Article or Page Title"
  128 -> "Website Domain / Sitemap URL"
  129 -> "URLs to include in Sitemap (one per line)"
  130 -> "Text to extract word frequency"
  else -> "Input Text"
}

private fun processSeoTool(toolId: Int, input: String): String = when (toolId) {
  119 -> {
    val tags = input.split("[,\\s]+".toRegex()).filter { it.isNotBlank() }
    val sb = StringBuilder("RECOMMENDED HASHTAGS:\n\n")
    sb.append("High Reach:\n")
    tags.forEach { sb.append("#").append(it.lowercase()).append(" ") }
    sb.append("\n\nNiche Specific:\n")
    tags.forEach { sb.append("#").append(it.lowercase()).append("tips #").append(it.lowercase()).append("community ") }
    sb.toString()
  }
  120 -> {
    "10 HIGH-CTR YOUTUBE TITLE FORMULAS:\n\n" +
        "1. How To $input (The Complete 2026 Guide)\n" +
        "2. Why Most People Fail At $input (And How To Fix It)\n" +
        "3. I Tried $input For 30 Days — Here's What Happened\n" +
        "4. Top 7 $input Secrets You Wish You Knew Sooner\n" +
        "5. Stop Doing $input Like This! (Do THIS Instead)"
  }
  121 -> {
    "YOUTUBE DESCRIPTION TEMPLATE:\n\n" +
        "$input\n\n" +
        "⏰ TIMESTAMPS:\n" +
        "0:00 - Introduction\n" +
        "1:15 - Core Concepts\n" +
        "4:30 - Step-by-Step Walkthrough\n" +
        "8:45 - Key Best Practices & Conclusion\n\n" +
        "🔗 HELPFUL LINKS:\n" +
        "• Download ToolBox PK: https://toolboxpk.com\n\n" +
        "⚠️ DISCLAIMER: Content provided for educational purposes."
  }
  122 -> {
    val words = input.split("[,\\s]+".toRegex()).filter { it.isNotBlank() }
    val tags = words.map { it.lowercase() } + words.map { "${it.lowercase()} tutorial" } + listOf("toolbox pk", "guide", "2026")
    tags.joinToString(", ")
  }
  123 -> {
    // Instagram Caption with clean spacing
    input.lines().joinToString("\n.\n") { it.trim() }
  }
  124 -> {
    "PROFILE BIO VARIATIONS:\n\n" +
        "1. Minimalist:\n$input | Building fast digital experiences ⚡\n\n" +
        "2. Bullet Points:\n👨‍💻 $input\n🚀 155+ open source tools created\n📍 Global | DM for collaboration\n\n" +
        "3. Action-Oriented:\nHelping creators scale with $input. Join the community 👇"
  }
  126 -> {
    input.lowercase()
      .replace("[^a-z0-9\\s-]".toRegex(), "")
      .trim()
      .replace("\\s+".toRegex(), "-")
  }
  128 -> {
    "# robots.txt generated by ToolBox PK\n" +
        "User-agent: *\n" +
        "Allow: /\n" +
        "Disallow: /admin/\n" +
        "Disallow: /api/\n\n" +
        "Sitemap: $input"
  }
  129 -> {
    val urls = input.lines().map { it.trim() }.filter { it.isNotEmpty() }
    val sb = StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
    sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n")
    urls.forEach { url ->
      sb.append("  <url>\n")
      sb.append("    <loc>$url</loc>\n")
      sb.append("    <changefreq>weekly</changefreq>\n")
      sb.append("    <priority>0.8</priority>\n")
      sb.append("  </url>\n")
    }
    sb.append("</urlset>")
    sb.toString()
  }
  130 -> {
    val words = input.lowercase().split("\\W+".toRegex()).filter { it.length > 2 }
    val freq = words.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }.take(15)
    val sb = StringBuilder("TOP 15 WORD FREQUENCIES:\n\n")
    freq.forEachIndexed { idx, pair ->
      sb.append("${idx + 1}. \"${pair.first}\" -> ${pair.second} occurrences\n")
    }
    sb.toString()
  }
  else -> input
}

private fun analyzeKeywordDensity(text: String): String {
  val words = text.lowercase().split("\\W+".toRegex()).filter { it.length > 2 }
  val total = words.size
  if (total == 0) return "Please enter text to analyze."

  val freq = words.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }.take(10)
  val sb = StringBuilder("Total Words Analyzed: $total\n\n")
  sb.append("% Density | Occurrences | Keyword\n")
  sb.append("------------------------------------\n")
  freq.forEach { (word, count) ->
    val density = (count.toDouble() / total.toDouble()) * 100.0
    val warning = if (density > 2.5) " ⚠️ High (Check stuffing)" else ""
    sb.append("%5.1f%%   | %11d | %s%s\n".format(density, count, word, warning))
  }
  return sb.toString()
}

private fun buildUtmUrl(baseUrl: String, source: String, medium: String, campaign: String): String {
  val cleanBase = baseUrl.trim()
  val sep = if (cleanBase.contains("?")) "&" else "?"
  val encSource = URLEncoder.encode(source.trim(), StandardCharsets.UTF_8.toString())
  val encMed = URLEncoder.encode(medium.trim(), StandardCharsets.UTF_8.toString())
  val encCamp = URLEncoder.encode(campaign.trim(), StandardCharsets.UTF_8.toString())
  return "$cleanBase${sep}utm_source=$encSource&utm_medium=$encMed&utm_campaign=$encCamp"
}
