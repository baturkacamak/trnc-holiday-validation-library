from datetime import datetime, timedelta

class HolidayValidator:
    FIXED_HOLIDAYS = [
        '01-01',  # Yeni Yıl
        '04-23',  # Ulusal Egemenlik ve Çocuk Bayramı
        '05-01',  # İşçi Bayramı
        '05-19',  # Atatürk'ü Anma, Gençlik ve Spor Bayramı
        '07-20',  # Barış ve Özgürlük Bayramı
        '08-30',  # Zafer Bayramı
        '10-29',  # Cumhuriyet Bayramı
    ]

    BASE_DATES = [
        {'name': 'ramazanBayrami', 'date': '2024-04-10', 'days': 3},
        {'name': 'kurbanBayrami', 'date': '2024-06-28', 'days': 4}
    ]

    ISLAMIC_YEAR_DAYS = 354.36667
    BASE_YEAR = 2024

    def __init__(self, include_saturday=False):
        self.include_saturday = include_saturday
        self.custom_holidays = []

    def is_holiday(self, date):
        """
        Check if the given date is a holiday

        :param date: The date to check
        :return: True if the date is a holiday, false otherwise
        """
        return self.is_weekend(date) or self.is_fixed_holiday(date) or self.is_islamic_holiday(date) or self.is_custom_holiday(date)

    def add_custom_holiday(self, date, recurring=False, is_islamic=False):
        """
        Add a custom holiday

        :param date: The date of the custom holiday
        :param recurring: Whether the holiday recurs every year
        :param is_islamic: Whether the holiday should be considered as an Islamic holiday
        """
        self.custom_holidays.append({'date': date, 'recurring': recurring, 'is_islamic': is_islamic})

    def is_weekend(self, date):
        """
        Check if the given date is a weekend

        :param date: The date to check
        :return: True if the date is a weekend, false otherwise
        """
        day_of_week = date.weekday()
        return day_of_week == 6 or (self.include_saturday and day_of_week == 5)

    def is_fixed_holiday(self, date):
        """
        Check if the given date is a fixed holiday

        :param date: The date to check
        :return: True if the date is a fixed holiday, false otherwise
        """
        month_day = date.strftime('%m-%d')
        return month_day in self.FIXED_HOLIDAYS

    def is_islamic_holiday(self, date):
        """
        Check if the given date is an Islamic holiday

        :param date: The date to check
        :return: True if the date is an Islamic holiday, false otherwise
        """
        year = date.year
        islamic_holidays = self.calculate_islamic_holidays(year)
        for holiday_dates in islamic_holidays.values():
            for holiday_date in holiday_dates:
                if date == holiday_date:
                    return True
        return False

    def is_custom_holiday(self, date):
        """
        Check if the given date is a custom holiday

        :param date: The date to check
        :return: True if the date is a custom holiday, false otherwise
        """
        for holiday in self.custom_holidays:
            if holiday['recurring']:
                if date.strftime('%m-%d') == holiday['date'].strftime('%m-%d'):
                    return True
            else:
                if date == holiday['date']:
                    return True

            if holiday['is_islamic']:
                if self.is_islamic_holiday(holiday['date']):
                    return True
        return False

    def calculate_islamic_holidays(self, year):
        """
        Calculate Islamic holidays for a given year

        :param year: The year to calculate the holidays for
        :return: A dictionary of Islamic holidays
        """
        year_difference = year - self.BASE_YEAR

        holidays = {}

        for base_date in self.BASE_DATES:
            start_date = self.calculate_islamic_date(base_date['date'], year_difference)
            holidays[base_date['name']] = self.generate_holiday_dates(start_date, base_date['days'])

        return holidays

    def calculate_islamic_date(self, base_date, year_difference):
        """
        Calculate the date of an Islamic holiday

        :param base_date: The base date of the holiday
        :param year_difference: The difference in years from the base year
        :return: The calculated date of the holiday
        """
        base = datetime.strptime(base_date, '%Y-%m-%d')
        delta = timedelta(days=round(year_difference * self.ISLAMIC_YEAR_DAYS))
        return base + delta

    def generate_holiday_dates(self, start_date, days):
        """
        Generate an array of holiday dates

        :param start_date: The start date of the holiday
        :param days: The number of days the holiday lasts
        :return: A list of holiday dates
        """
        return [start_date + timedelta(days=i) for i in range(days)]
