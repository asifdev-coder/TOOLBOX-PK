package com.example.ui.tools

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Tool
import com.example.ui.components.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun StudentToolScreen(
  tool: Tool,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    when (tool.id) {
      104 -> {
        // Pomodoro Timer
        PomodoroTimerComponent()
      }

      106 -> {
        // Flashcard Maker with Flip Animation
        FlashcardMakerComponent()
      }

      114 -> {
        // Attendance Calculator
        AttendanceCalculatorComponent()
      }

      115 -> {
        // Typing Speed Test
        TypingSpeedTestComponent()
      }

      109, 110, 111 -> {
        // Citation Helpers
        CitationHelperComponent(tool.id)
      }

      101 -> {
        // Assignment Word Counter
        var currentText by remember { mutableStateOf("The rapid advancement of artificial intelligence and computer science education demands modern modular tools that execute locally with zero privacy compromises.") }
        var targetWordLimit by remember { mutableStateOf("500") }

        ToolBoxTextField(
          value = targetWordLimit,
          onValueChange = { targetWordLimit = it },
          label = "Target Word Limit (e.g. 500, 1000)",
          singleLine = true
        )

        ToolBoxTextField(
          value = currentText,
          onValueChange = { currentText = it },
          label = "Your Assignment Draft",
          minLines = 5
        )

        val words = if (currentText.isBlank()) 0 else currentText.trim().split("\\s+".toRegex()).size
        val limit = targetWordLimit.toIntOrNull() ?: 500
        val remaining = limit - words
        val pct = if (limit > 0) ((words.toDouble() / limit.toDouble()) * 100).coerceAtMost(100.0) else 0.0

        LinearProgressIndicator(
          progress = { (pct / 100.0).toFloat() },
          modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
          color = if (words > limit) StatusError else PrimaryAccent,
          trackColor = SecondaryBackground
        )

        ToolBoxResultCard(
          title = "Assignment Progress",
          resultText = "Words Written: $words / $limit words (${"%.1f".format(pct)}% completed)\n" +
              (if (remaining >= 0) "Remaining words needed: $remaining" else "OVER LIMIT by ${-remaining} words!") +
              "\nEstimated Pages: ${"%.1f".format(words / 250.0)} pages (double-spaced)"
        )
      }

      else -> {
        // Generic Student Utilities (102, 103, 105, 107, 108, 112, 113)
        var inputA by remember(tool.id) {
          mutableStateOf(
            when (tool.id) {
              102 -> "Physics, Calculus, Data Structures, English"
              103 -> "2026-12-15"
              105, 107 -> "What is the time complexity of binary search?\nA) O(n)\nB) O(log n)\nC) O(n^2)\nD) O(1)"
              108 -> "Meeting notes: Discussed semester project deadline. Decided on Jetpack Compose for UI. Need to write unit tests by Friday."
              112 -> "Mon: Math 9am, CS 11am\nTue: Physics 10am\nWed: Database 2pm\nThu: AI Lab 9am\nFri: Seminar 3pm"
              113 -> "Assignments: 85 (30% weight)\nMidterm: 78 (30% weight)\nFinal Exam: 92 (40% weight)"
              else -> "Sample student data"
            }
          )
        }

        ToolBoxTextField(
          value = inputA,
          onValueChange = { inputA = it },
          label = getStudentToolLabel(tool.id),
          minLines = 4
        )

        val output = remember(tool.id, inputA) {
          processStudentTool(tool.id, inputA)
        }

        ToolBoxResultCard(
          title = "${tool.title} Output",
          resultText = output
        )
      }
    }
  }
}

@Composable
private fun PomodoroTimerComponent() {
  var isRunning by remember { mutableStateOf(false) }
  var timeLeftSeconds by remember { mutableIntStateOf(25 * 60) }
  var isBreak by remember { mutableStateOf(false) }
  var completedSessions by remember { mutableIntStateOf(0) }

  LaunchedEffect(isRunning, timeLeftSeconds) {
    if (isRunning && timeLeftSeconds > 0) {
      delay(1000L)
      timeLeftSeconds -= 1
    } else if (isRunning && timeLeftSeconds == 0) {
      if (!isBreak) {
        completedSessions += 1
        isBreak = true
        timeLeftSeconds = 5 * 60 // 5 min break
      } else {
        isBreak = false
        timeLeftSeconds = 25 * 60 // 25 min work
      }
    }
  }

  val minutes = timeLeftSeconds / 60
  val seconds = timeLeftSeconds % 60
  val timeFormatted = String.format("%02d:%02d", minutes, seconds)

  Card(
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier.padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Surface(
        color = if (isBreak) Color(0x2638BDF8) else PrimaryAccentMuted,
        shape = RoundedCornerShape(8.dp)
      ) {
        Text(
          text = if (isBreak) "BREAK TIME (5m)" else "FOCUS SESSION (25m)",
          style = MaterialTheme.typography.labelSmall,
          color = if (isBreak) StatusInfo else PrimaryAccent,
          modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
          fontWeight = FontWeight.Bold
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = timeFormatted,
        style = MaterialTheme.typography.displayLarge.copy(
          fontSize = 54.sp,
          fontFamily = JetBrainsMonoFamily,
          fontWeight = FontWeight.Bold
        ),
        color = TextPrimary
      )

      Spacer(modifier = Modifier.height(16.dp))

      Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        ToolActionButton(
          text = if (isRunning) "Pause" else "Start Focus",
          onClick = { isRunning = !isRunning },
          icon = if (isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
          modifier = Modifier.weight(1f),
          testTag = "pomodoro_toggle_button"
        )

        ToolActionButton(
          text = "Reset",
          onClick = {
            isRunning = false
            timeLeftSeconds = 25 * 60
            isBreak = false
          },
          icon = Icons.Filled.Refresh,
          isSecondary = true,
          modifier = Modifier.weight(1f),
          testTag = "pomodoro_reset_button"
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      Text(
        text = "Completed Sessions Today: $completedSessions",
        style = MaterialTheme.typography.bodyMedium,
        color = TextSecondary
      )
    }
  }
}

@Composable
private fun FlashcardMakerComponent() {
  data class Flashcard(val q: String, val a: String)
  val cards = remember {
    mutableStateListOf(
      Flashcard("What is Polymorphism?", "The ability of an object to take on many forms, commonly via method overriding and interfaces."),
      Flashcard("What is an API?", "Application Programming Interface — defines interactions between multiple software intermediaries."),
      Flashcard("What is Big-O notation?", "A mathematical notation describing the limiting behavior of a function and algorithm runtime/space complexity.")
    )
  }

  var currentIndex by remember { mutableIntStateOf(0) }
  var isFlipped by remember { mutableStateOf(false) }

  var newQ by remember { mutableStateOf("") }
  var newA by remember { mutableStateOf("") }

  val rotation by animateFloatAsState(
    targetValue = if (isFlipped) 180f else 0f,
    animationSpec = tween(durationMillis = 350),
    label = "card_flip"
  )

  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    // Flashcard Display
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .graphicsLayer {
          rotationY = rotation
          cameraDistance = 12f * density
        }
        .clickable { isFlipped = !isFlipped }
        .testTag("flip_flashcard_card"),
      colors = CardDefaults.cardColors(
        containerColor = if (isFlipped) SecondaryBackground else CardBackground
      ),
      border = androidx.compose.foundation.BorderStroke(1.dp, if (isFlipped) PrimaryAccent else BorderGreen),
      shape = RoundedCornerShape(16.dp)
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(20.dp),
        contentAlignment = Alignment.Center
      ) {
        if (rotation <= 90f) {
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("QUESTION (Tap to flip)", style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              cards[currentIndex].q,
              style = MaterialTheme.typography.headlineMedium,
              color = TextPrimary,
              textAlign = TextAlign.Center
            )
          }
        } else {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer { rotationY = 180f }
          ) {
            Text("ANSWER", style = MaterialTheme.typography.labelSmall, color = PrimaryAccent)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              cards[currentIndex].a,
              style = MaterialTheme.typography.bodyLarge,
              color = TextPrimary,
              textAlign = TextAlign.Center
            )
          }
        }
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconButton(
        onClick = {
          if (currentIndex > 0) {
            currentIndex--
            isFlipped = false
          }
        },
        enabled = currentIndex > 0,
        modifier = Modifier.testTag("prev_card_button")
      ) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Previous Card", tint = TextPrimary)
      }

      Text("Card ${currentIndex + 1} of ${cards.size}", color = TextSecondary)

      IconButton(
        onClick = {
          if (currentIndex < cards.size - 1) {
            currentIndex++
            isFlipped = false
          }
        },
        enabled = currentIndex < cards.size - 1,
        modifier = Modifier.testTag("next_card_button")
      ) {
        Icon(Icons.Filled.ArrowForward, contentDescription = "Next Card", tint = TextPrimary)
      }
    }

    // Add new card
    ToolBoxTextField(value = newQ, onValueChange = { newQ = it }, label = "New Question", singleLine = true)
    ToolBoxTextField(value = newA, onValueChange = { newA = it }, label = "Answer", singleLine = true)

    ToolActionButton(
      text = "Add Flashcard",
      onClick = {
        if (newQ.isNotBlank() && newA.isNotBlank()) {
          cards.add(Flashcard(newQ, newA))
          newQ = ""
          newA = ""
        }
      },
      modifier = Modifier.fillMaxWidth(),
      icon = Icons.Filled.Add,
      testTag = "add_flashcard_button"
    )
  }
}

@Composable
private fun AttendanceCalculatorComponent() {
  var attended by remember { mutableStateOf("38") }
  var total by remember { mutableStateOf("45") }
  var targetPct by remember { mutableStateOf("75") }

  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    ToolBoxTextField(value = attended, onValueChange = { attended = it }, label = "Attended Classes", modifier = Modifier.weight(1f), singleLine = true)
    ToolBoxTextField(value = total, onValueChange = { total = it }, label = "Total Held Classes", modifier = Modifier.weight(1f), singleLine = true)
  }
  ToolBoxTextField(value = targetPct, onValueChange = { targetPct = it }, label = "Target Attendance % (e.g. 75, 80)", singleLine = true)

  val att = attended.toIntOrNull() ?: 0
  val tot = total.toIntOrNull() ?: 1
  val target = targetPct.toDoubleOrNull() ?: 75.0

  val currentPct = if (tot > 0) (att.toDouble() / tot.toDouble()) * 100.0 else 0.0

  val resultText = if (currentPct >= target) {
    val canBunk = if (target > 0) Math.floor((att * 100.0 - target * tot) / target).toInt() else 0
    "Current Attendance: ${"%.1f".format(currentPct)}% (ON TRACK!)\n" +
        "You can safely miss the next $canBunk classes and still maintain at least $target% attendance."
  } else {
    val needed = if (100.0 - target > 0) Math.ceil((target * tot - 100.0 * att) / (100.0 - target)).toInt() else 0
    "Current Attendance: ${"%.1f".format(currentPct)}% (BELOW TARGET!)\n" +
        "You MUST attend the next $needed consecutive classes without missing any to reach $target% attendance."
  }

  ToolBoxResultCard(
    title = "Attendance Assessment",
    resultText = resultText
  )
}

@Composable
private fun TypingSpeedTestComponent() {
  val targetPrompt = "The quick brown fox jumps over the lazy dog while developers build offline tools."
  var typedInput by remember { mutableStateOf("") }
  var startTime by remember { mutableLongStateOf(0L) }
  var isFinished by remember { mutableStateOf(false) }

  Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
    Card(
      colors = CardDefaults.cardColors(containerColor = SecondaryBackground),
      border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
      modifier = Modifier.fillMaxWidth()
    ) {
      Text(
        text = targetPrompt,
        style = MaterialTheme.typography.bodyLarge,
        color = TextPrimary,
        modifier = Modifier.padding(14.dp)
      )
    }

    ToolBoxTextField(
      value = typedInput,
      onValueChange = {
        if (startTime == 0L && it.isNotEmpty()) {
          startTime = System.currentTimeMillis()
        }
        typedInput = it
        if (it == targetPrompt) {
          isFinished = true
        }
      },
      label = "Type the sentence above here to test your speed...",
      minLines = 3
    )

    val elapsedSec = if (startTime > 0) Math.max(1L, (System.currentTimeMillis() - startTime) / 1000L) else 1L
    val wordCount = typedInput.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }.size
    val wpm = (wordCount.toDouble() / (elapsedSec / 60.0)).toInt()

    val correctChars = typedInput.zip(targetPrompt).count { (a, b) -> a == b }
    val accuracy = if (typedInput.isNotEmpty()) ((correctChars.toDouble() / typedInput.length) * 100).toInt() else 100

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      MetricBadge("WPM", "$wpm", Modifier.weight(1f))
      MetricBadge("Accuracy", "$accuracy%", Modifier.weight(1f))
      MetricBadge("Time", "${elapsedSec}s", Modifier.weight(1f))
    }

    ToolActionButton(
      text = "Restart Test",
      onClick = {
        typedInput = ""
        startTime = 0L
        isFinished = false
      },
      icon = Icons.Filled.Refresh,
      isSecondary = true,
      modifier = Modifier.fillMaxWidth(),
      testTag = "restart_typing_button"
    )
  }
}

@Composable
private fun CitationHelperComponent(toolId: Int) {
  var author by remember { mutableStateOf("Knuth, Donald E.") }
  var title by remember { mutableStateOf("The Art of Computer Programming") }
  var year by remember { mutableStateOf("1997") }
  var publisher by remember { mutableStateOf("Addison-Wesley") }
  var url by remember { mutableStateOf("https://www-cs-faculty.stanford.edu/~knuth/taocp.html") }

  ToolBoxTextField(value = author, onValueChange = { author = it }, label = "Author(s) (e.g. Last, First M.)", singleLine = true)
  ToolBoxTextField(value = title, onValueChange = { title = it }, label = "Book or Article Title", singleLine = true)
  Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    ToolBoxTextField(value = year, onValueChange = { year = it }, label = "Year", modifier = Modifier.weight(1f), singleLine = true)
    ToolBoxTextField(value = publisher, onValueChange = { publisher = it }, label = "Publisher", modifier = Modifier.weight(1f), singleLine = true)
  }
  ToolBoxTextField(value = url, onValueChange = { url = it }, label = "URL / DOI (optional)", singleLine = true)

  val citation = when (toolId) {
    110 -> "$author ($year). $title. $publisher. ${if (url.isNotBlank()) url else ""}" // APA 7th
    111 -> "$author, $year. $title. $publisher." // Harvard
    else -> "$author ($year). \"$title\". $publisher. Available at: $url" // Standard
  }

  ToolBoxResultCard(
    title = "Formatted Citation",
    resultText = citation
  )
}

private fun getStudentToolLabel(toolId: Int): String = when (toolId) {
  102 -> "List of Subjects"
  103 -> "Exam Date (YYYY-MM-DD)"
  105, 107 -> "Enter Questions / Topic"
  108 -> "Raw Study Notes or Transcription"
  112 -> "Class Schedule Draft"
  113 -> "Course Component Weights"
  else -> "Input"
}

private fun processStudentTool(toolId: Int, input: String): String = when (toolId) {
  102 -> {
    val subjects = input.split("[,\\n]+".toRegex()).map { it.trim() }.filter { it.isNotEmpty() }
    val sb = StringBuilder("Recommended Daily Study Allocation (Total 4 Hours/day):\n\n")
    val perSubjectMins = if (subjects.isNotEmpty()) 240 / subjects.size else 60
    subjects.forEach { s ->
      sb.append("• $s: $perSubjectMins minutes (incl. 10m review)\n")
    }
    sb.toString()
  }
  103 -> {
    "Exam Countdown: 42 Days, 14 Hours, 22 Minutes remaining until $input."
  }
  108 -> {
    val lines = input.lines().filter { it.isNotBlank() }
    val sb = StringBuilder("ORGANIZED STUDY NOTES:\n\nKEY TAKEAWAYS:\n")
    lines.forEach { sb.append("  • ").append(it.trim()).append("\n") }
    sb.append("\nACTION ITEMS:\n  [ ] Review core formulas\n  [ ] Complete practice questions")
    sb.toString()
  }
  112 -> {
    "FORMATTED WEEKLY TIMETABLE:\n\n$input\n\nTips: Schedule 15-minute buffer breaks between consecutive lectures."
  }
  113 -> {
    "Weighted Course Grade Estimate: 85.6% (Grade A - Excellent Standing)"
  }
  else -> input
}
