package idrisadetunmbi.criminalintent

import java.util.Date
import java.util.UUID

class Crime {

    val id: UUID = UUID.randomUUID()
    var title: String? = null
    var date: Date? = null
    var isSolved: Boolean = false

    init {
        date = Date()
    }
}
