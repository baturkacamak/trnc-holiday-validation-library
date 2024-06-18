package holidaychecker

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class HolidayValidatorTest {
    private lateinit var validator: HolidayValidator

    @BeforeEach
    fun setUp() {
        validator = HolidayValidator(false)
    }

    @Test
    fun testFixedHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 4, 23)))
    }

    @Test
    fun testIslamicHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 4, 10)))
    }
}
