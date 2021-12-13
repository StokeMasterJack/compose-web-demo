package ss.compose.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.css.maxHeight
import org.jetbrains.compose.web.css.overflow
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.pt
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgba
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Pre
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement
import ss.compose.components.HighlightJs
import ss.compose.components.Tabs
import ss.compose.layout.ContainerInSection
import ss.compose.style.WtCols
import ss.compose.style.WtOffsets
import ss.compose.style.WtRows
import ss.compose.style.WtTexts

private fun HTMLElement.setHighlightedCode(code: String) {
    innerText = code
    HighlightJs.highlightElement(this)
}

private val SimpleCounterSnippetData = CodeSnippetData(
    title = "Simple Counter using Composable DOM",
    source = """
        fun main() {
            val count = mutableStateOf(0)
            
            renderComposable(rootElementId = "root") {
                Button(attrs = {
                    onClick { count.value = count.value - 1 }
                }) {
                    Text("-")
                }
                Span(attrs = { style { padding(15.px) }}) { /* we use inline style here */
                    Text("${"$"}{count.value}")
                }
                Button(attrs = {
                    onClick { count.value = count.value + 1 }
                }) {
                    Text("+")
                }
            }
        }
    """.trimIndent()
)

private val DeclareAndUseStylesheetData = CodeSnippetData(
    title = "Declare and use a stylesheet",
    source = """
        object MyStyleSheet : StyleSheet() {
            val container by style { /* define a class `container` */
                border(1.px, LineStyle.Solid, Color.RGB(255, 0, 0))
            }
        }
        
        @Composable
        fun MyComponent() {
            Div(attrs = {
                classes(MyStyleSheet.container) /* use `container` class */
            }) {
                Text("Hello world!")
            }
        }
        
        fun main() {
            renderComposable(rootElementId = "root") {
                Style(MyStyleSheet) /* mount the stylesheet */
                MyComponent()
            }
        }
    """.trimIndent()
)

private val DeclareAndUseCssVariableData = CodeSnippetData(
    title = "Declare and use CSS variables",
    source = """
        object MyVariables {
            val contentBackgroundColor by variable<Color>() /* declare a variable */
        }
        
        object MyStyleSheet: StyleSheet() {
            val container by style {
                MyVariables.contentBackgroundColor(Color("blue")) /* set its value */
            }
            val content by style {
                backgroundColor(MyVariables.contentBackgroundColor.value()) /* use it */
            }
        }
        
        @Composable
        fun MyComponent() {
            Div(attrs = {
                classes(MyStyleSheet.container)
            }) {
                Span(attrs = {
                    classes(MyStyleSheet.content)
                }) {
                    Text("Hello world!")
                }
            }
        }
    """.trimIndent()
)

private val HoverSelectorAndMediaData = CodeSnippetData(
    title = "Hover selector and media query examples",
    source = """
        object MyStyleSheet: StyleSheet() {
            val container by style {
            
                backgroundColor(Color("blue"))
            
                padding(20.px)
                
                hover(self) style { /* `self` is a reference to the class */
                    backgroundColor(Color("red"))
                }
                
                media(maxWidth(500.px)) {
                    self style {
                        padding(10.px)
                    }
                }
            }
        }
    """.trimIndent()
)

private val DefineCssClassInComponentData = CodeSnippetData(
    title = "Define a CSS class in a component",
    source = """
        object MyStyleSheet: StyleSheet() {}

        @Composable
        fun MyComponent() {
            Div(attrs = {
                /* the class name will be generated at runtime */
                classes(MyStyleSheet.css { 
                
                    backgroundColor(Color("blue"))
                    
                    self + ":hover" style { /* this is an example of a raw selector */
                        backgroundColor(Color("red"))
                    }
                })
            }) {
                Text("Hello world!")
            }
        }
    """.trimIndent()
)

private val LayoutsSample = CodeSnippetData(
    title = "Counter for Web and Desktop",
    source = """
        /* Shared code in commonMain - App.kt (No direct control over DOM or CSS here) */
        
        private val counter = mutableStateOf(0)

        @Composable
        fun App() {
            Row {
                Button(onClick = { counter.value = counter.value - 1 }) {
                    Text("-")
                }
        
                Text("${"$"}{counter.value}", modifier = Modifier.padding(16.dp))
        
                Button(onClick = { counter.value = counter.value + 1 }) {
                    Text("+")
                }
            }
        }
        
        /* Desktop specific code in desktopMain: */
        
        fun main() = Window(title = "Demo", size = IntSize(800, 800)) {
            App()
        }
        
        /* Web specific code in jsMain: */
        
        fun main() = renderComposable(rootElementId = "root") { 
            App() 
        }
    """.trimIndent()
)

private val allSnippets = arrayOf(
    SimpleCounterSnippetData,
    DeclareAndUseStylesheetData,
    DeclareAndUseCssVariableData,
    HoverSelectorAndMediaData,
    DefineCssClassInComponentData,
    //LayoutsSample
)

enum class SnippetTab(val data: CodeSnippetData) {

    SimpleCounterSnippet(SimpleCounterSnippetData),
    DeclareAndUseStylesheet(DeclareAndUseStylesheetData),
    DeclareAndUseCssVariable(DeclareAndUseCssVariableData),
    HoverSelectorAndMedia(HoverSelectorAndMediaData),
    DefineCssClassInComponent(DefineCssClassInComponentData);

    companion object {
        operator fun get(index: Int): SnippetTab = values()[index]

        val size: Int get() = values().size

        val all: List<SnippetTab> get() = values().toList()
    }

}

private var currentCodeSnippet: CodeSnippetData by mutableStateOf(allSnippets[0])
private var snippetsTab: SnippetTab by mutableStateOf(SnippetTab[0])

@Composable
fun CodeSamplesVu() {
    ContainerInSection {
        Div({
            classes(WtRows.wtRow)
            style {
                justifyContent(JustifyContent.SpaceBetween)
            }
        }) {
            Div({ classes(WtCols.wtCol6, WtCols.wtColMd4, WtCols.wtColSm12) }) {
                H1({
                    classes(WtTexts.wtH2)
                }) {
                    Text("Code samples")
                }
            }

            Div({ classes(WtOffsets.wtTopOffsetSm24) }) {
                Tabs(tabs = SnippetTab.all, selectedTab = snippetsTab) {
                    snippetsTab = it
                    currentCodeSnippet = it.data
                }
            }
        }

        TitledCodeSample(title = currentCodeSnippet.title, code = currentCodeSnippet.source)
    }
}

@Composable
private fun TitledCodeSample(title: String, code: String) {
    H3({
        classes(WtTexts.wtH3, WtOffsets.wtTopOffset48)
    }) {
        Text(title)
    }

    Div({
        classes(WtOffsets.wtTopOffset24)
        style {
            backgroundColor(rgba(39, 40, 44, 0.05))
            borderRadius(8.px, 8.px, 8.px)
            padding(12.px, 16.px)
        }
    }) {
        FormattedCodeSnippet(code = code)
    }
}

@Composable
fun FormattedCodeSnippet(code: String, language: String = "kotlin") {
    Pre({
        style {
            maxHeight(25.em)
            overflow("auto")
            height(auto)
        }
    }) {
        Code({
            classes("language-$language", "hljs")
            style {
                property("font-family", "'JetBrains Mono', monospace")
                property("tab-size", 4)
                fontSize(10.pt)
                backgroundColor(Color("transparent"))
            }
        }) {
            DomSideEffect(code) {
                it.setHighlightedCode(code)
            }
        }
    }
}

private data class CodeSnippetData(
    val title: String,
    val source: String
)
