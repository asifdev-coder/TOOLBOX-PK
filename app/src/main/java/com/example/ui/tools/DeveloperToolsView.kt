package com.example.ui.tools

import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DeveloperToolScreen(
  tool: Tool,
  modifier: Modifier = Modifier
) {
  var inputCode by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        61, 62, 63 -> "{\"name\":\"ToolBox PK\",\"version\":1.0,\"features\":[\"Offline First\",\"155+ Tools\",\"Secure\"],\"stats\":{\"downloads\":50000,\"rating\":4.9}}"
        64 -> "<toolbox><app name=\"ToolBox PK\"><tools total=\"155\"/><security status=\"safe\"/></app></toolbox>"
        65 -> "<div><h1>ToolBox PK</h1><p>155+ Free developer utilities</p><ul><li>Fast</li><li>Local</li></ul></div>"
        66 -> "/* ToolBox PK Theme */\n.toolbox-card {\n  background-color: #111713;\n  border: 1px solid #26332B;\n  border-radius: 8px;\n}"
        67 -> "// Calculate factorial\nfunction factorial(n) {\n  if (n <= 1) return 1;\n  return n * factorial(n - 1);\n}"
        68 -> "ToolBox PK Secret Key 2026"
        69 -> "VG9vbEJveCBQS18xNTVfRnJlZV9Ub29scw=="
        70 -> "https://toolboxpk.com/search?query=developer tools&filter=offline#results"
        71 -> "https%3A%2F%2Ftoolboxpk.com%2Fsearch%3Fquery%3Ddeveloper%2Btools"
        72 -> "<script>alert('ToolBox & Security');</script>"
        73 -> "&lt;div class=&quot;toolbox&quot;&gt;Safe &amp; Sound&lt;/div&gt;"
        76 -> "ToolBoxPK_Secret_2026"
        77 -> "Contact us at support@toolboxpk.com or sales@toolbox.dev for inquiries."
        78 -> "#00C878"
        79 -> "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlRvb2xCb3ggUEsiLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTgwMDAwMDAwMCwicm9sZSI6ImFkbWluIn0.c2lnbmF0dXJl"
        80 -> "${System.currentTimeMillis() / 1000}"
        else -> "ToolBox PK Developer Suite"
      }
    )
  }

  // State for Password Generator
  var passLength by remember { mutableFloatStateOf(16f) }
  var includeUpper by remember { mutableStateOf(true) }
  var includeLower by remember { mutableStateOf(true) }
  var includeDigits by remember { mutableStateOf(true) }
  var includeSymbols by remember { mutableStateOf(true) }
  var generatedPassword by remember { mutableStateOf("") }

  // State for UUID generator
  var uuidCount by remember { mutableIntStateOf(5) }
  var uuidUppercase by remember { mutableStateOf(false) }

  // State for Regex
  var regexPattern by remember { mutableStateOf("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}") }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    when (tool.id) {
      74 -> {
        // UUID Generator
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          ToolBoxTextField(
            value = uuidCount.toString(),
            onValueChange = { uuidCount = it.toIntOrNull()?.coerceIn(1, 50) ?: 1 },
            label = "Count (1 - 50)",
            modifier = Modifier.weight(1f),
            singleLine = true
          )

          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 8.dp)
          ) {
            Checkbox(
              checked = uuidUppercase,
              onCheckedChange = { uuidUppercase = it },
              colors = CheckboxDefaults.colors(checkedColor = PrimaryAccent)
            )
            Text("Uppercase", style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
          }
        }

        var uuids by remember(uuidCount, uuidUppercase) {
          mutableStateOf(generateUuids(uuidCount, uuidUppercase))
        }

        ToolActionButton(
          text = "Regenerate UUIDs",
          onClick = { uuids = generateUuids(uuidCount, uuidUppercase) },
          modifier = Modifier.fillMaxWidth(),
          icon = Icons.Filled.Autorenew,
          testTag = "regenerate_uuid_button"
        )

        ToolBoxResultCard(
          title = "Generated RFC-4122 v4 UUIDs",
          resultText = uuids,
          isCode = true
        )
      }

      75 -> {
        // Password Generator
        LaunchedEffect(Unit) {
          generatedPassword = generatePassword(passLength.toInt(), includeUpper, includeLower, includeDigits, includeSymbols)
        }

        Column(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SecondaryBackground)
            .border(1.dp, BorderGreen, RoundedCornerShape(12.dp))
            .padding(16.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text("Password Length: ${passLength.toInt()}", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            val entropy = calculateEntropy(passLength.toInt(), includeUpper, includeLower, includeDigits, includeSymbols)
            val strength = when {
              entropy > 80 -> "Very Strong"
              entropy > 60 -> "Strong"
              entropy > 40 -> "Moderate"
              else -> "Weak"
            }
            Text(
              text = "$strength (${entropy.toInt()} bits)",
              style = MaterialTheme.typography.labelSmall,
              color = if (entropy > 60) PrimaryAccent else StatusWarning,
              fontWeight = FontWeight.Bold
            )
          }

          Slider(
            value = passLength,
            onValueChange = { passLength = it },
            valueRange = 8f..64f,
            steps = 55,
            colors = SliderDefaults.colors(
              thumbColor = PrimaryAccent,
              activeTrackColor = PrimaryAccent,
              inactiveTrackColor = BorderGreen
            )
          )

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Checkbox(
                checked = includeUpper,
                onCheckedChange = { includeUpper = it },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryAccent)
              )
              Text("A-Z", color = TextPrimary)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              Checkbox(
                checked = includeLower,
                onCheckedChange = { includeLower = it },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryAccent)
              )
              Text("a-z", color = TextPrimary)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              Checkbox(
                checked = includeDigits,
                onCheckedChange = { includeDigits = it },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryAccent)
              )
              Text("0-9", color = TextPrimary)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
              Checkbox(
                checked = includeSymbols,
                onCheckedChange = { includeSymbols = it },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryAccent)
              )
              Text("!@#$", color = TextPrimary)
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          ToolActionButton(
            text = "Generate New Password",
            onClick = {
              generatedPassword = generatePassword(passLength.toInt(), includeUpper, includeLower, includeDigits, includeSymbols)
            },
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Filled.Autorenew,
            testTag = "generate_password_button"
          )
        }

        ToolBoxResultCard(
          title = "Generated Secure Password",
          resultText = generatedPassword,
          isCode = true
        )
      }

      76 -> {
        // Hash Generator
        ToolBoxTextField(
          value = inputCode,
          onValueChange = { inputCode = it },
          label = "Input Text to Hash",
          minLines = 3
        )

        val hashes = remember(inputCode) {
          val md5 = computeHash(inputCode, "MD5")
          val sha1 = computeHash(inputCode, "SHA-1")
          val sha256 = computeHash(inputCode, "SHA-256")
          val sha512 = computeHash(inputCode, "SHA-512")
          "MD5:\n$md5\n\nSHA-1:\n$sha1\n\nSHA-256:\n$sha256\n\nSHA-512:\n$sha512"
        }

        ToolBoxResultCard(
          title = "Cryptographic Hashes",
          resultText = hashes,
          isCode = true
        )
      }

      77 -> {
        // Regex Tester
        ToolBoxTextField(
          value = regexPattern,
          onValueChange = { regexPattern = it },
          label = "Regular Expression Pattern",
          singleLine = true
        )

        ToolBoxTextField(
          value = inputCode,
          onValueChange = { inputCode = it },
          label = "Test String",
          minLines = 4
        )

        val regexOutput = remember(regexPattern, inputCode) {
          try {
            val pattern = Regex(regexPattern)
            val matches = pattern.findAll(inputCode).toList()
            if (matches.isEmpty()) {
              "No matches found."
            } else {
              val sb = StringBuilder()
              sb.append("Total Matches Found: ${matches.size}\n\n")
              matches.forEachIndexed { index, matchResult ->
                sb.append("Match #${index + 1}: \"${matchResult.value}\" [range: ${matchResult.range}]\n")
                if (matchResult.groups.size > 1) {
                  matchResult.groups.drop(1).forEachIndexed { gIdx, group ->
                    if (group != null) {
                      sb.append("   Group ${gIdx + 1}: \"${group.value}\"\n")
                    }
                  }
                }
              }
              sb.toString().trimEnd()
            }
          } catch (e: Exception) {
            "Regex Syntax Error: ${e.message}"
          }
        }

        ToolBoxResultCard(
          title = "Regex Match Results",
          resultText = regexOutput,
          isCode = true
        )
      }

      78 -> {
        // Color Code Converter
        ToolBoxTextField(
          value = inputCode,
          onValueChange = { inputCode = it },
          label = "Color (HEX like #00C878 or rgb(0, 200, 120))",
          singleLine = true
        )

        val colorConversion = remember(inputCode) {
          convertColor(inputCode)
        }

        ToolBoxResultCard(
          title = "Converted Color Formats",
          resultText = colorConversion.displayText,
          isCode = true
        )
      }

      79 -> {
        // JWT Decoder
        Surface(
          color = Color(0x1F38BDF8),
          shape = RoundedCornerShape(8.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x4038BDF8))
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Filled.Security, contentDescription = null, tint = StatusInfo, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Notice: JWT decoding reads claims on-device. It does not verify the cryptographic signature.",
              style = MaterialTheme.typography.bodySmall,
              color = TextPrimary
            )
          }
        }

        ToolBoxTextField(
          value = inputCode,
          onValueChange = { inputCode = it },
          label = "Encoded JWT Token (Header.Payload.Signature)",
          minLines = 4
        )

        val decodedJwt = remember(inputCode) {
          decodeJwt(inputCode)
        }

        ToolBoxResultCard(
          title = "Decoded JWT Claims",
          resultText = decodedJwt,
          isCode = true
        )
      }

      80 -> {
        // Timestamp Converter
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          ToolBoxTextField(
            value = inputCode,
            onValueChange = { inputCode = it },
            label = "Unix Epoch (seconds or millis)",
            modifier = Modifier.weight(1f),
            singleLine = true
          )

          ToolActionButton(
            text = "Now",
            onClick = { inputCode = (System.currentTimeMillis() / 1000).toString() },
            isSecondary = true,
            modifier = Modifier.padding(top = 8.dp),
            testTag = "now_timestamp_button"
          )
        }

        val convertedTimestamp = remember(inputCode) {
          convertTimestamp(inputCode)
        }

        ToolBoxResultCard(
          title = "Formatted Date & Time",
          resultText = convertedTimestamp,
          isCode = true
        )
      }

      else -> {
        // Code Formatters & Encoders (61-73)
        ToolBoxTextField(
          value = inputCode,
          onValueChange = { inputCode = it },
          label = "Input Code / Text",
          minLines = 6
        )

        val devOutput = remember(tool.id, inputCode) {
          processDeveloperTool(tool.id, inputCode)
        }

        ToolBoxResultCard(
          title = "${tool.title} Output",
          resultText = devOutput,
          isCode = true
        )
      }
    }
  }
}

private fun processDeveloperTool(toolId: Int, input: String): String {
  return when (toolId) {
    61 -> {
      // JSON Formatter
      try {
        val trimmed = input.trim()
        if (trimmed.startsWith("{")) {
          JSONObject(trimmed).toString(2)
        } else if (trimmed.startsWith("[")) {
          JSONArray(trimmed).toString(2)
        } else {
          "Invalid JSON: must start with { or ["
        }
      } catch (e: Exception) {
        "JSON Syntax Error: ${e.message}"
      }
    }
    62 -> {
      // JSON Validator
      try {
        val trimmed = input.trim()
        if (trimmed.startsWith("{")) {
          JSONObject(trimmed)
          "VALID JSON!\nStructure: Object\nKey count: ${JSONObject(trimmed).length()}"
        } else if (trimmed.startsWith("[")) {
          JSONArray(trimmed)
          "VALID JSON!\nStructure: Array\nElement count: ${JSONArray(trimmed).length()}"
        } else {
          "INVALID: Expected root JSON Object or Array"
        }
      } catch (e: Exception) {
        "INVALID JSON:\n${e.message}"
      }
    }
    63 -> {
      // JSON Minifier
      try {
        val trimmed = input.trim()
        if (trimmed.startsWith("{")) {
          JSONObject(trimmed).toString()
        } else if (trimmed.startsWith("[")) {
          JSONArray(trimmed).toString()
        } else {
          input.replace("\\s+".toRegex(), "")
        }
      } catch (e: Exception) {
        input.replace("\\s+".toRegex(), "")
      }
    }
    64 -> {
      // XML Formatter
      formatXml(input)
    }
    65 -> {
      // HTML Formatter
      formatHtml(input)
    }
    66 -> {
      // CSS Minifier
      input
        .replace("/\\*[\\s\\S]*?\\*/".toRegex(), "") // strip comments
        .replace("\\s+".toRegex(), " ")
        .replace("\\s*([{}:;,])\\s*".toRegex(), "$1")
        .trim()
    }
    67 -> {
      // JS Minifier
      input
        .replace("//.*".toRegex(), "") // line comments
        .replace("/\\*[\\s\\S]*?\\*/".toRegex(), "") // block comments
        .lines().map { it.trim() }.filter { it.isNotEmpty() }
        .joinToString(" ")
        .replace("\\s*([=+\\-*/{}();,<>])\\s*".toRegex(), "$1")
    }
    68 -> {
      // Base64 Encode
      Base64.encodeToString(input.toByteArray(StandardCharsets.UTF_8), Base64.NO_WRAP)
    }
    69 -> {
      // Base64 Decode
      try {
        val decoded = Base64.decode(input.trim(), Base64.DEFAULT)
        String(decoded, StandardCharsets.UTF_8)
      } catch (e: Exception) {
        "Base64 Decode Error: Invalid base64 sequence"
      }
    }
    70 -> {
      // URL Encode
      try {
        URLEncoder.encode(input, StandardCharsets.UTF_8.toString())
      } catch (e: Exception) {
        input
      }
    }
    71 -> {
      // URL Decode
      try {
        URLDecoder.decode(input, StandardCharsets.UTF_8.toString())
      } catch (e: Exception) {
        "URL Decode Error: ${e.message}"
      }
    }
    72 -> {
      // HTML Entities Encode
      input
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#39;")
    }
    73 -> {
      // HTML Entities Decode
      input
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace("&amp;", "&")
    }
    else -> input
  }
}

private fun formatXml(xml: String): String {
  return try {
    var indent = 0
    val sb = StringBuilder()
    val tokens = xml.replace("><", ">\n<").lines()
    for (token in tokens) {
      val t = token.trim()
      if (t.isEmpty()) continue
      if (t.startsWith("</")) indent = Math.max(0, indent - 1)
      sb.append("  ".repeat(indent)).append(t).append("\n")
      if (t.startsWith("<") && !t.startsWith("</") && !t.endsWith("/>") && !t.startsWith("<?")) {
        indent++
      }
    }
    sb.toString().trimEnd()
  } catch (e: Exception) {
    xml
  }
}

private fun formatHtml(html: String): String {
  return formatXml(html)
}

private fun generateUuids(count: Int, uppercase: Boolean): String {
  val sb = StringBuilder()
  repeat(count) {
    val u = UUID.randomUUID().toString()
    sb.append(if (uppercase) u.uppercase() else u).append("\n")
  }
  return sb.toString().trimEnd()
}

private fun generatePassword(length: Int, upper: Boolean, lower: Boolean, digits: Boolean, symbols: Boolean): String {
  val charPool = StringBuilder()
  if (upper) charPool.append("ABCDEFGHJKLMNPQRSTUVWXYZ")
  if (lower) charPool.append("abcdefghijkmnopqrstuvwxyz")
  if (digits) charPool.append("23456789")
  if (symbols) charPool.append("!@#$%&*?_~-+")
  if (charPool.isEmpty()) charPool.append("abcdefghijklmnopqrstuvwxyz")

  val random = SecureRandom()
  val result = StringBuilder()
  repeat(length) {
    val idx = random.nextInt(charPool.length)
    result.append(charPool[idx])
  }
  return result.toString()
}

private fun calculateEntropy(length: Int, upper: Boolean, lower: Boolean, digits: Boolean, symbols: Boolean): Double {
  var poolSize = 0
  if (upper) poolSize += 24
  if (lower) poolSize += 24
  if (digits) poolSize += 8
  if (symbols) poolSize += 11
  if (poolSize == 0) poolSize = 24
  return length * (Math.log(poolSize.toDouble()) / Math.log(2.0))
}

private fun computeHash(input: String, algorithm: String): String {
  return try {
    val digest = MessageDigest.getInstance(algorithm)
    val bytes = digest.digest(input.toByteArray(StandardCharsets.UTF_8))
    bytes.joinToString("") { "%02x".format(it) }
  } catch (e: Exception) {
    "Algorithm error"
  }
}

private data class ColorConversion(val displayText: String)

private fun convertColor(input: String): ColorConversion {
  val clean = input.trim().replace("#", "")
  val hexVal = clean.toIntOrNull(16)
  if (hexVal != null && (clean.length == 6 || clean.length == 8)) {
    val r = (hexVal shr 16) and 0xFF
    val g = (hexVal shr 8) and 0xFF
    val b = hexVal and 0xFF

    // Convert to HSL
    val rf = r / 255f
    val gf = g / 255f
    val bf = b / 255f
    val max = maxOf(rf, gf, bf)
    val min = minOf(rf, gf, bf)
    val l = (max + min) / 2f
    val d = max - min
    val s = if (d == 0f) 0f else if (l > 0.5f) d / (2f - max - min) else d / (max + min)
    var h = when {
      d == 0f -> 0f
      max == rf -> ((gf - bf) / d) % 6f
      max == gf -> ((bf - rf) / d) + 2f
      else -> ((rf - gf) / d) + 4f
    } * 60f
    if (h < 0f) h += 360f

    // Convert to CMYK
    val k = 1f - max
    val c = if (k == 1f) 0f else (1f - rf - k) / (1f - k)
    val m = if (k == 1f) 0f else (1f - gf - k) / (1f - k)
    val y = if (k == 1f) 0f else (1f - bf - k) / (1f - k)

    val res = "HEX: #${clean.uppercase()}\n" +
        "RGB: rgb($r, $g, $b)\n" +
        "RGBA: rgba($r, $g, $b, 1.0)\n" +
        "HSL: hsl(${h.toInt()}°, ${(s * 100).toInt()}%, ${(l * 100).toInt()}%)\n" +
        "CMYK: cmyk(${(c * 100).toInt()}%, ${(m * 100).toInt()}%, ${(y * 100).toInt()}%, ${(k * 100).toInt()}%)"
    return ColorConversion(res)
  }
  return ColorConversion("Enter a valid 6-digit HEX color code (e.g. #00C878)")
}

private fun decodeJwt(token: String): String {
  val parts = token.trim().split(".")
  if (parts.size < 2) return "Invalid JWT format. Must contain at least Header and Payload separated by dot."
  return try {
    val headerJson = String(Base64.decode(parts[0], Base64.URL_SAFE or Base64.NO_PADDING), StandardCharsets.UTF_8)
    val payloadJson = String(Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING), StandardCharsets.UTF_8)

    val prettyHeader = JSONObject(headerJson).toString(2)
    val payloadObj = JSONObject(payloadJson)
    val prettyPayload = payloadObj.toString(2)

    val expInfo = if (payloadObj.has("exp")) {
      val expSec = payloadObj.getLong("exp")
      val expDate = Date(expSec * 1000)
      val isExpired = expDate.before(Date())
      "\n\nExpiration Check: ${if (isExpired) "EXPIRED" else "ACTIVE"}\nExpires: $expDate"
    } else ""

    "HEADER:\n$prettyHeader\n\nPAYLOAD CLAIMS:\n$prettyPayload$expInfo\n\nSIGNATURE:\n${if (parts.size > 2) parts[2] else "None"}"
  } catch (e: Exception) {
    "Error decoding JWT: ${e.message}"
  }
}

private fun convertTimestamp(epochStr: String): String {
  val num = epochStr.trim().toLongOrNull() ?: return "Enter a valid numeric epoch timestamp"
  val millis = if (num < 100000000000L) num * 1000 else num
  val date = Date(millis)

  val utcFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'", Locale.US).apply {
    timeZone = TimeZone.getTimeZone("UTC")
  }
  val localFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss (z)", Locale.getDefault())
  val relative = getRelativeTimeString(millis)

  return "Local Time: ${localFormat.format(date)}\n" +
      "UTC Time:   ${utcFormat.format(date)}\n" +
      "Relative:   $relative\n" +
      "Unix Seconds: ${millis / 1000}\n" +
      "Unix Millis:  $millis"
}

private fun getRelativeTimeString(timeMillis: Long): String {
  val diff = System.currentTimeMillis() - timeMillis
  val absDiff = Math.abs(diff)
  val suffix = if (diff >= 0) "ago" else "from now"

  val seconds = absDiff / 1000
  val minutes = seconds / 60
  val hours = minutes / 60
  val days = hours / 24

  return when {
    seconds < 60 -> "$seconds seconds $suffix"
    minutes < 60 -> "$minutes minutes $suffix"
    hours < 24 -> "$hours hours $suffix"
    else -> "$days days $suffix"
  }
}
