from datetime import datetime, timedelta

class TRNCHolidayValidator:
    FIXED_HOLIDAYS = [
        '01-01',  # Yeni Yıl
        '04-23',  # Ulusal Egemenlik ve Çocuk Bayramı
        '05-01',  # İşçi Bayramı
        '05-19',  # Atatürk'ü Anma, Gençlik ve Spor Bayramı
        '07-20',  # Barış ve Özgürlük Bayramı
        '08-01',  # TMT Günü
        '08-30',  # Zafer Bayramı
        '10-29',  # Cumhuriyet Bayramı
        '11-15',  # Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    ]

    BASE_DATES = [
        { 'name': 'ramazanBayrami', 'date': '2024-04-10', 'days': 3 },
        { 'name': 'kurbanBayrami', 'date': '2024-06-28', 'days': 4 },
        { 'name': 'mevlidKandili', 'date': '2024-09-15', 'days': 1 }
    ]

    ISLAMIC_YEAR_DAYS = 354.36667
    BASE_YEAR = 2024

    def __init__(self, include_saturday=False):
        self.include_saturday = include_saturday
        self.custom_holidays = []

    def is_holiday(self, date):
        return self.is_weekend(date) or self.is_fixed_holiday(date) or self.is_islamic_holiday(date) or self.is_custom_holiday(date)

    def add_custom_holiday(self, date):
        self.custom_holidays.append(date)

    def is_weekend(self, date):
        day_of_week = date.weekday()  # Monday is 0 and Sunday is 6
        return day_of_week == 6 or (self.include_saturday and day_of_week == 5)

    def is_fixed_holiday(self, date):
        month_day = date.strftime('%m-%d')
        return month_day in self.FIXED_HOLIDAYS

    def is_islamic_holiday(self, date):
        year = date.year
        islamic_holidays = self.calculate_islamic_holidays(year)
        for holiday_dates in islamic_holidays.values():
            for holiday_date in holiday_dates:
                if date == holiday_date:
                    return True
        return False

    def is_custom_holiday(self, date):
        return date in self.custom_holidays

    def calculate_islamic_holidays(self, year):
        year_difference = year - self.BASE_YEAR

        holidays = {}

        for base_date in self.BASE_DATES:
            start_date = self.calculate_islamic_date(base_date['date'], year_difference)
            holidays[base_date['name']] = self.generate_holiday_dates(start_date, base_date['days'])

        return holidays

    def calculate_islamic_date(self, base_date, year_difference):
        base = datetime.strptime(base_date, '%Y-%m-%d')
        delta = timedelta(days=round(year_difference * self.ISLAMIC_YEAR_DAYS))
        return base + delta

    def generate_holiday_dates(self, start_date, days):
        return [start_date + timedelta(days=i) for i in range(days)]
