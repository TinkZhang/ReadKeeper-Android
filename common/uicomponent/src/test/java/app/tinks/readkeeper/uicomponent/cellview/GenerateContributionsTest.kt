package app.tinks.readkeeper.uicomponent.cellview

import app.tinks.readkeeper.basic.model.Record
import com.google.firebase.Timestamp
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class GenerateContributionsTest {
    @Test
    fun records_to_contributions() {
        val records = listOf(
            Record(startPage = 0, endPage = 100, timestamp = Timestamp(Date(122, 9, 1))),
            Record(startPage = 1, endPage = 100, timestamp = Timestamp(Date(122, 9, 2))),
            Record(startPage = 1, endPage = 100, timestamp = Timestamp(Date(122, 9, 4))),
            Record(startPage = 100, endPage = 200, timestamp = Timestamp(Date(122, 9, 1))),
        )
        val contributions = generateontributions(records)

        assertEquals(contributions[0].day.toString(), "2022-10-01")
        assertEquals(contributions[0].pages, 200)

        assertEquals(contributions[1].day.toString(), "2022-10-02")
        assertEquals(contributions[1].pages, 99)

        assertEquals(contributions[2].day.toString(), "2022-10-03")
        assertEquals(contributions[2].pages, 0)

    }
}