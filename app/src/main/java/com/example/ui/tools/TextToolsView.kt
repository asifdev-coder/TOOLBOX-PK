package com.example.ui.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Tool
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun TextToolScreen(
  tool: Tool,
  modifier: Modifier = Modifier
) {
  var inputText by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        56 -> "ToolBox PK offers 155+ free tools.\nEverything runs on your device.\nFast and secure."
        58 -> "Hello ToolBox PK"
        59 -> "01001000 01101001"
        else -> "ToolBox PK is a powerful suite of 155+ free utility tools designed for productivity, students, developers, and creators."
      }
    )
  }

  // Secondary text for Diff or Find/Replace
  var secondaryText by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        56 -> "ToolBox PK offers 155+ free offline tools.\nEverything runs locally in your device.\nSuper fast and 100% secure."
        54 -> "suite"
        else -> ""
      }
    )
  }

  var tertiaryText by remember(tool.id) {
    mutableStateOf(if (tool.id == 54) "collection" else "")
  }

  var repeatCount by remember(tool.id) { mutableIntStateOf(5) }
  var repeatSeparator by remember(tool.id) { mutableStateOf(" ") }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    when (tool.id) {
      41, 42, 43, 44, 55, 60 -> {
        // Text Statistics & Counters
        val words = if (inputText.isBlank()) 0 else inputText.trim().split("\\s+".toRegex()).size
        val characters = inputText.length
        val charsNoSpaces = inputText.replace("\\s+".toRegex(), "").length
        val sentences = if (inputText.isBlank()) 0 else inputText.split("[.!?]+".toRegex()).filter { it.isNotBlank() }.size
        val paragraphs = if (inputText.isBlank()) 0 else inputText.split("\n+".toRegex()).filter { it.isNotBlank() }.size
        val lines = if (inputText.isEmpty()) 0 else inputText.lines().size
        val readingTimeMinutes = Math.max(1, (words / 200.0).toInt())
        val syllables = estimateSyllables(inputText)
        val fleschScore = calculateFleschReadingEase(words, sentences, syllables)

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          MetricBadge("Words", "$words", Modifier.weight(1f))
          MetricBadge("Chars", "$characters", Modifier.weight(1f))
          MetricBadge("Sentences", "$sentences", Modifier.weight(1f))
          MetricBadge("Lines", "$lines", Modifier.weight(1f))
        }

        if (tool.id == 60) {
          // Readability Card
          val readingLevel = when {
            fleschScore >= 90.0 -> "Very Easy (5th grade)"
            fleschScore >= 80.0 -> "Easy (6th grade)"
            fleschScore >= 70.0 -> "Fairly Easy (7th grade)"
            fleschScore >= 60.0 -> "Plain English (8th-9th grade)"
            fleschScore >= 50.0 -> "Fairly Difficult (High school)"
            fleschScore >= 30.0 -> "Difficult (College)"
            else -> "Very Confusing (Professional/Academic)"
          }

          Card(
            colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
            border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text("Flesch Reading Ease Score", style = MaterialTheme.typography.titleMedium, color = PrimaryAccent)
              Spacer(modifier = Modifier.height(4.dp))
              Text("%.1f / 100".format(fleschScore), style = MaterialTheme.typography.displayMedium, color = TextPrimary)
              Text("Reading Level: $readingLevel", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
              Spacer(modifier = Modifier.height(4.dp))
              Text("Est. reading time: ~$readingTimeMinutes min", style = MaterialTheme.typography.bodySmall, color = TextMuted)
            }
          }
        }

        ToolBoxTextField(
          value = inputText,
          onValueChange = { inputText = it },
          label = "Input Text to Analyze",
          minLines = 5,
          placeholder = "Type or paste your text here..."
        )
      }

      56 -> {
        // Text Diff Checker
        ToolBoxTextField(
          value = inputText,
          onValueChange = { inputText = it },
          label = "Original Text",
          minLines = 4
        )

        ToolBoxTextField(
          value = secondaryText,
          onValueChange = { secondaryText = it },
          label = "Modified Text to Compare",
          minLines = 4
        )

        val diffResult = remember(inputText, secondaryText) {
          computeSimpleDiff(inputText, secondaryText)
        }

        ToolBoxResultCard(
          title = "Side-by-Side Diff Analysis",
          resultText = diffResult,
          isCode = true
        )
      }

      54 -> {
        // Find and Replace
        ToolBoxTextField(
          value = inputText,
          onValueChange = { inputText = it },
          label = "Source Text",
          minLines = 4
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          ToolBoxTextField(
            value = secondaryText,
            onValueChange = { secondaryText = it },
            label = "Find",
            modifier = Modifier.weight(1f),
            singleLine = true
          )
          ToolBoxTextField(
            value = tertiaryText,
            onValueChange = { tertiaryText = it },
            label = "Replace With",
            modifier = Modifier.weight(1f),
            singleLine = true
          )
        }

        val replaced = remember(inputText, secondaryText, tertiaryText) {
          if (secondaryText.isEmpty()) inputText else inputText.replace(secondaryText, tertiaryText)
        }

        ToolBoxResultCard(
          title = "Replaced Output",
          resultText = replaced
        )
      }

      57 -> {
        // Text Repeater
        ToolBoxTextField(
          value = inputText,
          onValueChange = { inputText = it },
          label = "Text to Repeat",
          singleLine = true
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          ToolBoxTextField(
            value = repeatCount.toString(),
            onValueChange = { repeatCount = it.toIntOrNull()?.coerceIn(1, 1000) ?: 1 },
            label = "Repeat Times (1-1000)",
            modifier = Modifier.weight(1f),
            singleLine = true
          )
          ToolBoxTextField(
            value = repeatSeparator,
            onValueChange = { repeatSeparator = it },
            label = "Separator (e.g. space, newline)",
            modifier = Modifier.weight(1f),
            singleLine = true
          )
        }

        val repeated = remember(inputText, repeatCount, repeatSeparator) {
          List(repeatCount) { inputText }.joinToString(repeatSeparator)
        }

        ToolBoxResultCard(
          title = "Repeated Output",
          resultText = repeated
        )
      }

      else -> {
        // Standard Text Transformations (Case, Clean, Reverse, Sorter, Slug, Binary)
        ToolBoxTextField(
          value = inputText,
          onValueChange = { inputText = it },
          label = "Input Text",
          minLines = 4,
          placeholder = "Enter text..."
        )

        val output = remember(tool.id, inputText) {
          processTextTransformation(tool.id, inputText)
        }

        ToolBoxResultCard(
          title = "${tool.title} Result",
          resultText = output,
          isCode = tool.id in listOf(53, 58, 59)
        )
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
      ToolActionButton(
        text = "Clear Text",
        onClick = { inputText = "" },
        modifier = Modifier.weight(1f),
        icon = Icons.Filled.Clear,
        isSecondary = true,
        testTag = "clear_text_button"
      )

      ToolActionButton(
        text = "Sample Text",
        onClick = {
          inputText = "The quick brown fox jumps over the lazy dog. ToolBox PK delivers 155+ high performance tools."
        },
        modifier = Modifier.weight(1f),
        icon = Icons.Filled.Refresh,
        isSecondary = true,
        testTag = "sample_text_button"
      )
    }
  }
}

private fun processTextTransformation(toolId: Int, text: String): String {
  return when (toolId) {
    45 -> text.uppercase()
    46 -> text.lowercase()
    47 -> text.split(" ").joinToString(" ") { word ->
      word.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
    48 -> {
      val regex = "(^|[.!?]\\s+)([a-z])".toRegex()
      regex.replace(text.lowercase()) { match ->
        match.groupValues[1] + match.groupValues[2].uppercase()
      }
    }
    49 -> text.trim().replace("\\s+".toRegex(), " ")
    50 -> text.lines().distinct().joinToString("\n")
    51 -> text.reversed()
    52 -> text.lines().sorted().joinToString("\n")
    53 -> {
      text.lowercase()
        .replace("[^a-z0-9\\s-]".toRegex(), "")
        .trim()
        .replace("\\s+".toRegex(), "-")
    }
    58 -> {
      text.toByteArray(Charsets.UTF_8).joinToString(" ") { b ->
        String.format("%8s", Integer.toBinaryString(b.toInt() and 0xFF)).replace(' ', '0')
      }
    }
    59 -> {
      try {
        val clean = text.trim().replace("\\s+".toRegex(), " ")
        if (clean.isEmpty()) return ""
        val bytes = clean.split(" ").map { it.toInt(2).toByte() }.toByteArray()
        String(bytes, Charsets.UTF_8)
      } catch (e: Exception) {
        "Error: Invalid binary input. Ensure space-separated 8-bit binary numbers (e.g. 01001000 01101001)."
      }
    }
    else -> text
  }
}

private fun computeSimpleDiff(orig: String, mod: String): String {
  val origLines = orig.lines()
  val modLines = mod.lines()
  val sb = StringBuilder()

  var i = 0
  var j = 0
  while (i < origLines.size || j < modLines.size) {
    if (i < origLines.size && j < modLines.size && origLines[i] == modLines[j]) {
      sb.append("  ").append(origLines[i]).append("\n")
      i++
      j++
    } else if (i < origLines.size && (j >= modLines.size || !modLines.contains(origLines[i]))) {
      sb.append("- ").append(origLines[i]).append("\n")
      i++
    } else if (j < modLines.size) {
      sb.append("+ ").append(modLines[j]).append("\n")
      j++
    } else {
      break
    }
  }
  return sb.toString().trimEnd()
}

private fun estimateSyllables(text: String): Int {
  val words = text.lowercase().split("\\s+".toRegex())
  var count = 0
  for (word in words) {
    val clean = word.replace("[^a-z]".toRegex(), "")
    if (clean.length <= 3) {
      count += 1
      continue
    }
    val vowels = clean.replace("[^aeiouy]+".toRegex(), " ")
    val parts = vowels.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }
    var syllables = parts.size
    if (clean.endsWith("e")) syllables = Math.max(1, syllables - 1)
    count += Math.max(1, syllables)
  }
  return Math.max(1, count)
}

private fun calculateFleschReadingEase(words: Int, sentences: Int, syllables: Int): Double {
  if (words == 0 || sentences == 0) return 100.0
  val wordsPerSentence = words.toDouble() / sentences.toDouble()
  val syllablesPerWord = syllables.toDouble() / words.toDouble()
  val score = 206.835 - (1.015 * wordsPerSentence) - (84.6 * syllablesPerWord)
  return score.coerceIn(0.0, 100.0)
}
