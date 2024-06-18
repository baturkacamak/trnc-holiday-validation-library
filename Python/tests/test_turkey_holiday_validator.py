import unittest
from datetime import datetime
from HolidayChecker.Turkey.turkey_holiday_validator import TurkeyHolidayValidator

class TestTurkeyHolidayValidator(unittest.TestCase):
    def setUp(self):
        self.validator = TurkeyHolidayValidator(include_saturday=False)

    def test_turkey_fixed_holidays(self):
        self.assertTrue(self.validator.is_holiday(datetime(2024, 4, 23)))

if __name__ == '__main__':
    unittest.main()
