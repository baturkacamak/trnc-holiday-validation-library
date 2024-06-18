package holidaychecker

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class TRNCHolidayValidatorTest {
    private lateinit var validator: TRNCHolidayValidator

    @BeforeEach
    fun setUp() {
        validator = TRNCHolidayValidator(false)
    }

    @Test
    fun testTRNCFixedHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 8, 1)))
    }

    @Test
    fun testTRNCIslamicHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 9, 15)))
    }
}
