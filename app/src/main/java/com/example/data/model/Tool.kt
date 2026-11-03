package com.example.data.model

enum class ToolCategory(
  val id: String,
  val displayName: String,
  val description: String
) {
  IMAGE("image", "Image Tools", "Compress, resize, convert, crop and edit images locally"),
  PDF("pdf", "PDF Tools", "Merge, split, compress, convert, watermark & view PDFs securely"),
  TEXT("text", "Text Tools", "Count words, format case, diff check, clean & reverse text"),
  DEVELOPER("developer", "Developer Tools", "Format JSON/XML, minify code, encode Base64, generate hashes"),
  CALCULATOR("calculator", "Calculators", "Financial, math, EMI, percentage, health & unit calculators"),
  STUDENT("student", "Student Tools", "Study timer, citations, flashcards, GPA & attendance calculators"),
  SEO("seo", "SEO & Marketing", "Meta tag checkers, keyword density, slug & robots generators"),
  QR_DESIGN("qr_design", "QR & Design", "QR code generators, color pickers, CSS gradients & shadows"),
  FILE("file", "File & Data", "CSV converters, table viewers, markdown previewers, ZIP utilities")
}

data class Tool(
  val id: Int,
  val title: String,
  val category: ToolCategory,
  val description: String,
  val keywords: List<String>,
  val badge: String? = null,
  val isPopular: Boolean = false
) {
  val route: String get() = "tool/$id"
}
