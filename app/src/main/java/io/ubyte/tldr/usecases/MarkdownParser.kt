package io.ubyte.tldr.usecases

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import io.ubyte.tldr.theme.LightGreen
import io.ubyte.tldr.theme.LightRed
import io.ubyte.tldr.theme.LightYellow
import io.ubyte.tldr.usecases.Style.Green
import io.ubyte.tldr.usecases.Style.Red
import io.ubyte.tldr.usecases.Style.Yellow
import io.ubyte.tldr.util.AppCoroutineDispatchers
import kotlinx.coroutines.withContext
import logcat.LogPriority
import logcat.logcat
import org.commonmark.node.*
import org.commonmark.parser.Parser
import javax.inject.Inject

class MarkdownParser @Inject constructor(
    private val parser: Parser,
    private val dispatchers: AppCoroutineDispatchers
) {
    private val builder = AnnotatedString.Builder()
    private val visitor: Visitor = StyleVisitor(builder)

    suspend fun parse(markdown: String): AnnotatedString? = withContext(dispatchers.computation) {
        return@withContext try {
            parser.parse(markdown).accept(visitor)
            builder.toAnnotatedString()
        } catch (e: Exception) {
            logcat(LogPriority.WARN) { "Could not parse markdown page" }
            null
        }
    }
}

private class StyleVisitor(private val builder: AnnotatedString.Builder) : AbstractVisitor() {
    override fun visit(text: Text) {
        if (text.literal == "." || text.literal.startsWith("More info")) return

        if (text.parent.parent is BlockQuote) {
            builder.withStyle(Yellow) {
                appendLine(text.literal)
            }
        } else {
            builder.withStyle(Green) {
                appendLine(
                    text.literal.replace("[", "")
                        .replace("]", "")
                        .trim()
                )
            }
        }
    }

    override fun visit(code: Code) {
        val strings = code.literal.split(" ")

        buildAnnotatedString {
            for ((index, str) in strings.withIndex()) {
                if (str.contains("{{") && str.contains("}}")) {
                    withStyle(Red) { append(str.substringBefore("{{")) }
                    withStyle(Yellow) { append(str.substringAfter("{{").substringBefore("}}")) }
                    withStyle(Red) { append(str.substringAfter("}}")) }
                } else {
                    withStyle(Red) { append(str) }
                }
                if (index < strings.size - 1) append(" ")
            }
        }.also { annotatedString -> builder.appendLine(annotatedString) }
    }

    override fun visit(bulletList: BulletList?) {
        builder.appendLine()
        super.visit(bulletList)
    }

    override fun visit(heading: Heading?) {}

    override fun visit(link: Link?) {}
}

private object Style {
    val Yellow = SpanStyle(
        color = LightYellow,
        fontWeight = FontWeight.Bold
    )

    val Red = SpanStyle(
        color = LightRed,
        fontWeight = FontWeight.Bold
    )

    val Green = SpanStyle(
        color = LightGreen,
        fontWeight = FontWeight.Bold
    )
}

private fun AnnotatedString.Builder.appendLine(text: String = "") = this.append("$text\n")
private fun AnnotatedString.Builder.appendLine(text: AnnotatedString) =
    this.append(text).also { append("\n") }
