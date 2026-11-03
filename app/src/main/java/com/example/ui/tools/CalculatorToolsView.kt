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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow

@Composable
fun CalculatorToolScreen(
  tool: Tool,
  modifier: Modifier = Modifier
) {
  // Input fields for various calculators
  var val1 by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        81 -> "25" // Percentage
        82 -> "1998-05-14" // Age
        83 -> "175" // Height cm
        84 -> "1200" // Original price
        85 -> "5000" // GST amount
        86 -> "1000" // Cost price
        87, 88 -> "100000" // Principal
        89 -> "500000" // Loan
        90 -> "2026-01-01" // Date 1
        91 -> "09:30" // Time 1
        92 -> "15, 28, 34, 45, 52, 68, 77, 89" // Numbers
        93 -> "16" // Ratio A
        94 -> "150000" // Gross Salary
        95, 96 -> "3.75" // GPA
        97 -> "880" // Marks obtained
        98 -> "350" // Electricity units (kWh)
        99 -> "450" // Distance km
        100 -> "100" // Unit value
        else -> "100"
      }
    )
  }

  var val2 by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        81 -> "500" // Total
        83 -> "70" // Weight kg
        84 -> "20" // Discount %
        85 -> "18" // GST %
        86 -> "1450" // Selling price
        87, 88 -> "8.5" // Interest rate %
        89 -> "11.5" // EMI rate %
        90 -> "2026-12-31" // Date 2
        91 -> "17:45" // Time 2
        93 -> "9" // Ratio B
        94 -> "15" // Tax %
        97 -> "1100" // Total marks
        98 -> "35" // Electricity rate per unit
        99 -> "15" // Fuel mileage km/L
        100 -> "Meters to Feet" // Conversion mode
        else -> "10"
      }
    )
  }

  var val3 by remember(tool.id) {
    mutableStateOf(
      when (tool.id) {
        87, 88 -> "5" // Time in years
        89 -> "60" // Loan tenure in months
        93 -> "1920" // Ratio C
        94 -> "10000" // Monthly deductions
        99 -> "280" // Fuel price per liter
        else -> ""
      }
    )
  }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    when (tool.id) {
      81 -> {
        // Percentage Calculator
        ToolBoxTextField(value = val1, onValueChange = { val1 = it }, label = "Percentage (%)", singleLine = true)
        ToolBoxTextField(value = val2, onValueChange = { val2 = it }, label = "Total Amount", singleLine = true)

        val p = val1.toDoubleOrNull() ?: 0.0
        val t = val2.toDoubleOrNull() ?: 0.0
        val res = (p / 100.0) * t

        ToolBoxResultCard(
          title = "Percentage Calculation",
          resultText = "$p% of $t = ${"%.2f".format(res)}\n" +
              "Total with Addition: ${"%.2f".format(t + res)}\n" +
              "Total with Subtraction: ${"%.2f".format(t - res)}"
        )
      }

      82 -> {
        // Age Calculator
        ToolBoxTextField(
          value = val1,
          onValueChange = { val1 = it },
          label = "Date of Birth (YYYY-MM-DD)",
          singleLine = true
        )

        val ageInfo = remember(val1) { calculateAge(val1) }
        ToolBoxResultCard(title = "Exact Age Breakdown", resultText = ageInfo)
      }

      83 -> {
        // BMI Calculator
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          ToolBoxTextField(value = val1, onValueChange = { val1 = it }, label = "Height (cm)", modifier = Modifier.weight(1f), singleLine = true)
          ToolBoxTextField(value = val2, onValueChange = { val2 = it }, label = "Weight (kg)", modifier = Modifier.weight(1f), singleLine = true)
        }

        val h = val1.toDoubleOrNull() ?: 1.0
        val w = val2.toDoubleOrNull() ?: 0.0
        val heightM = h / 100.0
        val bmi = if (heightM > 0) w / (heightM * heightM) else 0.0
        val category = when {
          bmi < 18.5 -> "Underweight"
          bmi < 25.0 -> "Normal weight (Healthy)"
          bmi < 30.0 -> "Overweight"
          else -> "Obese"
        }
        val healthyMin = 18.5 * (heightM * heightM)
        val healthyMax = 24.9 * (heightM * heightM)

        ToolBoxResultCard(
          title = "Body Mass Index (BMI)",
          resultText = "BMI Score: ${"%.1f".format(bmi)} kg/m²\n" +
              "Category: $category\n" +
              "Healthy weight range for your height: ${"%.1f".format(healthyMin)} kg - ${"%.1f".format(healthyMax)} kg\n\n" +
              "Note: BMI is for general wellness screening, not clinical diagnosis."
        )
      }

      84 -> {
        // Discount Calculator
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          ToolBoxTextField(value = val1, onValueChange = { val1 = it }, label = "Original Price", modifier = Modifier.weight(1f), singleLine = true)
          ToolBoxTextField(value = val2, onValueChange = { val2 = it }, label = "Discount (%)", modifier = Modifier.weight(1f), singleLine = true)
        }

        val orig = val1.toDoubleOrNull() ?: 0.0
        val discPct = val2.toDoubleOrNull() ?: 0.0
        val savings = orig * (discPct / 100.0)
        val finalPrice = orig - savings

        ToolBoxResultCard(
          title = "Savings & Final Price",
          resultText = "Final Sale Price: ${"%.2f".format(finalPrice)}\n" +
              "Total Savings: ${"%.2f".format(savings)}\n" +
              "You save $discPct% on the initial price of ${"%.2f".format(orig)}"
        )
      }

      85 -> {
        // GST Calculator
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          ToolBoxTextField(value = val1, onValueChange = { val1 = it }, label = "Amount", modifier = Modifier.weight(1f), singleLine = true)
          ToolBoxTextField(value = val2, onValueChange = { val2 = it }, label = "GST Rate (%)", modifier = Modifier.weight(1f), singleLine = true)
        }

        val amt = val1.toDoubleOrNull() ?: 0.0
        val rate = val2.toDoubleOrNull() ?: 18.0

        // Exclusive GST (Add GST)
        val addGstTax = amt * (rate / 100.0)
        val addGstTotal = amt + addGstTax

        // Inclusive GST (Extract GST)
        val incGstBase = amt / (1 + rate / 100.0)
        val incGstTax = amt - incGstBase

        ToolBoxResultCard(
          title = "GST Tax Breakdown ($rate%)",
          resultText = "GST EXCLUSIVE (Amount + GST):\n" +
              "  • Net Amount: ${"%.2f".format(amt)}\n" +
              "  • GST Tax Amount ($rate%): ${"%.2f".format(addGstTax)}\n" +
              "  • Total Payable: ${"%.2f".format(addGstTotal)}\n\n" +
              "GST INCLUSIVE (Extract GST from Amount):\n" +
              "  • Base Price (Before GST): ${"%.2f".format(incGstBase)}\n" +
              "  • Included GST Tax: ${"%.2f".format(incGstTax)}\n" +
              "  • Gross Amount: ${"%.2f".format(amt)}"
        )
      }

      89 -> {
        // Loan EMI Calculator
        ToolBoxTextField(value = val1, onValueChange = { val1 = it }, label = "Loan Amount (Principal)", singleLine = true)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          ToolBoxTextField(value = val2, onValueChange = { val2 = it }, label = "Annual Interest Rate (%)", modifier = Modifier.weight(1f), singleLine = true)
          ToolBoxTextField(value = val3, onValueChange = { val3 = it }, label = "Tenure (Months)", modifier = Modifier.weight(1f), singleLine = true)
        }

        val principal = val1.toDoubleOrNull() ?: 0.0
        val annualRate = val2.toDoubleOrNull() ?: 0.0
        val months = val3.toIntOrNull() ?: 12

        val monthlyRate = (annualRate / 12.0) / 100.0
        val emi = if (monthlyRate > 0) {
          (principal * monthlyRate * (1 + monthlyRate).pow(months.toDouble())) /
              ((1 + monthlyRate).pow(months.toDouble()) - 1)
        } else {
          principal / months
        }
        val totalPayment = emi * months
        val totalInterest = totalPayment - principal

        ToolBoxResultCard(
          title = "Loan Amortization Summary",
          resultText = "Monthly EMI: ${"%.2f".format(emi)}\n" +
              "Total Interest Payable: ${"%.2f".format(totalInterest)}\n" +
              "Total Amount Payable: ${"%.2f".format(totalPayment)}\n" +
              "Tenure: $months months (${months / 12} years)"
        )
      }

      92 -> {
        // Average & Stats Calculator
        ToolBoxTextField(
          value = val1,
          onValueChange = { val1 = it },
          label = "Numbers (comma or space separated)",
          minLines = 3
        )

        val stats = remember(val1) { calculateStats(val1) }
        ToolBoxResultCard(title = "Statistical Analysis", resultText = stats)
      }

      100 -> {
        // Unit Converter
        var unitCategory by remember { mutableStateOf("Length") }
        var fromUnit by remember { mutableStateOf("Meters (m)") }
        var toUnit by remember { mutableStateOf("Feet (ft)") }

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          ToolBoxTextField(
            value = val1,
            onValueChange = { val1 = it },
            label = "Value to Convert",
            modifier = Modifier.weight(1f),
            singleLine = true
          )
        }

        val converted = remember(val1, fromUnit, toUnit) {
          convertUnits(val1.toDoubleOrNull() ?: 0.0, fromUnit, toUnit)
        }

        ToolBoxResultCard(
          title = "Converted Measurement",
          resultText = "$val1 $fromUnit = ${"%.4f".format(converted)} $toUnit\n\n" +
              "Common Equivalents for $val1 $fromUnit:\n" +
              "  • In Inches: ${"%.2f".format((val1.toDoubleOrNull() ?: 0.0) * 39.3701)} in\n" +
              "  • In Centimeters: ${"%.2f".format((val1.toDoubleOrNull() ?: 0.0) * 100)} cm\n" +
              "  • In Kilometers: ${"%.5f".format((val1.toDoubleOrNull() ?: 0.0) / 1000)} km\n" +
              "  • In Miles: ${"%.5f".format((val1.toDoubleOrNull() ?: 0.0) * 0.000621371)} mi"
        )
      }

      else -> {
        // Generic Math, Financial & Everyday Calculators
        ToolBoxTextField(value = val1, onValueChange = { val1 = it }, label = getCalcLabel1(tool.id), singleLine = true)
        ToolBoxTextField(value = val2, onValueChange = { val2 = it }, label = getCalcLabel2(tool.id), singleLine = true)
        if (getCalcLabel3(tool.id).isNotBlank()) {
          ToolBoxTextField(value = val3, onValueChange = { val3 = it }, label = getCalcLabel3(tool.id), singleLine = true)
        }

        val calcResult = remember(tool.id, val1, val2, val3) {
          processGenericCalculator(tool.id, val1, val2, val3)
        }

        ToolBoxResultCard(title = "${tool.title} Result", resultText = calcResult)
      }
    }
  }
}

private fun getCalcLabel1(toolId: Int): String = when (toolId) {
  86 -> "Cost Price (CP)"
  87, 88 -> "Principal Amount"
  90 -> "Start Date (YYYY-MM-DD)"
  91 -> "Start Time (HH:MM)"
  93 -> "Ratio A"
  94 -> "Gross Monthly Salary"
  95 -> "Total Grade Points"
  96 -> "Cumulative Grade Points"
  97 -> "Marks Obtained"
  98 -> "Units Consumed (kWh)"
  99 -> "Trip Distance (km)"
  else -> "Value 1"
}

private fun getCalcLabel2(toolId: Int): String = when (toolId) {
  86 -> "Selling Price (SP)"
  87, 88 -> "Annual Interest Rate (%)"
  90 -> "End Date (YYYY-MM-DD)"
  91 -> "End Time (HH:MM)"
  93 -> "Ratio B"
  94 -> "Tax Rate (%)"
  95 -> "Total Credit Hours"
  96 -> "Total Semesters"
  97 -> "Total Maximum Marks"
  98 -> "Electricity Rate Per Unit (kWh)"
  99 -> "Fuel Economy / Mileage (km/L)"
  else -> "Value 2"
}

private fun getCalcLabel3(toolId: Int): String = when (toolId) {
  87, 88 -> "Time Duration (Years)"
  93 -> "Ratio C (Solve for D)"
  94 -> "Monthly Deductions / Provident Fund"
  99 -> "Fuel Price Per Liter"
  else -> ""
}

private fun processGenericCalculator(toolId: Int, v1: String, v2: String, v3: String): String {
  val d1 = v1.toDoubleOrNull() ?: 0.0
  val d2 = v2.toDoubleOrNull() ?: 0.0
  val d3 = v3.toDoubleOrNull() ?: 0.0

  return when (toolId) {
    86 -> {
      // Profit & Loss
      val diff = d2 - d1
      val pct = if (d1 > 0) (Math.abs(diff) / d1) * 100.0 else 0.0
      if (diff >= 0) {
        "Profit Amount: ${"%.2f".format(diff)}\nProfit Margin: ${"%.2f".format(pct)}%"
      } else {
        "Loss Amount: ${"%.2f".format(-diff)}\nLoss Percentage: ${"%.2f".format(pct)}%"
      }
    }
    87 -> {
      // Simple Interest
      val interest = (d1 * d2 * d3) / 100.0
      val total = d1 + interest
      "Simple Interest Earned: ${"%.2f".format(interest)}\nTotal Final Maturity Value: ${"%.2f".format(total)}"
    }
    88 -> {
      // Compound Interest
      val rateDecimal = d2 / 100.0
      val total = d1 * (1.0 + rateDecimal).pow(d3)
      val interest = total - d1
      "Compound Interest Earned: ${"%.2f".format(interest)}\nTotal Final Maturity Value: ${"%.2f".format(total)}"
    }
    90 -> {
      // Date Difference
      try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val start = sdf.parse(v1.trim()) ?: Date()
        val end = sdf.parse(v2.trim()) ?: Date()
        val diffMs = Math.abs(end.time - start.time)
        val days = diffMs / (1000 * 60 * 60 * 24)
        val weeks = days / 7
        val remDays = days % 7
        "$days Total Days\n($weeks weeks and $remDays days)"
      } catch (e: Exception) {
        "Please enter dates in YYYY-MM-DD format (e.g. 2026-05-15)"
      }
    }
    91 -> {
      // Time Duration
      try {
        val p1 = v1.split(":").map { it.trim().toInt() }
        val p2 = v2.split(":").map { it.trim().toInt() }
        val mins1 = p1[0] * 60 + p1[1]
        val mins2 = p2[0] * 60 + p2[1]
        val diff = if (mins2 >= mins1) mins2 - mins1 else (1440 - mins1) + mins2
        val h = diff / 60
        val m = diff % 60
        "Duration: $h hours and $m minutes ($diff total minutes)"
      } catch (e: Exception) {
        "Please enter times in HH:MM format (e.g. 09:30 and 17:45)"
      }
    }
    93 -> {
      // Ratio A:B = C:D
      if (d1 == 0.0) return "Ratio A cannot be 0"
      val d = (d2 * d3) / d1
      val gcd = gcd(d1.toLong(), d2.toLong())
      val simA = d1.toLong() / gcd
      val simB = d2.toLong() / gcd
      "Solved D: ${"%.2f".format(d)}\nRatio: $d1 : $d2 = $d3 : ${"%.2f".format(d)}\nSimplified Ratio: $simA : $simB"
    }
    94 -> {
      // Salary Calculator
      val taxAmount = d1 * (d2 / 100.0)
      val netPay = d1 - taxAmount - d3
      "Net Take-Home Monthly Salary: ${"%.2f".format(netPay)}\n" +
          "Income Tax Deduction: ${"%.2f".format(taxAmount)}\n" +
          "Other Deductions: ${"%.2f".format(d3)}\n" +
          "Annual Net Pay: ${"%.2f".format(netPay * 12)}"
    }
    95, 96 -> {
      // GPA / CGPA
      val gpa = if (d2 > 0) d1 / d2 else d1
      val standing = when {
        gpa >= 3.7 -> "First Class Honours / Dean's List"
        gpa >= 3.0 -> "Upper Second Class"
        gpa >= 2.0 -> "Satisfactory"
        else -> "Academic Probation Warning"
      }
      "GPA: ${"%.2f".format(gpa)} / 4.0\nAcademic Standing: $standing"
    }
    97 -> {
      // Marks Percentage
      val pct = if (d2 > 0) (d1 / d2) * 100.0 else 0.0
      val grade = when {
        pct >= 90 -> "A+ (Outstanding)"
        pct >= 80 -> "A (Excellent)"
        pct >= 70 -> "B (Very Good)"
        pct >= 60 -> "C (Good)"
        pct >= 50 -> "D (Pass)"
        else -> "F (Fail)"
      }
      "Percentage: ${"%.2f".format(pct)}%\nGrade: $grade"
    }
    98 -> {
      // Electricity Bill Estimator
      val energyCharge = d1 * d2
      val salesTax = energyCharge * 0.17
      val total = energyCharge + salesTax
      "Energy Cost (${d1.toInt()} kWh @ Rs ${d2}/unit): ${"%.2f".format(energyCharge)}\n" +
          "Estimated Taxes & Surcharges (17%): ${"%.2f".format(salesTax)}\n" +
          "Estimated Total Electricity Bill: ${"%.2f".format(total)}"
    }
    99 -> {
      // Fuel Cost Calculator
      val litersNeeded = if (d2 > 0) d1 / d2 else 0.0
      val totalCost = litersNeeded * d3
      "Total Fuel Required: ${"%.2f".format(litersNeeded)} Liters\n" +
          "Total Trip Fuel Cost: ${"%.2f".format(totalCost)}\n" +
          "Cost per Kilometer: ${"%.2f".format(if (d1 > 0) totalCost / d1 else 0.0)}"
    }
    else -> "Result: ${d1 + d2}"
  }
}

private fun calculateAge(dobStr: String): String {
  return try {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val dob = Calendar.getInstance().apply { time = sdf.parse(dobStr.trim()) ?: Date() }
    val now = Calendar.getInstance()

    var years = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
    var months = now.get(Calendar.MONTH) - dob.get(Calendar.MONTH)
    var days = now.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH)

    if (days < 0) {
      months--
      val prevMonth = Calendar.getInstance().apply { add(Calendar.MONTH, -1) }
      days += prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH)
    }
    if (months < 0) {
      years--
      months += 12
    }

    val totalDays = (now.timeInMillis - dob.timeInMillis) / (1000 * 60 * 60 * 24)
    val totalHours = totalDays * 24

    "You are:\n" +
        "• $years Years, $months Months, and $days Days old\n" +
        "• $totalDays Total Days lived\n" +
        "• ~$totalHours Total Hours"
  } catch (e: Exception) {
    "Enter a valid date in YYYY-MM-DD format (e.g. 2000-08-25)"
  }
}

private fun calculateStats(input: String): String {
  val nums = input.split("[,\\s]+".toRegex()).mapNotNull { it.toDoubleOrNull() }.sorted()
  if (nums.isEmpty()) return "Please enter numbers separated by commas or spaces."

  val sum = nums.sum()
  val count = nums.size
  val mean = sum / count
  val median = if (count % 2 == 1) nums[count / 2] else (nums[count / 2 - 1] + nums[count / 2]) / 2.0
  val min = nums.first()
  val max = nums.last()
  val range = max - min

  return "Count: $count\n" +
      "Sum: ${"%.2f".format(sum)}\n" +
      "Mean (Average): ${"%.2f".format(mean)}\n" +
      "Median: ${"%.2f".format(median)}\n" +
      "Minimum: ${"%.2f".format(min)}\n" +
      "Maximum: ${"%.2f".format(max)}\n" +
      "Range: ${"%.2f".format(range)}"
}

private fun convertUnits(value: Double, from: String, to: String): Double {
  // Meters to Feet
  return value * 3.28084
}

private fun gcd(a: Long, b: Long): Long {
  var x = Math.abs(a)
  var y = Math.abs(b)
  if (y == 0L) return if (x == 0L) 1L else x
  while (y != 0L) {
    val temp = y
    y = x % y
    x = temp
  }
  return x
}
