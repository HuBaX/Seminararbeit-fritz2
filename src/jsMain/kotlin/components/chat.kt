package components

import dev.fritz2.components.*
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.styling.p
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.BoxParams
import dev.fritz2.styling.params.FlexParams
import dev.fritz2.styling.params.Style
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.chat() {

    val flexBoxConfig: Style<FlexParams> = {
        direction { column }
        justifyContent { center }
        minHeight { "100%" }
    }

    val chatWindow: Style<FlexParams> = {
        flex { grow { "1" } }
        justifyContent { flexEnd }
        alignItems { stretch }
    }

    val message: Style<FlexParams> = {
        maxWidth { "600px" }
        fontSize { "1.25rem" }
        margin { "0 auto 1rem" }
        padding { "0.5rem 1.5rem" }
    }

    val myMessage: Style<FlexParams> = {
        message()
        margins { right { "1em" } }
    }

    val theirMessage: Style<FlexParams> = {
        message()
        margins { left { "1em" } }
    }

    val messageParagraph: Style<BasicParams> = {
        radius { "1.15rem" }
        lineHeight { "1.25" }
        maxWidth { "75%" }
        padding { "0.5rem .875rem" }
        css("overflow-wrap: break-word;")
        after {
            height { "1rem" }
        }
        before {
            height { "1rem" }
        }
    }

    val myParagraph: Style<FlexParams> = {
        messageParagraph()
        alignItems { flexEnd }
        margins { left { auto } }
        background { color { "#248bf5" } }
        color { "#fff" }
        after {
            background { color { "#fff" } }
            width { "10px" }
        }
    }

    val theirParagraph: Style<BoxParams> = {
        messageParagraph()
        alignItems { flexStart }
        background { color { "#e5e5ea" } }
        color { "#000" }
        after {
            background { color { "#fff" } }
            width { "10px" }
        }
    }

    val chatInput: Style<BasicParams> = {
        display { flex }
        position { sticky { bottom { "0" } } }
    }

    val textInput: Style<BasicParams> = {
        margins {
            left { auto }
            right { auto }
        }
        flex { grow { "1" } }
    }

    val buttonInput: Style<BasicParams> = {
        height { auto }
    }


    flexBox(flexBoxConfig) {
        stackUp(chatWindow) {
            items {
                box(myMessage)
                {
                    p(myParagraph) { +"Some MessageAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa bbbbbbbbbbbbbbb bbbbb  bdwev ewrewr" }
                }
                box(myMessage)
                {
                    p(myParagraph) { +"Some MessageAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa bbbbbbbbbbbbbbb bbbbb  bdwev ewrewr" }
                }
                box(myMessage)
                {
                    p(myParagraph) { +"Some MessageAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa bbbbbbbbbbbbbbb bbbbb  bdwev ewrewr" }
                }
                box(myMessage)
                {
                    p(myParagraph) { +"Some MessageAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa bbbbbbbbbbbbbbb bbbbb  bdwev ewrewr" }
                }
                box(myMessage)
                {
                    p(myParagraph) { +"Some MessageAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa bbbbbbbbbbbbbbb bbbbb  bdwev ewrewr" }
                }
                box(myMessage)
                {
                    p(myParagraph) { +"Some MessageAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa bbbbbbbbbbbbbbb bbbbb  bdwev ewrewr" }
                }
                box(myMessage)
                {
                    p(myParagraph) { +"Some MessageAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa bbbbbbbbbbbbbbb bbbbb  bdwev ewrewr" }
                }
                box(myMessage)
                {
                    p(myParagraph) { +"Some MessageAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa bbbbbbbbbbbbbbb bbbbb  bdwev ewrewr" }
                }
                box(theirMessage)
                {
                    p(theirParagraph) { +"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBCCCCCCCCCCCCCCCcAAAAAAAAAAAAAA" }
                }
            }
        }
        box(chatInput) {
            textArea(textInput) {
                placeholder("Enter your message...")
                resizeBehavior { none }
            }
            clickButton(buttonInput) { text("Send") }
        }
    }
}
