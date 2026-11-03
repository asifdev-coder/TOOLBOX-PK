package com.example.data

import com.example.data.model.Tool
import com.example.data.model.ToolCategory

object ToolRegistry {

  val allTools: List<Tool> = listOf(
    // CATEGORY A: IMAGE TOOLS (1 - 20)
    Tool(1, "Image Compressor", ToolCategory.IMAGE, "Reduce image file size while maintaining visual quality locally.", listOf("compress", "size", "jpg", "png", "webp"), badge = "Popular", isPopular = true),
    Tool(2, "Image Resizer", ToolCategory.IMAGE, "Resize images by specific width, height or scale percentage.", listOf("resize", "dimensions", "scale", "width", "height"), isPopular = true),
    Tool(3, "JPG to PNG", ToolCategory.IMAGE, "Convert JPEG pictures to lossless PNG format in browser memory.", listOf("jpg", "jpeg", "png", "convert")),
    Tool(4, "PNG to JPG", ToolCategory.IMAGE, "Convert PNG images to standard JPEG with adjustable quality.", listOf("png", "jpg", "convert")),
    Tool(5, "PNG to WebP", ToolCategory.IMAGE, "Convert PNG files into ultra-light modern WebP format.", listOf("png", "webp", "convert")),
    Tool(6, "JPG to WebP", ToolCategory.IMAGE, "Convert JPEG files to highly efficient WebP format.", listOf("jpg", "webp", "convert")),
    Tool(7, "WebP to JPG", ToolCategory.IMAGE, "Convert WebP graphics into widely compatible JPG format.", listOf("webp", "jpg", "convert")),
    Tool(8, "WebP to PNG", ToolCategory.IMAGE, "Convert WebP graphics into transparent lossless PNG.", listOf("webp", "png", "convert")),
    Tool(9, "Image Cropper", ToolCategory.IMAGE, "Crop photos to exact aspect ratios (1:1, 16:9, 4:3, custom).", listOf("crop", "cut", "aspect", "ratio")),
    Tool(10, "Image Rotator", ToolCategory.IMAGE, "Rotate pictures by 90°, 180°, 270° or free angle rotation.", listOf("rotate", "angle", "orientation")),
    Tool(11, "Image Flipper", ToolCategory.IMAGE, "Flip image horizontally or vertically (mirror effect).", listOf("flip", "mirror", "horizontal", "vertical")),
    Tool(12, "Image to Base64", ToolCategory.IMAGE, "Encode image directly to data URI Base64 string for HTML/CSS.", listOf("base64", "datauri", "encode", "embed")),
    Tool(13, "Base64 to Image", ToolCategory.IMAGE, "Decode data URI or Base64 string back into previewable image.", listOf("base64", "decode", "datauri", "restore")),
    Tool(14, "Image Color Picker", ToolCategory.IMAGE, "Inspect pixel colors directly on an image to get HEX and RGB values.", listOf("color", "picker", "eyedropper", "hex", "rgb")),
    Tool(15, "Image Background Color Changer", ToolCategory.IMAGE, "Change background color using pixel color and tolerance matching.", listOf("background", "color", "replace", "tolerance")),
    Tool(16, "Image Grayscale Converter", ToolCategory.IMAGE, "Convert full-color photos into clean black and white grayscale.", listOf("grayscale", "black and white", "monochrome")),
    Tool(17, "Image Blur Tool", ToolCategory.IMAGE, "Apply smooth Gaussian blur effect with adjustable radius.", listOf("blur", "gaussian", "soften", "privacy")),
    Tool(18, "Image Pixelate Tool", ToolCategory.IMAGE, "Pixelate sensitive regions or entire image for retro or privacy effect.", listOf("pixelate", "mosaic", "privacy", "censor")),
    Tool(19, "Multiple Images Resizer", ToolCategory.IMAGE, "Batch resize multiple photos simultaneously to uniform dimensions.", listOf("batch", "multiple", "bulk", "resize")),
    Tool(20, "Image Metadata Viewer", ToolCategory.IMAGE, "Inspect dimensions, MIME type, color depth and EXIF camera data.", listOf("exif", "metadata", "dimensions", "info")),

    // CATEGORY B: PDF TOOLS (21 - 40)
    Tool(21, "Images to PDF", ToolCategory.PDF, "Combine one or multiple pictures into a single clean PDF document.", listOf("image to pdf", "convert", "album", "document"), badge = "Popular", isPopular = true),
    Tool(22, "PDF Merger", ToolCategory.PDF, "Merge multiple PDF documents together into a unified file.", listOf("merge", "combine", "join", "pages"), isPopular = true),
    Tool(23, "PDF Splitter", ToolCategory.PDF, "Split large PDF files by page ranges or extract individual pages.", listOf("split", "separate", "cut", "ranges")),
    Tool(24, "PDF Compressor", ToolCategory.PDF, "Optimize PDF document page rendering and file footprint.", listOf("compress", "reduce", "optimize", "size")),
    Tool(25, "PDF to JPG", ToolCategory.PDF, "Render and extract PDF pages as high-resolution JPEG images.", listOf("pdf to jpg", "render", "pages", "export")),
    Tool(26, "PDF to PNG", ToolCategory.PDF, "Convert PDF document pages to clean lossless PNG images.", listOf("pdf to png", "convert", "render")),
    Tool(27, "JPG to PDF", ToolCategory.PDF, "Quickly convert JPG files into standard formatted PDF documents.", listOf("jpg to pdf", "convert", "create")),
    Tool(28, "PNG to PDF", ToolCategory.PDF, "Convert PNG images into clean PDF documents.", listOf("png to pdf", "convert", "create")),
    Tool(29, "PDF Page Rotator", ToolCategory.PDF, "Rotate misaligned PDF pages by 90, 180, or 270 degrees.", listOf("rotate", "orientation", "pages")),
    Tool(30, "PDF Page Reorder", ToolCategory.PDF, "Rearrange and resequence order of pages in a PDF.", listOf("reorder", "sort", "rearrange", "sequence")),
    Tool(31, "Delete PDF Pages", ToolCategory.PDF, "Remove unwanted pages or blank sheets from PDF files.", listOf("delete", "remove", "clean", "pages")),
    Tool(32, "Extract PDF Pages", ToolCategory.PDF, "Extract specified page numbers from PDF into a new PDF.", listOf("extract", "select", "pages", "save")),
    Tool(33, "PDF Metadata Viewer", ToolCategory.PDF, "Inspect PDF title, author, creator, page count, and creation date.", listOf("metadata", "info", "properties", "author")),
    Tool(34, "PDF Page Counter", ToolCategory.PDF, "Count total number of pages and inspect page dimensions.", listOf("page count", "pages", "total", "stats")),
    Tool(35, "Add Page Numbers", ToolCategory.PDF, "Stamp header or footer page numbers (e.g., Page X of Y) onto PDF.", listOf("page numbers", "stamp", "footer", "header")),
    Tool(36, "Add Watermark to PDF", ToolCategory.PDF, "Add custom confidential or copyright text watermark across PDF pages.", listOf("watermark", "confidential", "stamp", "copyright")),
    Tool(37, "Protect PDF with Password", ToolCategory.PDF, "Set owner and user encryption password security metadata.", listOf("protect", "password", "encrypt", "secure")),
    Tool(38, "Unlock PDF — authorized files only", ToolCategory.PDF, "Verify password and remove protection from authorized PDFs.", listOf("unlock", "decrypt", "authorized", "password")),
    Tool(39, "PDF Text Extractor", ToolCategory.PDF, "Extract text content from digital PDF documents for copying.", listOf("extract text", "copy", "ocr", "read")),
    Tool(40, "PDF Viewer", ToolCategory.PDF, "Clean local PDF reader with page navigation and zoom controls.", listOf("viewer", "reader", "open", "read"), isPopular = true),

    // CATEGORY C: TEXT TOOLS (41 - 60)
    Tool(41, "Word Counter", ToolCategory.TEXT, "Count words, characters, sentences, paragraphs and reading time.", listOf("words", "count", "stats", "characters"), badge = "Popular", isPopular = true),
    Tool(42, "Character Counter", ToolCategory.TEXT, "Count characters with and without whitespace plus byte count.", listOf("characters", "letters", "symbols", "bytes")),
    Tool(43, "Sentence Counter", ToolCategory.TEXT, "Analyze total sentence count and average sentence length.", listOf("sentences", "count", "structure")),
    Tool(44, "Paragraph Counter", ToolCategory.TEXT, "Calculate total paragraphs and block structures in your text.", listOf("paragraphs", "structure", "blocks")),
    Tool(45, "Uppercase Converter", ToolCategory.TEXT, "Convert text completely to uppercase letters (CAPS).", listOf("uppercase", "all caps", "case")),
    Tool(46, "Lowercase Converter", ToolCategory.TEXT, "Transform text entirely to lowercase letters.", listOf("lowercase", "small letters", "case")),
    Tool(47, "Title Case Converter", ToolCategory.TEXT, "Capitalize the First Letter Of Every Major Word.", listOf("title case", "headline", "capitalize")),
    Tool(48, "Sentence Case Converter", ToolCategory.TEXT, "Capitalize the first letter of each sentence cleanly.", listOf("sentence case", "grammar", "format")),
    Tool(49, "Remove Extra Spaces", ToolCategory.TEXT, "Strip redundant spaces, trailing whitespace, and excessive empty lines.", listOf("spaces", "strip", "clean", "trim")),
    Tool(50, "Remove Duplicate Lines", ToolCategory.TEXT, "Filter out duplicate lines and preserve unique entries.", listOf("duplicate lines", "unique", "filter", "dedupe")),
    Tool(51, "Text Reverser", ToolCategory.TEXT, "Reverse entire text strings, words, or individual lines.", listOf("reverse", "backwards", "flip")),
    Tool(52, "Text Sorter", ToolCategory.TEXT, "Sort lines alphabetically (A-Z, Z-A), numerically or by length.", listOf("sort", "alphabetical", "order", "list")),
    Tool(53, "Text to Slug", ToolCategory.TEXT, "Generate clean, URL-safe slugs with hyphens from text.", listOf("slug", "url", "seo", "kebab-case")),
    Tool(54, "Find and Replace", ToolCategory.TEXT, "Find and substitute strings with support for case sensitivity.", listOf("find", "replace", "substitute", "search")),
    Tool(55, "Line Counter", ToolCategory.TEXT, "Count total lines, empty lines, and non-empty lines in text.", listOf("lines", "line count", "rows")),
    Tool(56, "Text Diff Checker", ToolCategory.TEXT, "Compare two text snippets side-by-side to highlight differences.", listOf("diff", "compare", "changes", "comparison"), isPopular = true),
    Tool(57, "Text Repeater", ToolCategory.TEXT, "Repeat any string, word or emoji N times with custom separator.", listOf("repeat", "multiply", "generator")),
    Tool(58, "Text to Binary", ToolCategory.TEXT, "Convert ASCII/UTF-8 characters into 8-bit binary representation.", listOf("binary", "bits", "encode", "bytes")),
    Tool(59, "Binary to Text", ToolCategory.TEXT, "Decode 8-bit binary streams back into readable text.", listOf("binary", "decode", "ascii", "text")),
    Tool(60, "Readability Checker", ToolCategory.TEXT, "Calculate Flesch Reading Ease score and estimated grade level.", listOf("readability", "flesch", "grade", "score")),

    // CATEGORY D: DEVELOPER TOOLS (61 - 80)
    Tool(61, "JSON Formatter", ToolCategory.DEVELOPER, "Prettify and indent JSON with 2-space or 4-space formatting.", listOf("json", "prettify", "format", "indent"), badge = "Popular", isPopular = true),
    Tool(62, "JSON Validator", ToolCategory.DEVELOPER, "Validate JSON syntax with clear error messages and line position.", listOf("json", "validate", "syntax", "lint")),
    Tool(63, "JSON Minifier", ToolCategory.DEVELOPER, "Compress JSON by stripping all whitespace and line breaks.", listOf("json", "minify", "compress")),
    Tool(64, "XML Formatter", ToolCategory.DEVELOPER, "Format and indent XML documents with proper hierarchy.", listOf("xml", "format", "prettify")),
    Tool(65, "HTML Formatter", ToolCategory.DEVELOPER, "Format raw HTML strings into clean indented code.", listOf("html", "format", "prettify")),
    Tool(66, "CSS Minifier", ToolCategory.DEVELOPER, "Minify CSS code by stripping comments and redundant whitespace.", listOf("css", "minify", "compress")),
    Tool(67, "JavaScript Minifier", ToolCategory.DEVELOPER, "Minify JS code by stripping comments and blank lines.", listOf("javascript", "js", "minify")),
    Tool(68, "Base64 Encoder", ToolCategory.DEVELOPER, "Encode raw text or credentials into Base64 format.", listOf("base64", "encode", "string")),
    Tool(69, "Base64 Decoder", ToolCategory.DEVELOPER, "Decode Base64 strings back into UTF-8 text.", listOf("base64", "decode", "string")),
    Tool(70, "URL Encoder", ToolCategory.DEVELOPER, "Encode query parameters and URI special characters safely.", listOf("url", "encode", "percent-encoding")),
    Tool(71, "URL Decoder", ToolCategory.DEVELOPER, "Decode percent-encoded URL components back to plain text.", listOf("url", "decode", "uri")),
    Tool(72, "HTML Encoder", ToolCategory.DEVELOPER, "Escape HTML special entities (&, <, >, \", ') for safe rendering.", listOf("html", "escape", "entities", "encode")),
    Tool(73, "HTML Decoder", ToolCategory.DEVELOPER, "Unescape HTML entity characters back to normal symbols.", listOf("html", "unescape", "decode")),
    Tool(74, "UUID Generator", ToolCategory.DEVELOPER, "Generate cryptographically secure RFC-4122 v4 UUID identifiers.", listOf("uuid", "guid", "generator", "random"), isPopular = true),
    Tool(75, "Password Generator", ToolCategory.DEVELOPER, "Create high-entropy passwords with custom length, symbols & numbers.", listOf("password", "generator", "security", "entropy"), badge = "Popular", isPopular = true),
    Tool(76, "Hash Generator", ToolCategory.DEVELOPER, "Compute cryptographic hashes (MD5, SHA-1, SHA-256, SHA-512).", listOf("hash", "sha256", "md5", "sha512", "crypto")),
    Tool(77, "Regex Tester", ToolCategory.DEVELOPER, "Test regular expressions with real-time match highlights & group capture.", listOf("regex", "regexp", "test", "pattern", "matcher")),
    Tool(78, "Color Code Converter", ToolCategory.DEVELOPER, "Convert between HEX, RGB, HSL, and CMYK color representations.", listOf("color", "hex", "rgb", "hsl", "cmyk")),
    Tool(79, "JWT Decoder", ToolCategory.DEVELOPER, "Decode JSON Web Token header and payload claims (no verification).", listOf("jwt", "token", "decode", "payload", "claims")),
    Tool(80, "Timestamp Converter", ToolCategory.DEVELOPER, "Convert Unix timestamp to human-readable date/time and vice-versa.", listOf("timestamp", "epoch", "unix", "date", "time")),

    // CATEGORY E: CALCULATORS (81 - 100)
    Tool(81, "Percentage Calculator", ToolCategory.CALCULATOR, "Calculate X% of Y, percentage increase/decrease, and ratios.", listOf("percentage", "percent", "math", "calculator"), badge = "Popular", isPopular = true),
    Tool(82, "Age Calculator", ToolCategory.CALCULATOR, "Calculate exact age in years, months, days, hours, and next birthday.", listOf("age", "birthday", "years", "days"), isPopular = true),
    Tool(83, "BMI Calculator", ToolCategory.CALCULATOR, "Calculate Body Mass Index and healthy weight category (informational).", listOf("bmi", "body mass", "health", "weight")),
    Tool(84, "Discount Calculator", ToolCategory.CALCULATOR, "Calculate final sale price, savings amount, and tax addition.", listOf("discount", "sale", "savings", "shopping")),
    Tool(85, "GST Calculator", ToolCategory.CALCULATOR, "Calculate Goods & Services Tax (inclusive or exclusive) with custom rate.", listOf("gst", "tax", "vat", "sales tax")),
    Tool(86, "Profit and Loss Calculator", ToolCategory.CALCULATOR, "Calculate profit/loss amount, percentage margin, and breakeven.", listOf("profit", "loss", "margin", "business")),
    Tool(87, "Simple Interest Calculator", ToolCategory.CALCULATOR, "Calculate simple interest on principal with rate and duration.", listOf("interest", "simple", "principal", "finance")),
    Tool(88, "Compound Interest Calculator", ToolCategory.CALCULATOR, "Calculate compound interest with periodic compounding frequencies.", listOf("compound", "interest", "investment", "finance")),
    Tool(89, "Loan EMI Calculator", ToolCategory.CALCULATOR, "Calculate monthly EMI, total interest payable, and amortization schedule.", listOf("emi", "loan", "mortgage", "finance"), isPopular = true),
    Tool(90, "Date Difference Calculator", ToolCategory.CALCULATOR, "Calculate total days, weeks, months and workdays between two dates.", listOf("date", "difference", "days", "calendar")),
    Tool(91, "Time Duration Calculator", ToolCategory.CALCULATOR, "Add, subtract, or find duration between two timestamps.", listOf("time", "duration", "hours", "minutes")),
    Tool(92, "Average Calculator", ToolCategory.CALCULATOR, "Calculate mean, median, mode, range and sum of numbers.", listOf("average", "mean", "median", "statistics")),
    Tool(93, "Ratio Calculator", ToolCategory.CALCULATOR, "Simplify ratios, solve equivalent ratios (A:B = C:D) and scale.", listOf("ratio", "proportion", "scale", "math")),
    Tool(94, "Salary Calculator", ToolCategory.CALCULATOR, "Calculate gross to net salary with configurable deductions and taxes.", listOf("salary", "paycheck", "net pay", "deductions")),
    Tool(95, "GPA Calculator", ToolCategory.CALCULATOR, "Calculate semester Grade Point Average on 4.0 scale.", listOf("gpa", "grade", "university", "college")),
    Tool(96, "CGPA Calculator", ToolCategory.CALCULATOR, "Calculate Cumulative Grade Point Average across multiple semesters.", listOf("cgpa", "cumulative", "grades")),
    Tool(97, "Marks Percentage Calculator", ToolCategory.CALCULATOR, "Calculate percentage score from obtained and total exam marks.", listOf("marks", "percentage", "exam", "score")),
    Tool(98, "Electricity Bill Estimator", ToolCategory.CALCULATOR, "Estimate monthly power bill based on kWh units and unit price.", listOf("electricity", "power", "bill", "units", "kwh")),
    Tool(99, "Fuel Cost Calculator", ToolCategory.CALCULATOR, "Estimate trip fuel cost from distance, mileage and fuel price.", listOf("fuel", "petrol", "trip", "cost", "mileage")),
    Tool(100, "Unit Converter", ToolCategory.CALCULATOR, "Convert Length, Weight, Temperature, Area, Volume and Speed.", listOf("unit", "convert", "metric", "imperial", "length", "weight"), badge = "Popular", isPopular = true),

    // CATEGORY F: STUDENT TOOLS (101 - 115)
    Tool(101, "Assignment Word Counter", ToolCategory.STUDENT, "Track assignment word limits with target completion percentage.", listOf("assignment", "words", "essay", "limit")),
    Tool(102, "Study Time Calculator", ToolCategory.STUDENT, "Plan daily study allocation across subjects based on exam dates.", listOf("study", "schedule", "time", "revision")),
    Tool(103, "Exam Countdown", ToolCategory.STUDENT, "Live countdown timer to your upcoming exams and deadlines.", listOf("exam", "countdown", "timer", "days left")),
    Tool(104, "Pomodoro Timer", ToolCategory.STUDENT, "Productivity timer with 25m focus and 5m short break intervals.", listOf("pomodoro", "timer", "focus", "productivity"), badge = "Popular", isPopular = true),
    Tool(105, "Random MCQ Generator", ToolCategory.STUDENT, "Generate multiple choice questions with shuffled options for practice.", listOf("mcq", "quiz", "practice", "questions")),
    Tool(106, "Flashcard Maker", ToolCategory.STUDENT, "Create digital study flashcards with flip animation for active recall.", listOf("flashcards", "study", "memorize", "cards"), isPopular = true),
    Tool(107, "Quiz Maker", ToolCategory.STUDENT, "Build and test custom quizzes with instant score tracking.", listOf("quiz", "test", "exam", "maker")),
    Tool(108, "Notes Formatter", ToolCategory.STUDENT, "Format raw study notes into clean bulleted summaries with key takeaways.", listOf("notes", "summary", "bullet", "study")),
    Tool(109, "Citation Generator", ToolCategory.STUDENT, "Generate clean citations for Books, Websites, and Journal articles.", listOf("citation", "bibliography", "reference", "source")),
    Tool(110, "APA Citation Helper", ToolCategory.STUDENT, "Format references following standard APA 7th edition guidelines.", listOf("apa", "citation", "reference", "academic")),
    Tool(111, "Harvard Citation Helper", ToolCategory.STUDENT, "Format referencing adhering to Harvard author-date style.", listOf("harvard", "citation", "referencing")),
    Tool(112, "Timetable Maker", ToolCategory.STUDENT, "Build weekly class timetable and schedule with day-by-day blocks.", listOf("timetable", "schedule", "classes", "routine")),
    Tool(113, "Grade Calculator", ToolCategory.STUDENT, "Calculate weighted course grade from assignments, quizzes, and finals.", listOf("grade", "weighted", "course", "score")),
    Tool(114, "Attendance Calculator", ToolCategory.STUDENT, "Calculate required classes to attend to maintain target attendance (e.g. 75%).", listOf("attendance", "bunk", "classes", "target")),
    Tool(115, "Typing Speed Test", ToolCategory.STUDENT, "Test typing speed in Words Per Minute (WPM) and accuracy percentage.", listOf("typing", "speed", "wpm", "accuracy"), isPopular = true),

    // CATEGORY G: SEO AND MARKETING TOOLS (116 - 130)
    Tool(116, "Meta Title Checker", ToolCategory.SEO, "Check meta title length (recommended 50-60 chars) with Google SERP preview.", listOf("seo", "title", "meta", "serp"), isPopular = true),
    Tool(117, "Meta Description Checker", ToolCategory.SEO, "Check meta description length (150-160 chars) and truncation warning.", listOf("meta", "description", "seo", "snippet")),
    Tool(118, "Keyword Density Checker", ToolCategory.SEO, "Analyze keyword frequency percentage to avoid keyword stuffing.", listOf("keyword", "density", "seo", "frequency")),
    Tool(119, "Hashtag Generator", ToolCategory.SEO, "Generate relevant social media hashtags categorized by niche.", listOf("hashtag", "tags", "social", "instagram")),
    Tool(120, "YouTube Title Generator", ToolCategory.SEO, "Generate catchy, SEO-friendly video title formulas and hooks.", listOf("youtube", "title", "video", "creator")),
    Tool(121, "YouTube Description Formatter", ToolCategory.SEO, "Format video description with timestamps, social links, and disclaimer blocks.", listOf("youtube", "description", "timestamps", "formatting")),
    Tool(122, "YouTube Tag Generator", ToolCategory.SEO, "Generate comma-separated video tags for YouTube upload metadata.", listOf("youtube", "tags", "keywords", "video")),
    Tool(123, "Instagram Caption Formatter", ToolCategory.SEO, "Clean Instagram captions with invisible line breaks and spaced hashtags.", listOf("instagram", "caption", "line breaks", "spacing")),
    Tool(124, "Social Media Bio Generator", ToolCategory.SEO, "Generate professional and punchy profile bios for Twitter/X, IG, LinkedIn.", listOf("bio", "profile", "social", "headline")),
    Tool(125, "Open Graph Preview", ToolCategory.SEO, "Simulate Open Graph card preview as it will appear when shared on social media.", listOf("open graph", "og", "preview", "card", "share")),
    Tool(126, "URL Slug Generator", ToolCategory.SEO, "Create clean, SEO-optimized URL permalinks from post titles.", listOf("slug", "permalink", "url", "seo")),
    Tool(127, "UTM Link Builder", ToolCategory.SEO, "Build Google Analytics campaign tracking URLs with source, medium, campaign.", listOf("utm", "tracking", "analytics", "campaign")),
    Tool(128, "Robots.txt Generator", ToolCategory.SEO, "Generate standardized robots.txt crawling directives for search engines.", listOf("robots.txt", "crawler", "sitemap", "seo")),
    Tool(129, "Sitemap XML Generator", ToolCategory.SEO, "Generate XML sitemap file containing provided user URLs and changefreq.", listOf("sitemap", "xml", "seo", "urls")),
    Tool(130, "Word Frequency Counter", ToolCategory.SEO, "Extract top recurring words and phrases ranked by occurrence count.", listOf("word frequency", "terms", "occurrences", "count")),

    // CATEGORY H: QR AND DESIGN TOOLS (131 - 145)
    Tool(131, "QR Code Generator", ToolCategory.QR_DESIGN, "Generate custom QR codes for any URL, text, or contact info.", listOf("qr", "qrcode", "generator", "barcode"), badge = "Popular", isPopular = true),
    Tool(132, "QR Code Reader", ToolCategory.QR_DESIGN, "Decode QR codes from uploaded images or camera input.", listOf("qr", "reader", "scan", "decode")),
    Tool(133, "Wi-Fi QR Generator", ToolCategory.QR_DESIGN, "Generate QR code for instant Wi-Fi network connection without typing password.", listOf("wifi", "qr", "network", "connect"), isPopular = true),
    Tool(134, "WhatsApp QR Generator", ToolCategory.QR_DESIGN, "Create direct WhatsApp chat QR code with prefilled greeting message.", listOf("whatsapp", "chat", "qr", "direct")),
    Tool(135, "Website QR Generator", ToolCategory.QR_DESIGN, "Generate high-resolution QR code configured for website hyperlinks.", listOf("website", "url", "qr", "link")),
    Tool(136, "Color Picker", ToolCategory.QR_DESIGN, "Interactive HSV/RGB color picker with instant HEX & RGB readout.", listOf("color", "picker", "hex", "rgb", "hsv"), isPopular = true),
    Tool(137, "HEX to RGB", ToolCategory.QR_DESIGN, "Convert 6-digit or 3-digit HEX color codes into RGB values.", listOf("hex", "rgb", "convert")),
    Tool(138, "RGB to HEX", ToolCategory.QR_DESIGN, "Convert Red, Green, Blue values (0-255) into standard HEX code.", listOf("rgb", "hex", "convert")),
    Tool(139, "HEX to HSL", ToolCategory.QR_DESIGN, "Convert HEX colors to Hue, Saturation and Lightness percentages.", listOf("hex", "hsl", "convert")),
    Tool(140, "Gradient Generator", ToolCategory.QR_DESIGN, "Create two-color and multi-color linear or radial CSS gradients.", listOf("gradient", "css", "linear", "radial")),
    Tool(141, "CSS Gradient Copy Tool", ToolCategory.QR_DESIGN, "Browse curated modern gradient presets and copy CSS code in 1 tap.", listOf("gradient", "presets", "css", "copy")),
    Tool(142, "Box Shadow Generator", ToolCategory.QR_DESIGN, "Visual preview and CSS code generator for custom box shadows.", listOf("shadow", "box-shadow", "css", "elevation")),
    Tool(143, "Border Radius Generator", ToolCategory.QR_DESIGN, "Generate custom CSS border-radius and smooth blob shapes.", listOf("border radius", "corners", "css", "shape")),
    Tool(144, "CSS Glassmorphism Generator", ToolCategory.QR_DESIGN, "Visual glassmorphism generator with backdrop blur and frosted glass CSS.", listOf("glassmorphism", "blur", "frost", "css")),
    Tool(145, "Color Palette Generator", ToolCategory.QR_DESIGN, "Generate harmonious complementary, monochromatic, and triadic color schemes.", listOf("palette", "colors", "harmony", "scheme")),

    // CATEGORY I: FILE AND DATA TOOLS (146 - 155)
    Tool(146, "CSV to JSON", ToolCategory.FILE, "Convert spreadsheet CSV rows into structured JSON array of objects.", listOf("csv", "json", "convert", "table"), badge = "Popular", isPopular = true),
    Tool(147, "JSON to CSV", ToolCategory.FILE, "Convert JSON array of records into standard downloadable CSV text.", listOf("json", "csv", "convert", "export")),
    Tool(148, "CSV Viewer", ToolCategory.FILE, "Render CSV data as a searchable, sortable on-screen data table.", listOf("csv", "viewer", "table", "grid"), isPopular = true),
    Tool(149, "File Size Converter", ToolCategory.FILE, "Convert bytes, KB, MB, GB, TB, PB with decimal & binary (KiB/MiB) modes.", listOf("file size", "bytes", "mb", "gb", "converter")),
    Tool(150, "ZIP File Creator", ToolCategory.FILE, "Bundle multiple text files into a compressed standard ZIP archive locally.", listOf("zip", "archive", "compress", "bundle")),
    Tool(151, "ZIP File Extractor", ToolCategory.FILE, "Inspect and extract files and directories from ZIP archives.", listOf("zip", "extract", "unzip", "decompress")),
    Tool(152, "Markdown Previewer", ToolCategory.FILE, "Live preview Markdown formatted text with headings, lists, and code blocks.", listOf("markdown", "preview", "md", "render"), isPopular = true),
    Tool(153, "Markdown to HTML", ToolCategory.FILE, "Convert standard Markdown syntax into semantic HTML markup.", listOf("markdown", "html", "convert")),
    Tool(154, "HTML to Markdown", ToolCategory.FILE, "Convert HTML tags back into lightweight Markdown formatting.", listOf("html", "markdown", "convert")),
    Tool(155, "File Type Detector", ToolCategory.FILE, "Analyze magic bytes and header signatures to detect true file MIME type.", listOf("file type", "magic bytes", "mime", "signature"))
  )

  fun getToolById(id: Int): Tool? = allTools.find { it.id == id }

  fun getToolsByCategory(category: ToolCategory): List<Tool> =
    allTools.filter { it.category == category }

  fun searchTools(query: String, category: ToolCategory? = null): List<Tool> {
    val q = query.trim().lowercase()
    return allTools.filter { tool ->
      val categoryMatch = category == null || tool.category == category
      val textMatch = q.isEmpty() ||
          tool.title.lowercase().contains(q) ||
          tool.description.lowercase().contains(q) ||
          tool.keywords.any { it.lowercase().contains(q) } ||
          tool.id.toString() == q
      categoryMatch && textMatch
    }
  }

  val popularTools: List<Tool> by lazy {
    allTools.filter { it.isPopular }
  }
}
