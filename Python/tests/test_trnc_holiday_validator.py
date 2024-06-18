import unittest
from datetime import datetime
from HolidayChecker.TRNC.trnc_holiday_validator import TRNCHolidayValidator

class TestTRNCHolidayValidator(unittest.TestCase):
    def setUp(self):
        self.validator = TRNCHolidayValidator(include_saturday=False)

    def test_trnc_fixed_holidays(self):
        self.assertTrue(self.validator.is_holiday(datetime(2024, 8, 1)))

    def test_trnc_islamic_holidays(self):
        self.assertTrue(self.validator.is_holiday(datetime(2024, 9, 15)))

if __name__ == '__main__':
    unittest.main()
