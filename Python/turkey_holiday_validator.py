from HolidayChecker.HolidayValidator import HolidayValidator

class TurkeyHolidayValidator(HolidayValidator):
    # Uses the common holidays defined in the base class
    def __init__(self, include_saturday=False):
        super().__init__(include_saturday)
