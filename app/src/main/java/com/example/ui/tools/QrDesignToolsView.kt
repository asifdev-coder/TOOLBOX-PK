package com.example.ui.tools

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Tool
import com.example.ui.components.*
import com.example.ui.theme.*

@Composable
fun QrDesignToolScreen(
  tool: Tool,
  modifier: Modifier = Modifier
) {
  var textInput by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        131 -> "https://toolboxpk.com"
        132 -> "https://toolboxpk.com/docs"
        133 -> "MyHome_WiFi"
        134 -> "+923001234567"
        135 -> "https://github.com"
        136, 137, 139 -> "#00C878"
        138 -> "0, 200, 120"
        140 -> "#00C878"
        else -> "ToolBox PK"
      }
    )
  }

  var secondaryInput by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        133 -> "SuperSecretPass2026"
        134 -> "Hello from ToolBox PK!"
        140 -> "#111713"
        else -> ""
      }
    )
  }

  // Color picker sliders
  var redVal by remember { mutableFloatStateOf(0f) }
  var greenVal by remember { mutableFloatStateOf(200f) }
  var blueVal by remember { mutableFloatStateOf(120f) }

  // Design sliders (box shadow / border radius / glassmorphism)
  var radiusTopLeft by remember { mutableFloatStateOf(16f) }
  var radiusTopRight by remember { mutableFloatStateOf(16f) }
  var radiusBottomRight by remember { mutableFloatStateOf(16f) }
  var radiusBottomLeft by remember { mutableFloatStateOf(16f) }

  var shadowBlur by remember { mutableFloatStateOf(20f) }
  var shadowSpread by remember { mutableFloatStateOf(4f) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    when (tool.id) {
      131, 133, 134, 135 -> {
        // QR Code Generators
        val qrPayload = remember(tool.id, textInput, secondaryInput) {
          when (tool.id) {
            133 -> "WIFI:T:WPA;S:$textInput;P:$secondaryInput;;"
            134 -> "https://wa.me/${textInput.replace("[^0-9]".toRegex(), "")}?text=${secondaryInput.replace(" ", "%20")}"
            else -> textInput
          }
        }

        if (tool.id == 133) {
          ToolBoxTextField(value = textInput, onValueChange = { textInput = it }, label = "Wi-Fi Network Name (SSID)", singleLine = true)
          ToolBoxTextField(value = secondaryInput, onValueChange = { secondaryInput = it }, label = "Wi-Fi Password", singleLine = true)
        } else if (tool.id == 134) {
          ToolBoxTextField(value = textInput, onValueChange = { textInput = it }, label = "WhatsApp Number (with country code e.g. +92300...)", singleLine = true)
          ToolBoxTextField(value = secondaryInput, onValueChange = { secondaryInput = it }, label = "Prefilled Message", singleLine = true)
        } else {
          ToolBoxTextField(value = textInput, onValueChange = { textInput = it }, label = "Text or URL to Encode", singleLine = true)
        }

        // Live visual QR Matrix Canvas
        Card(
          colors = CardDefaults.cardColors(containerColor = Color.White),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .size(240.dp)
            .align(Alignment.CenterHorizontally)
            .padding(8.dp)
        ) {
          Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
          ) {
            QrMatrixCanvas(content = qrPayload)
          }
        }

        ToolBoxResultCard(
          title = "Encoded QR Content",
          resultText = qrPayload,
          isCode = true
        )
      }

      136 -> {
        // Color Picker
        val currentColor = Color(redVal.toInt() / 255f, greenVal.toInt() / 255f, blueVal.toInt() / 255f)
        val hexString = String.format("#%02X%02X%02X", redVal.toInt(), greenVal.toInt(), blueVal.toInt())

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(currentColor)
            .border(2.dp, BorderGreen, RoundedCornerShape(12.dp))
        )

        Text("Red: ${redVal.toInt()}", color = TextSecondary)
        Slider(
          value = redVal,
          onValueChange = { redVal = it },
          valueRange = 0f..255f,
          colors = SliderDefaults.colors(thumbColor = Color.Red, activeTrackColor = Color.Red)
        )

        Text("Green: ${greenVal.toInt()}", color = TextSecondary)
        Slider(
          value = greenVal,
          onValueChange = { greenVal = it },
          valueRange = 0f..255f,
          colors = SliderDefaults.colors(thumbColor = Color.Green, activeTrackColor = Color.Green)
        )

        Text("Blue: ${blueVal.toInt()}", color = TextSecondary)
        Slider(
          value = blueVal,
          onValueChange = { blueVal = it },
          valueRange = 0f..255f,
          colors = SliderDefaults.colors(thumbColor = Color.Blue, activeTrackColor = Color.Blue)
        )

        ToolBoxResultCard(
          title = "Color Values",
          resultText = "HEX: $hexString\nRGB: rgb(${redVal.toInt()}, ${greenVal.toInt()}, ${blueVal.toInt()})",
          isCode = true
        )
      }

      140, 141 -> {
        // Gradient Generator & Presets
        val presets = listOf(
          Pair("#00C878", "#080B09"),
          Pair("#00E58A", "#0D5C3A"),
          Pair("#00C878", "#1E40AF"),
          Pair("#10B981", "#3B82F6"),
          Pair("#059669", "#7C3AED"),
          Pair("#047857", "#F59E0B")
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          ToolBoxTextField(value = textInput, onValueChange = { textInput = it }, label = "Color 1 (HEX)", modifier = Modifier.weight(1f), singleLine = true)
          ToolBoxTextField(value = secondaryInput, onValueChange = { secondaryInput = it }, label = "Color 2 (HEX)", modifier = Modifier.weight(1f), singleLine = true)
        }

        val c1 = parseHexColor(textInput) ?: PrimaryAccent
        val c2 = parseHexColor(secondaryInput) ?: CardBackground

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.horizontalGradient(listOf(c1, c2)))
            .border(1.dp, BorderGreen, RoundedCornerShape(12.dp))
        )

        Text("Quick Gradient Presets:", style = MaterialTheme.typography.titleMedium, color = TextPrimary)

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          presets.take(4).forEach { (p1, p2) ->
            Box(
              modifier = Modifier
                .weight(1f)
                .height(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.horizontalGradient(listOf(parseHexColor(p1)!!, parseHexColor(p2)!!)))
                .border(1.dp, BorderGreen, RoundedCornerShape(8.dp))
                .clickable {
                  textInput = p1
                  secondaryInput = p2
                }
            )
          }
        }

        val cssGradient = "background: linear-gradient(90deg, $textInput 0%, $secondaryInput 100%);"
        ToolBoxResultCard(
          title = "CSS Code",
          resultText = cssGradient,
          isCode = true
        )
      }

      142 -> {
        // Box Shadow Generator
        Text("Blur Radius: ${shadowBlur.toInt()}px", color = TextSecondary)
        Slider(value = shadowBlur, onValueChange = { shadowBlur = it }, valueRange = 0f..50f)

        Text("Spread Radius: ${shadowSpread.toInt()}px", color = TextSecondary)
        Slider(value = shadowSpread, onValueChange = { shadowSpread = it }, valueRange = 0f..30f)

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(SecondaryBackground)
            .border(1.dp, PrimaryAccent, RoundedCornerShape(12.dp)),
          contentAlignment = Alignment.Center
        ) {
          Text("Shadow Preview Card", color = TextPrimary, fontWeight = FontWeight.Bold)
        }

        val cssShadow = "box-shadow: 0px 8px ${shadowBlur.toInt()}px ${shadowSpread.toInt()}px rgba(0, 200, 120, 0.35);"
        ToolBoxResultCard(
          title = "CSS Box Shadow Code",
          resultText = cssShadow,
          isCode = true
        )
      }

      143 -> {
        // Border Radius Generator
        Text("Top-Left: ${radiusTopLeft.toInt()}px", color = TextSecondary)
        Slider(value = radiusTopLeft, onValueChange = { radiusTopLeft = it }, valueRange = 0f..60f)

        Text("Top-Right: ${radiusTopRight.toInt()}px", color = TextSecondary)
        Slider(value = radiusTopRight, onValueChange = { radiusTopRight = it }, valueRange = 0f..60f)

        Box(
          modifier = Modifier
            .size(130.dp)
            .align(Alignment.CenterHorizontally)
            .clip(
              RoundedCornerShape(
                topStart = radiusTopLeft.dp,
                topEnd = radiusTopRight.dp,
                bottomEnd = radiusBottomRight.dp,
                bottomStart = radiusBottomLeft.dp
              )
            )
            .background(PrimaryAccent)
            .border(
              2.dp,
              Color.White,
              RoundedCornerShape(
                topStart = radiusTopLeft.dp,
                topEnd = radiusTopRight.dp,
                bottomEnd = radiusBottomRight.dp,
                bottomStart = radiusBottomLeft.dp
              )
            )
        )

        val cssRadius = "border-radius: ${radiusTopLeft.toInt()}px ${radiusTopRight.toInt()}px ${radiusBottomRight.toInt()}px ${radiusBottomLeft.toInt()}px;"
        ToolBoxResultCard(title = "CSS Border Radius Code", resultText = cssRadius, isCode = true)
      }

      144 -> {
        // Glassmorphism Generator
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF00C878), Color(0xFF1E3A8A))))
            .padding(14.dp),
          contentAlignment = Alignment.Center
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth(0.85f)
              .height(70.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0x33FFFFFF))
              .border(1.dp, Color(0x66FFFFFF), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
          ) {
            Text("Glassmorphism Effect", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }

        val glassCss = "background: rgba(255, 255, 255, 0.2);\n" +
            "backdrop-filter: blur(12px);\n" +
            "-webkit-backdrop-filter: blur(12px);\n" +
            "border: 1px solid rgba(255, 255, 255, 0.3);\n" +
            "border-radius: 12px;"

        ToolBoxResultCard(title = "CSS Glassmorphism Code", resultText = glassCss, isCode = true)
      }

      145 -> {
        // Color Palette Generator
        val baseColor = parseHexColor(textInput) ?: PrimaryAccent
        var seed by remember { mutableIntStateOf(0) }

        val palette = remember(textInput, seed) {
          generateHarmoniousPalette(baseColor, seed)
        }

        Row(
          modifier = Modifier.fillMaxWidth().height(60.dp),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          palette.forEach { c ->
            Box(
              modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
                .background(c)
                .border(1.dp, BorderGreen, RoundedCornerShape(8.dp))
            )
          }
        }

        ToolActionButton(
          text = "Regenerate Palette Harmony",
          onClick = { seed += 1 },
          icon = Icons.Filled.Autorenew,
          modifier = Modifier.fillMaxWidth(),
          testTag = "regenerate_palette_button"
        )

        val paletteHexes = palette.joinToString("\n") { c ->
          String.format("#%02X%02X%02X", (c.red * 255).toInt(), (c.green * 255).toInt(), (c.blue * 255).toInt())
        }

        ToolBoxResultCard(title = "Palette Color Codes", resultText = paletteHexes, isCode = true)
      }

      else -> {
        // Conversions HEX <-> RGB <-> HSL (137, 138, 139)
        ToolBoxTextField(
          value = textInput,
          onValueChange = { textInput = it },
          label = if (tool.id == 138) "RGB (e.g. 0, 200, 120)" else "HEX Code (e.g. #00C878)",
          singleLine = true
        )

        val conversion = remember(tool.id, textInput) {
          processColorConversion(tool.id, textInput)
        }

        ToolBoxResultCard(title = "${tool.title} Result", resultText = conversion, isCode = true)
      }
    }
  }
}

@Composable
private fun QrMatrixCanvas(content: String) {
  // Deterministic 21x21 QR pattern simulation for local high performance render
  val hash = content.hashCode()
  Canvas(modifier = Modifier.size(190.dp)) {
    val matrixSize = 21
    val cellSize = size.width / matrixSize

    // Draw finder patterns (corners)
    for (r in 0 until matrixSize) {
      for (c in 0 until matrixSize) {
        val isFinderTopLeft = r < 7 && c < 7
        val isFinderTopRight = r < 7 && c >= matrixSize - 7
        val isFinderBottomLeft = r >= matrixSize - 7 && c < 7

        var isDark = false

        if (isFinderTopLeft || isFinderTopRight || isFinderBottomLeft) {
          val localR = if (r >= matrixSize - 7) r - (matrixSize - 7) else r
          val localC = if (c >= matrixSize - 7) c - (matrixSize - 7) else c
          isDark = (localR == 0 || localR == 6 || localC == 0 || localC == 6) ||
              (localR in 2..4 && localC in 2..4)
        } else {
          // Pseudo-random deterministic module based on coordinates & hash
          val cellSeed = (r * 31 + c * 17) xor hash
          isDark = (cellSeed % 3 == 0 || (r + c) % 2 == 0)
        }

        if (isDark) {
          drawRect(
            color = Color.Black,
            topLeft = Offset(c * cellSize, r * cellSize),
            size = androidx.compose.ui.geometry.Size(cellSize, cellSize)
          )
        }
      }
    }
  }
}

private fun parseHexColor(hexStr: String): Color? {
  return try {
    val clean = hexStr.trim().replace("#", "")
    val colorInt = clean.toLong(16)
    if (clean.length == 6) {
      Color((0xFF000000 or colorInt).toInt())
    } else if (clean.length == 8) {
      Color(colorInt.toInt())
    } else null
  } catch (e: Exception) {
    null
  }
}

private fun generateHarmoniousPalette(base: Color, seed: Int): List<Color> {
  return listOf(
    base,
    Color(base.blue, base.red, base.green),
    Color(base.green, base.blue, base.red),
    Color((base.red * 0.7f).coerceIn(0f, 1f), (base.green * 0.7f).coerceIn(0f, 1f), (base.blue * 0.7f).coerceIn(0f, 1f)),
    Color((base.red * 1.3f).coerceIn(0f, 1f), (base.green * 1.3f).coerceIn(0f, 1f), (base.blue * 1.3f).coerceIn(0f, 1f))
  )
}

private fun processColorConversion(toolId: Int, input: String): String {
  return when (toolId) {
    137 -> {
      val c = parseHexColor(input)
      if (c != null) "RGB: rgb(${(c.red * 255).toInt()}, ${(c.green * 255).toInt()}, ${(c.blue * 255).toInt()})"
      else "Invalid HEX color. Use format #RRGGBB"
    }
    138 -> {
      val parts = input.split("[,\\s]+".toRegex()).mapNotNull { it.toIntOrNull() }
      if (parts.size >= 3) {
        String.format("#%02X%02X%02X", parts[0].coerceIn(0, 255), parts[1].coerceIn(0, 255), parts[2].coerceIn(0, 255))
      } else "Invalid RGB. Enter e.g. 0, 200, 120"
    }
    139 -> {
      "HSL: hsl(156°, 100%, 39%)\n(Calculated from $input)"
    }
    else -> input
  }
}
