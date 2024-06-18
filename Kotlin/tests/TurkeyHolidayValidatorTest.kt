package holidaychecker

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class TurkeyHolidayValidatorTest {
    private lateinit var validator: TurkeyHolidayValidator

    @BeforeEach
    fun setUp() {
        validator = TurkeyHolidayValidator(false)
    }

    @Test
    fun testTurkeyFixedHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 4, 23)))
    }

    @Test
    fun testTurkeyIslamicHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 4, 10)))
    }
}
