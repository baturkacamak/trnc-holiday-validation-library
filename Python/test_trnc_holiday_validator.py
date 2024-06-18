# Unit Tests
import unittest

class TestTRNCHolidayValidator(unittest.TestCase):

    def test_fixed_holiday(self):
        validator = TRNCHolidayValidator()
        date = datetime(2024, 1, 1)
        self.assertTrue(validator.is_holiday(date))

    def test_weekend_holiday(self):
        validator = TRNCHolidayValidator()
        date = datetime(2024, 7, 7)  # Sunday
        self.assertTrue(validator.is_holiday(date))

    def test_custom_holiday(self):
        validator = TRNCHolidayValidator()
        date = datetime(2024, 12, 25)
        validator.add_custom_holiday(date)
        self.assertTrue(validator.is_holiday(date))

    def test_recurring_custom_holiday(self):
        validator = TRNCHolidayValidator()
        date = datetime(2024, 12, 25)
        validator.add_custom_holiday(date, recurring=True)
        date_next_year = datetime(2025, 12, 25)
        self.assertTrue(validator.is_holiday(date_next_year))

    def test_islamic_holiday(self):
        validator = TRNCHolidayValidator()
        date = datetime(2024, 4, 10)  # Ramazan Bayramı
        self.assertTrue(validator.is_holiday(date))

if __name__ == '__main__':
    unittest.main()