package ss.compose.components

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.css.CSSNumeric
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.StyleBuilder
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.dom.Div

@Composable
fun Col(styles: StyleBuilder.() -> Unit = {}, content: @Composable () -> Unit) {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
        }
        style(styles)
    }) {
        content()
    }
}

@Composable
fun Row(styles: StyleBuilder.() -> Unit = {}, content: @Composable () -> Unit) {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Row)
        }
        style(styles)
    }) {
        content()
    }
}

@Composable
fun HGap(height: CSSNumeric = 1.cssRem) {
    Row({ marginLeft(height) }) {}
}

@Composable
fun VGap(width: CSSNumeric = 1.cssRem) {
    Row({ marginBottom(width) }) {}
}