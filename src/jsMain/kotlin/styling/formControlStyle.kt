package styling

import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.Style
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
val dialogFormControl: Style<BasicParams> = {
    margins {
        top { "20px" }
        bottom { "20px" }
    }
    firstChild {
        margins {
            top { "0px" }
        }
    }
}
