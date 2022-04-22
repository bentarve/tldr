package io.ubyte.tldr.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

private var _northWest: ImageVector? = null
val Icons.Rounded.NorthWest: ImageVector
    get() {
        if (_northWest != null) {
            return _northWest!!
        }
        _northWest = materialIcon(name = "Rounded.NorthWest") {
            materialPath {
                moveTo(6.0f, 15.0f)
                lineTo(6.0f, 15.0f)
                curveToRelative(0.56f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineTo(8.41f)
                lineTo(17.89f, 19.3f)
                curveToRelative(0.39f, 0.39f, 1.02f, 0.39f, 1.41f, 0.0f)
                lineToRelative(0.0f, 0.0f)
                curveToRelative(0.39f, -0.39f, 0.39f, -1.02f, 0.0f, -1.41f)
                lineTo(8.41f, 7.0f)
                horizontalLineTo(14.0f)
                curveToRelative(0.55f, 0.0f, 1.0f, -0.45f, 1.0f, -1.0f)
                verticalLineTo(6.0f)
                curveToRelative(0.0f, -0.55f, -0.45f, -1.0f, -1.0f, -1.0f)
                horizontalLineTo(6.0f)
                curveTo(5.45f, 5.0f, 5.0f, 5.45f, 5.0f, 6.0f)
                verticalLineTo(14.0f)
                curveTo(5.0f, 14.55f, 5.45f, 15.0f, 6.0f, 15.0f)
                close()
            }
        }
        return _northWest!!
    }

private var _history: ImageVector? = null
val Icons.Rounded.History: ImageVector
    get() {
        if (_history != null) {
            return _history!!
        }
        _history = materialIcon(name = "Rounded.History") {
            materialPath {
                moveTo(13.26f, 3.0f)
                curveTo(8.17f, 2.86f, 4.0f, 6.95f, 4.0f, 12.0f)
                lineTo(2.21f, 12.0f)
                curveToRelative(-0.45f, 0.0f, -0.67f, 0.54f, -0.35f, 0.85f)
                lineToRelative(2.79f, 2.8f)
                curveToRelative(0.2f, 0.2f, 0.51f, 0.2f, 0.71f, 0.0f)
                lineToRelative(2.79f, -2.8f)
                curveToRelative(0.31f, -0.31f, 0.09f, -0.85f, -0.36f, -0.85f)
                lineTo(6.0f, 12.0f)
                curveToRelative(0.0f, -3.9f, 3.18f, -7.05f, 7.1f, -7.0f)
                curveToRelative(3.72f, 0.05f, 6.85f, 3.18f, 6.9f, 6.9f)
                curveToRelative(0.05f, 3.91f, -3.1f, 7.1f, -7.0f, 7.1f)
                curveToRelative(-1.61f, 0.0f, -3.1f, -0.55f, -4.28f, -1.48f)
                curveToRelative(-0.4f, -0.31f, -0.96f, -0.28f, -1.32f, 0.08f)
                curveToRelative(-0.42f, 0.42f, -0.39f, 1.13f, 0.08f, 1.49f)
                curveTo(9.0f, 20.29f, 10.91f, 21.0f, 13.0f, 21.0f)
                curveToRelative(5.05f, 0.0f, 9.14f, -4.17f, 9.0f, -9.26f)
                curveToRelative(-0.13f, -4.69f, -4.05f, -8.61f, -8.74f, -8.74f)
                close()
                moveTo(12.75f, 8.0f)
                curveToRelative(-0.41f, 0.0f, -0.75f, 0.34f, -0.75f, 0.75f)
                verticalLineToRelative(3.68f)
                curveToRelative(0.0f, 0.35f, 0.19f, 0.68f, 0.49f, 0.86f)
                lineToRelative(3.12f, 1.85f)
                curveToRelative(0.36f, 0.21f, 0.82f, 0.09f, 1.03f, -0.26f)
                curveToRelative(0.21f, -0.36f, 0.09f, -0.82f, -0.26f, -1.03f)
                lineToRelative(-2.88f, -1.71f)
                verticalLineToRelative(-3.4f)
                curveToRelative(0.0f, -0.4f, -0.34f, -0.74f, -0.75f, -0.74f)
                close()
            }
        }
        return _history!!
    }

private var _labelImportant: ImageVector? = null
val Icons.Rounded.LabelImportant: ImageVector
    get() {
        if (_labelImportant != null) {
            return _labelImportant!!
        }
        _labelImportant = materialIcon(name = "Rounded.LabelImportant") {
            materialPath {
                moveTo(5.94f, 18.99f)
                horizontalLineTo(15.0f)
                curveToRelative(0.65f, 0.0f, 1.26f, -0.31f, 1.63f, -0.84f)
                lineToRelative(3.95f, -5.57f)
                curveToRelative(0.25f, -0.35f, 0.25f, -0.81f, 0.0f, -1.16f)
                lineToRelative(-3.96f, -5.58f)
                curveTo(16.26f, 5.31f, 15.65f, 5.0f, 15.0f, 5.0f)
                horizontalLineTo(5.94f)
                curveToRelative(-0.81f, 0.0f, -1.28f, 0.93f, -0.81f, 1.59f)
                lineTo(9.0f, 12.0f)
                lineToRelative(-3.87f, 5.41f)
                curveToRelative(-0.47f, 0.66f, 0.0f, 1.58f, 0.81f, 1.58f)
                close()
            }
        }
        return _labelImportant!!
    }
