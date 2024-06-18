import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class HolidayValidatorTest {
    private HolidayValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new HolidayValidator(false) {};
    }

    @Test
    public void testFixedHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 4, 23)));
    }

    @Test
    public void testWeekends() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 4, 28)));

        validator = new HolidayValidator(true) {};
        assertTrue(validator.isHoliday(LocalDate.of(2024, 4, 27)));
    }

    @Test
    public void testCustomHolidays() {
        LocalDate customDate = LocalDate.of(2024, 12, 25);
        validator.addCustomHoliday(customDate, true, false);
        assertTrue(validator.isHoliday(customDate));
    }
}
