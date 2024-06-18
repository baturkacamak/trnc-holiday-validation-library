import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TRNCHolidayValidatorTest
 *
 * This class contains unit tests for the TRNCHolidayValidator class.
 */
public class TRNCHolidayValidatorTest {
    private TRNCHolidayValidator validator;

    /**
     * Setup method to initialize the TRNCHolidayValidator instance before each test.
     */
    @BeforeEach
    public void setUp() {
        validator = new TRNCHolidayValidator(false);
    }

    /**
     * Test to verify that TRNC-specific fixed holidays are correctly identified.
     */
    @Test
    public void testTRNCFixedHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 8, 1)), "08-01 should be recognized as a holiday.");
    }

    /**
     * Test to verify that TRNC-specific Islamic holidays are correctly identified.
     */
    @Test
    public void testTRNCIslamicHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 9, 15)), "09-15 should be recognized as an Islamic holiday.");
    }
}
