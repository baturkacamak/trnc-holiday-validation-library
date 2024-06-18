import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TurkeyHolidayValidatorTest {
    private TurkeyHolidayValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new TurkeyHolidayValidator(false);
    }

    @Test
    public void testTurkeyFixedHolidays() {
        assertTrue(validator.isHoliday(LocalDate.of(2024, 4, 23)));
    }
}
