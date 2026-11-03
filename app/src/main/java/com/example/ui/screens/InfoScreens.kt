package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ToolActionButton
import com.example.ui.components.ToolBoxTextField
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
  pageType: String,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }

  val pageTitle = when (pageType) {
    "about" -> "About Us"
    "privacy" -> "Privacy Policy"
    "terms" -> "Terms & Conditions"
    "disclaimer" -> "Legal Disclaimer"
    "faq" -> "Frequently Asked Questions"
    "contact" -> "Contact Support"
    else -> "Information"
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(pageTitle, color = TextPrimary, fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(onClick = onBack, modifier = Modifier.testTag("info_back_button")) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = CardBackground)
      )
    },
    containerColor = BackgroundDark,
    modifier = modifier
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      when (pageType) {
        "about" -> {
          item {
            InfoCard(
              title = "About ToolBox PK",
              content = "ToolBox PK is built with a singular mission: providing 155+ free, high-performance, everyday digital utilities without requiring account registration, invasive subscriptions, or external cloud dependencies.\n\nWhether you are a developer formatting JSON or generating hashes, a student calculating attendance or generating APA citations, an engineer calculating loan EMI, or a content creator compressing media, ToolBox PK is designed to work completely on your device with maximum speed and zero friction."
            )
          }
          item {
            InfoCard(
              title = "Core Principles",
              content = "• Privacy By Default: Files and sensitive credentials never leave your hardware.\n• Zero Advertising Clutter: Clean, distraction-free emerald dark aesthetic.\n• Native Android Performance: Engineered using Jetpack Compose, Kotlin coroutines, and reactive state."
            )
          }
        }

        "privacy" -> {
          item {
            InfoCard(
              title = "100% On-Device Data Policy",
              content = "ToolBox PK processes files, texts, passwords, and numbers exclusively inside your device memory.\n\n1. No Server File Uploads: Any image compressed, PDF edited, or text formatted remains strictly on your device.\n2. No Compulsory Accounts: We do not ask for your email address, phone number, or password.\n3. Zero Analytics Tracking: We do not package third-party ad-tracking SDKs or trackers."
            )
          }
          item {
            InfoCard(
              title = "Local Preferences Storage",
              content = "Tool bookmarks (Favorites) and recently used tool history are saved directly to your device local SharedPreferences. You can wipe this local history at any time using the Clear History button."
            )
          }
        }

        "terms" -> {
          item {
            InfoCard(
              title = "Terms & Conditions",
              content = "By downloading and utilizing ToolBox PK, you agree to these operational terms:\n\n1. Permitted Use: All 155 tools are provided free of charge for personal, academic, and commercial utility.\n2. Security Responsibility: You must only process PDF documents and cryptographic keys that you are explicitly authorized to access and inspect.\n3. As-Is Provision: Utilities are provided without warranty of fitness for a particular purpose."
            )
          }
        }

        "disclaimer" -> {
          item {
            InfoCard(
              title = "Informational Disclaimer",
              content = "Important notices regarding specific calculations:\n\n• Health Tools (BMI): Body Mass Index calculators are meant solely for general fitness reference and cannot replace clinical medical consultations.\n• Financial Tools (EMI, GST, Tax): Tax and interest calculations are estimates based on standard compounding and user-supplied rates. Consult licensed accountants for statutory tax filings.\n• Security Tools (JWT Decoder): Token decoding is parsing of payload claims and does not perform cryptographic digital signature verification."
            )
          }
        }

        "faq" -> {
          item {
            FaqItem(
              q = "Does ToolBox PK work completely offline?",
              a = "Yes! All calculations, text conversions, code formatting, hashing, and color tools run 100% offline without needing an active internet connection."
            )
          }
          item {
            FaqItem(
              q = "Are my uploaded images or PDFs saved anywhere?",
              a = "No. Files are processed in memory and discarded when you close or reset the tool. Nothing is uploaded to any remote server."
            )
          }
          item {
            FaqItem(
              q = "Is ToolBox PK really 100% free?",
              a = "Yes. All 155 tools are completely free to use with no hidden subscription fees."
            )
          }
          item {
            FaqItem(
              q = "Can I request new tools to be added?",
              a = "Yes! Use the Contact Support section to suggest new features for future versions."
            )
          }
        }

        "contact" -> {
          item {
            ContactFormComponent()
          }
        }
      }
    }
  }
}

@Composable
private fun InfoCard(title: String, content: String) {
  Card(
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    shape = RoundedCornerShape(12.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Text(title, style = MaterialTheme.typography.titleLarge, color = PrimaryAccent, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(10.dp))
      Text(content, style = MaterialTheme.typography.bodyMedium, color = TextPrimary, lineHeight = 22.sp)
    }
  }
}

@Composable
private fun FaqItem(q: String, a: String) {
  Card(
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    shape = RoundedCornerShape(12.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Filled.HelpOutline, contentDescription = null, tint = PrimaryAccent, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(q, style = MaterialTheme.typography.titleMedium, color = TextPrimary, fontWeight = FontWeight.SemiBold)
      }
      Spacer(modifier = Modifier.height(8.dp))
      Text(a, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
    }
  }
}

@Composable
private fun ContactFormComponent() {
  var name by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var message by remember { mutableStateOf("") }
  var isSubmitted by remember { mutableStateOf(false) }

  Card(
    colors = CardDefaults.cardColors(containerColor = CardBackground),
    border = androidx.compose.foundation.BorderStroke(1.dp, BorderGreen),
    shape = RoundedCornerShape(12.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Text("Send Feedback or Tool Request", style = MaterialTheme.typography.titleLarge, color = PrimaryAccent, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(6.dp))
      Text("Have an idea for a new tool or found a bug? Let our engineering team know.", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
      Spacer(modifier = Modifier.height(14.dp))

      if (isSubmitted) {
        Surface(
          color = Color(0x2600C878),
          shape = RoundedCornerShape(8.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryAccent),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = PrimaryAccent)
            Spacer(modifier = Modifier.width(10.dp))
            Text("Thank you! Your feedback has been recorded locally.", color = TextPrimary, style = MaterialTheme.typography.bodyMedium)
          }
        }
      } else {
        ToolBoxTextField(value = name, onValueChange = { name = it }, label = "Your Name", singleLine = true)
        Spacer(modifier = Modifier.height(10.dp))
        ToolBoxTextField(value = email, onValueChange = { email = it }, label = "Email Address", singleLine = true)
        Spacer(modifier = Modifier.height(10.dp))
        ToolBoxTextField(value = message, onValueChange = { message = it }, label = "Message / Feature Request", minLines = 4)
        Spacer(modifier = Modifier.height(14.dp))

        ToolActionButton(
          text = "Submit Message",
          onClick = {
            if (message.isNotBlank()) {
              isSubmitted = true
            }
          },
          modifier = Modifier.fillMaxWidth(),
          icon = Icons.Filled.Send,
          testTag = "submit_contact_button"
        )
      }
    }
  }
}
