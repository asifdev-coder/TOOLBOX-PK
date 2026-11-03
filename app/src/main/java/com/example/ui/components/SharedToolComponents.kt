package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Tool
import com.example.data.model.ToolCategory
import com.example.ui.theme.*

@Composable
fun ToolBoxHeader(
  title: String,
  category: ToolCategory,
  description: String,
  toolId: Int,
  isFavorite: Boolean,
  onToggleFavorite: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = 16.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Surface(
        color = SecondaryBackground,
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen)
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = getCategoryIcon(category),
            contentDescription = null,
            tint = PrimaryAccent,
            modifier = Modifier.size(14.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = category.displayName,
            style = MaterialTheme.typography.labelSmall,
            color = PrimaryAccent,
            fontWeight = FontWeight.Bold
          )
        }
      }

      IconButton(
        onClick = onToggleFavorite,
        modifier = Modifier
          .size(40.dp)
          .testTag("favorite_button_$toolId")
      ) {
        Icon(
          imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
          contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
          tint = if (isFavorite) Color(0xFFFF4D4D) else TextSecondary
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = title,
      style = MaterialTheme.typography.headlineLarge,
      color = TextPrimary
    )

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = description,
      style = MaterialTheme.typography.bodyMedium,
      color = TextSecondary
    )

    Spacer(modifier = Modifier.height(10.dp))

    // Privacy badge
    Surface(
      color = Color(0x1400C878),
      shape = RoundedCornerShape(6.dp),
      border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x3300C878))
    ) {
      Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Filled.Security,
          contentDescription = null,
          tint = PrimaryAccent,
          modifier = Modifier.size(13.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "100% Private — Processed locally on your device. Zero cloud uploads.",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = PrimaryAccent
        )
      }
    }
  }
}

@Composable
fun ToolBoxResultCard(
  title: String = "Result",
  resultText: String,
  modifier: Modifier = Modifier,
  isCode: Boolean = false,
  extraActions: @Composable RowScope.() -> Unit = {}
) {
  val context = LocalContext.current

  Card(
    modifier = modifier.fillMaxWidth(),
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    shape = RoundedCornerShape(12.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.titleMedium,
          color = TextPrimary,
          fontWeight = FontWeight.Bold
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
          extraActions()

          if (resultText.isNotBlank()) {
            IconButton(
              onClick = {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("ToolBox PK Result", resultText)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
              },
              modifier = Modifier.size(36.dp).testTag("copy_result_button")
            ) {
              Icon(
                imageVector = Icons.Outlined.ContentCopy,
                contentDescription = "Copy result",
                tint = PrimaryAccent,
                modifier = Modifier.size(18.dp)
              )
            }

            IconButton(
              onClick = {
                val sendIntent = Intent().apply {
                  action = Intent.ACTION_SEND
                  putExtra(Intent.EXTRA_TEXT, resultText)
                  type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, "Share result via"))
              },
              modifier = Modifier.size(36.dp).testTag("share_result_button")
            ) {
              Icon(
                imageVector = Icons.Outlined.Share,
                contentDescription = "Share result",
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      SelectionContainer {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SecondaryBackground)
            .border(1.dp, BorderGreen, RoundedCornerShape(8.dp))
            .padding(12.dp)
        ) {
          if (resultText.isBlank()) {
            Text(
              text = "Output will appear here once processed...",
              style = MaterialTheme.typography.bodyMedium,
              color = TextMuted
            )
          } else {
            Text(
              text = resultText,
              style = if (isCode) {
                MaterialTheme.typography.bodyMedium.copy(
                  fontFamily = JetBrainsMonoFamily,
                  fontSize = 12.5.sp,
                  lineHeight = 18.sp,
                  color = TextPrimary
                )
              } else {
                MaterialTheme.typography.bodyLarge.copy(color = TextPrimary)
              }
            )
          }
        }
      }
    }
  }
}

@Composable
fun MetricBadge(
  label: String,
  value: String,
  modifier: Modifier = Modifier,
  color: Color = PrimaryAccent
) {
  Surface(
    modifier = modifier,
    color = SecondaryBackground,
    shape = RoundedCornerShape(10.dp),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen)
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = value,
        style = MaterialTheme.typography.headlineMedium.copy(
          fontFamily = JetBrainsMonoFamily,
          fontWeight = FontWeight.Bold
        ),
        color = color
      )
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = TextSecondary
      )
    }
  }
}

@Composable
fun ToolBoxTextField(
  value: String,
  onValueChange: (String) -> Unit,
  label: String,
  modifier: Modifier = Modifier,
  placeholder: String = "",
  singleLine: Boolean = false,
  minLines: Int = 1,
  maxLines: Int = Int.MAX_VALUE,
  trailingIcon: @Composable (() -> Unit)? = null
) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    label = { Text(label, color = TextSecondary) },
    placeholder = { Text(placeholder, color = TextMuted) },
    modifier = modifier.fillMaxWidth(),
    singleLine = singleLine,
    minLines = minLines,
    maxLines = maxLines,
    colors = OutlinedTextFieldDefaults.colors(
      focusedBorderColor = PrimaryAccent,
      unfocusedBorderColor = BorderGreen,
      focusedTextColor = TextPrimary,
      unfocusedTextColor = TextPrimary,
      cursorColor = PrimaryAccent,
      focusedContainerColor = SecondaryBackground,
      unfocusedContainerColor = SecondaryBackground
    ),
    shape = RoundedCornerShape(10.dp),
    trailingIcon = trailingIcon
  )
}

@Composable
fun ToolActionButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  icon: ImageVector? = null,
  isSecondary: Boolean = false,
  testTag: String = "action_button"
) {
  Button(
    onClick = onClick,
    modifier = modifier
      .defaultMinSize(minHeight = 48.dp)
      .testTag(testTag),
    shape = RoundedCornerShape(10.dp),
    colors = if (isSecondary) {
      ButtonDefaults.buttonColors(
        containerColor = SecondaryBackground,
        contentColor = TextPrimary
      )
    } else {
      ButtonDefaults.buttonColors(
        containerColor = PrimaryAccent,
        contentColor = BackgroundDark
      )
    },
    border = if (isSecondary) androidx.compose.foundation.BorderStroke(1.dp, BorderGreen) else null
  ) {
    if (icon != null) {
      Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
      Spacer(modifier = Modifier.width(8.dp))
    }
    Text(
      text = text,
      style = MaterialTheme.typography.labelLarge,
      fontWeight = FontWeight.Bold
    )
  }
}

fun getCategoryIcon(category: ToolCategory): ImageVector {
  return when (category) {
    ToolCategory.IMAGE -> Icons.Filled.Image
    ToolCategory.PDF -> Icons.Filled.PictureAsPdf
    ToolCategory.TEXT -> Icons.Filled.TextFields
    ToolCategory.DEVELOPER -> Icons.Filled.Code
    ToolCategory.CALCULATOR -> Icons.Filled.Calculate
    ToolCategory.STUDENT -> Icons.Filled.School
    ToolCategory.SEO -> Icons.Filled.Campaign
    ToolCategory.QR_DESIGN -> Icons.Filled.QrCode2
    ToolCategory.FILE -> Icons.Filled.Folder
  }
}
