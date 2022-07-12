package io.ubyte.tldr.usecases

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import io.ubyte.tldr.theme.LightGreen
import io.ubyte.tldr.theme.LightRed
import io.ubyte.tldr.theme.LightYellow
import io.ubyte.tldr.usecases.Style.Default
import io.ubyte.tldr.usecases.Style.Description
import io.ubyte.tldr.usecases.Style.Highlight
import java.util.*
import javax.inject.Inject

class MarkdownParser @Inject constructor() {
    private val builder = AnnotatedString.Builder()

    operator fun invoke(markdown: String): AnnotatedString {
        markdown.lines().drop(1).forEach { line ->
            when {
                line.startsWith(">") -> summary(line)
                line.startsWith("-") -> description(line)
                line.startsWith("`") && line.endsWith("`") -> example(line)
            }
        }
        return builder.toAnnotatedString()
    }

    private fun summary(line: String) {
        if (!line.contains("More information:")) {
            builder.withStyle(Default) {
                appendLine(
                    line.removePrefix(">")
                        .trim()
                        .removeBacktick()
                )
            }
        }
    }

    private fun description(line: String) = builder
        .appendLine()
        .withStyle(Description) {
            appendLine(
                line.removePrefix("-")
                    .trim()
                    .removeBacktick()
                    .removeBrackets()
            )
        }

    private fun example(line: String) {
        var prev: Char? = null
        var style = Highlight

        line.removeSurrounding("`").forEach { char ->
            if (char == '{' && prev == '{') {
                style = Default
            } else if (char == '}' && prev == '}') {
                style = Highlight
            }

            if (char != '}' && char != '{') {
                builder.withStyle(style) {
                    append(char)
                }
            }
            prev = char
        }
        builder.appendLine()
    }

    private fun String.removeBacktick() = this.replace("`", "")

    private fun String.removeBrackets() = this
        .replace("[", "")
        .replace("]", "")
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}

private object Style {
    val Default = SpanStyle(
        color = LightYellow,
        fontWeight = FontWeight.Bold
    )

    val Highlight = SpanStyle(
        color = LightRed,
        fontWeight = FontWeight.Bold
    )

    val Description = SpanStyle(
        color = LightGreen,
        fontWeight = FontWeight.Bold
    )
}

private fun AnnotatedString.Builder.appendLine(text: String = ""): AnnotatedString.Builder {
    this.append("$text\n").also { return this }
}
