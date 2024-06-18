from datetime import datetime
from HolidayChecker.HolidayValidator import HolidayValidator

class TRNCHolidayValidator(HolidayValidator):
    """
    TRNCHolidayValidator

    This class is used to determine if a given date is a holiday in TRNC.
    """

    FIXED_HOLIDAYS = HolidayValidator.FIXED_HOLIDAYS + [
        '08-01',  # TMT Günü
        '11-15',  # Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    ]

    BASE_DATES = HolidayValidator.BASE_DATES + [
        {'name': 'mevlidKandili', 'date': '2024-09-15', 'days': 1}
    ]

    def __init__(self, include_saturday=False):
        """
        Initialize the TRNCHolidayValidator

        :param include_saturday: Boolean flag indicating whether to include Saturdays as holidays
        """
        super().__init__(include_saturday)
