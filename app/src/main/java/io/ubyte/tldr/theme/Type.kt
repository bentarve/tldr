package io.ubyte.tldr.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import io.ubyte.tldr.R

private val SourceCodePro = FontFamily(
    Font(R.font.sourcecodepro_regular),
    Font(R.font.sourcecodepro_semibold, FontWeight.SemiBold)
)

private val SourceCodeProItalic = FontFamily(
    Font(R.font.sourcecodepro_italic),
    Font(R.font.sourcecodepro_italic, FontWeight.SemiBold)
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = SourceCodePro,
        fontWeight = FontWeight.SemiBold,
        fontSize = 100.sp
    ),
    h2 = TextStyle(
        fontFamily = SourceCodePro,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    h3 = TextStyle(
        fontFamily = SourceCodePro,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    h4 = TextStyle(
        fontFamily = SourceCodeProItalic,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
    )
)
