package com.personal.app

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat

/**
 * A TextView that supports terminal/command-line style formatting with ANSI color codes
 */
class TerminalTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        private const val ANSI_RESET = "\u001B[0m"
        private const val ANSI_BLACK = "\u001B[30m"
        private const val ANSI_RED = "\u001B[31m"
        private const val ANSI_GREEN = "\u001B[32m"
        private const val ANSI_YELLOW = "\u001B[33m"
        private const val ANSI_BLUE = "\u001B[34m"
        private const val ANSI_MAGENTA = "\u001B[35m"
        private const val ANSI_CYAN = "\u001B[36m"
        private const val ANSI_WHITE = "\u001B[37m"
        private const val ANSI_BRIGHT_BLACK = "\u001B[90m"
        private const val ANSI_BRIGHT_RED = "\u001B[91m"
        private const val ANSI_BRIGHT_GREEN = "\u001B[92m"
        private const val ANSI_BRIGHT_YELLOW = "\u001B[93m"
        private const val ANSI_BRIGHT_BLUE = "\u001B[94m"
        private const val ANSI_BRIGHT_MAGENTA = "\u001B[95m"
        private const val ANSI_BRIGHT_CYAN = "\u001B[96m"
        private const val ANSI_BRIGHT_WHITE = "\u001B[97m"
        
        // Background colors
        private const val ANSI_BG_BLACK = "\u001B[40m"
        private const val ANSI_BG_RED = "\u001B[41m"
        private const val ANSI_BG_GREEN = "\u001B[42m"
        private const val ANSI_BG_YELLOW = "\u001B[43m"
        private const val ANSI_BG_BLUE = "\u001B[44m"
        private const val ANSI_BG_MAGENTA = "\u001B[45m"
        private const val ANSI_BG_CYAN = "\u001B[46m"
        private const val ANSI_BG_WHITE = "\u001B[47m"
    }

    init {
        typeface = android.graphics.Typeface.MONOSPACE
        setTextIsSelectable(true)
    }

    fun appendTerminal(text: String) {
        val currentText = this.text?.toString() ?: ""
        val newText = if (currentText.isEmpty()) text else "$currentText\n$text"
        setTextFormatted(newText)
    }

    fun setTextFormatted(text: String) {
        val spannable = SpannableStringBuilder()
        var i = 0
        val len = text.length
        var currentColor = Color.parseColor("#00FF00") // Default green

        while (i < len) {
            if (text[i] == '\u001B' && i + 1 < len && text[i + 1] == '[') {
                // Found ANSI escape sequence
                var j = i + 2
                while (j < len && text[j] != 'm') {
                    j++
                }
                if (j < len) {
                    val code = text.substring(i + 2, j)
                    val color = parseAnsiColor(code)
                    if (color != -1) {
                        if (color == Color.TRANSPARENT) {
                            currentColor = Color.parseColor("#00FF00") // Reset to default green
                        } else {
                            currentColor = color
                        }
                        i = j + 1
                        continue
                    }
                }
            }
            // Regular character - apply current color
            val start = spannable.length
            var end = i + 1
            // Collect consecutive characters until next ANSI code
            while (end < len) {
                if (text[end] == '\u001B' && end + 1 < len && text[end + 1] == '[') {
                    break
                }
                end++
            }
            spannable.append(text.substring(i, end))
            if (currentColor != Color.TRANSPARENT) {
                spannable.setSpan(
                    ForegroundColorSpan(currentColor),
                    start,
                    spannable.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            i = end
        }

        this.text = spannable
    }

    private fun parseAnsiColor(code: String): Int {
        return when (code) {
            "0" -> Color.TRANSPARENT // Reset
            "30" -> Color.parseColor("#000000") // Black
            "31" -> Color.parseColor("#CD0000") // Red
            "32" -> Color.parseColor("#00CD00") // Green
            "33" -> Color.parseColor("#CDCD00") // Yellow
            "34" -> Color.parseColor("#0000EE") // Blue
            "35" -> Color.parseColor("#CD00CD") // Magenta
            "36" -> Color.parseColor("#00CDCD") // Cyan
            "37" -> Color.parseColor("#E5E5E5") // White
            "90" -> Color.parseColor("#7F7F7F") // Bright Black (Gray)
            "91" -> Color.parseColor("#FF0000") // Bright Red
            "92" -> Color.parseColor("#00FF00") // Bright Green
            "93" -> Color.parseColor("#FFFF00") // Bright Yellow
            "94" -> Color.parseColor("#5C5CFF") // Bright Blue
            "95" -> Color.parseColor("#FF00FF") // Bright Magenta
            "96" -> Color.parseColor("#00FFFF") // Bright Cyan
            "97" -> Color.parseColor("#FFFFFF") // Bright White
            else -> -1
        }
    }
}

