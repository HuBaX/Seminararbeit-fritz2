package styling

import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.Style
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
val defaultButton: Style<BasicParams> = {
    margins {
        top { "5px" }
        right { "5px" }
        bottom { "5px" }
    }
}
