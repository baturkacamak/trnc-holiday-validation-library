import unittest
from datetime import datetime
from HolidayChecker.holiday_validator import HolidayValidator

class TestHolidayValidator(unittest.TestCase):
    def setUp(self):
        self.validator = HolidayValidator(include_saturday=False)

    def test_fixed_holidays(self):
        self.assertTrue(self.validator.is_holiday(datetime(2024, 4, 23)))

    def test_weekends(self):
        self.assertTrue(self.validator.is_holiday(datetime(2024, 4, 28)))

        self.validator = HolidayValidator(include_saturday=True)
        self.assertTrue(self.validator.is_holiday(datetime(2024, 4, 27)))

    def test_custom_holidays(self):
        custom_date = datetime(2024, 12, 25)
        self.validator.add_custom_holiday(custom_date, recurring=True, is_islamic=False)
        self.assertTrue(self.validator.is_holiday(custom_date))

if __name__ == '__main__':
    unittest.main()
