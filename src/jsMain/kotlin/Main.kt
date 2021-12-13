import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.renderComposable
import ss.compose.components.Tabs
import ss.compose.layout.Header
import ss.compose.layout.Layout
import ss.compose.layout.MainContentLayout
import ss.compose.layout.PageFooter
import ss.compose.pages.BlackjackVu
import ss.compose.pages.CodeSamplesVu
import ss.compose.style.AppStylesheet
import ss.compose.style.WtOffsets

enum class MainTab(val component: @Composable () -> Unit) {

    Blackjack(component = { BlackjackVu() }),
    CodeSamples(component = { CodeSamplesVu() });

    companion object {
        operator fun get(index: Int): MainTab = values()[index]

        val size: Int get() = values().size

        val all: List<MainTab> get() = values().toList()
    }

}

private var mainTab: MainTab by mutableStateOf(MainTab.Blackjack)

fun main() {


    renderComposable(rootElementId = "root") {


        Style(AppStylesheet)

        Layout {
            Header()
            MainContentLayout {
                Div({ classes(WtOffsets.wtTopOffsetSm24) }) {
                    Tabs(tabs = MainTab.all, selectedTab = mainTab) { mainTab = it }
                }
                Div {
//                    selectedTab.component()
                    when (mainTab) {
                        MainTab.Blackjack -> BlackjackVu()
                        MainTab.CodeSamples -> CodeSamplesVu()
                    }
                }

            }
            PageFooter()
        }


    }
}