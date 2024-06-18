class HolidayValidator
  # List of fixed holidays
  FIXED_HOLIDAYS = [
    '01-01', # Yeni Yıl
    '04-23', # Ulusal Egemenlik ve Çocuk Bayramı
    '05-01', # İşçi Bayramı
    '05-19', # Atatürk'ü Anma, Gençlik ve Spor Bayramı
    '07-20', # Barış ve Özgürlük Bayramı
    '08-30', # Zafer Bayramı
    '10-29'  # Cumhuriyet Bayramı
  ]

  # List of base dates for Islamic holidays
  BASE_DATES = [
    { name: 'ramazanBayrami', date: Date.new(2024, 4, 10), days: 3 },
    { name: 'kurbanBayrami', date: Date.new(2024, 6, 28), days: 4 }
  ]

  ISLAMIC_YEAR_DAYS = 354.36667
  BASE_YEAR = 2024

  # Initializes the HolidayValidator class
  #
  # @param [Boolean] include_saturday Include Saturdays as holidays
  def initialize(include_saturday = false)
    @include_saturday = include_saturday
    @custom_holidays = []
  end

  # Checks if a given date is a holiday
  #
  # @param [Date] date The date to check
  # @return [Boolean] True if the date is a holiday, false otherwise
  def holiday?(date)
    weekend?(date) || fixed_holiday?(date) || islamic_holiday?(date) || custom_holiday?(date)
  end

  # Adds a custom holiday
  #
  # @param [Date] date The date of the custom holiday
  # @param [Boolean] recurring Whether the holiday recurs every year
  # @param [Boolean] islamic Whether the holiday should be considered an Islamic holiday
  def add_custom_holiday(date, recurring: false, islamic: false)
    @custom_holidays << { date: date, recurring: recurring, islamic: islamic }
  end

  private

  # Checks if a given date is a weekend
  #
  # @param [Date] date The date to check
  # @return [Boolean] True if the date is a weekend, false otherwise
  def weekend?(date)
    day_of_week = date.wday
    day_of_week == 0 || (@include_saturday && day_of_week == 6)
  end

  # Checks if a given date is a fixed holiday
  #
  # @param [Date] date The date to check
  # @return [Boolean] True if the date is a fixed holiday, false otherwise
  def fixed_holiday?(date)
    FIXED_HOLIDAYS.include?(date.strftime('%m-%d'))
  end

  # Checks if a given date is an Islamic holiday
  #
  # @param [Date] date The date to check
  # @return [Boolean] True if the date is an Islamic holiday, false otherwise
  def islamic_holiday?(date)
    islamic_holidays = calculate_islamic_holidays(date.year)
    islamic_holidays.include?(date)
  end

  # Checks if a given date is a custom holiday
  #
  # @param [Date] date The date to check
  # @return [Boolean] True if the date is a custom holiday, false otherwise
  def custom_holiday?(date)
    @custom_holidays.any? do |holiday|
      if holiday[:recurring]
        date.strftime('%m-%d') == holiday[:date].strftime('%m-%d')
      else
        date == holiday[:date]
      end
    end
  end

  # Calculates the Islamic holidays for a given year
  #
  # @param [Integer] year The year to calculate the holidays for
  # @return [Array<Date>] An array of Islamic holiday dates
  def calculate_islamic_holidays(year)
    year_difference = year - BASE_YEAR

    BASE_DATES.flat_map do |base_date|
      start_date = calculate_islamic_date(base_date[:date], year_difference)
      generate_holiday_dates(start_date, base_date[:days])
    end
  end

  # Calculates the date of an Islamic holiday
  #
  # @param [Date] base_date The base date of the holiday
  # @param [Integer] year_difference The difference in years from the base year
  # @return [Date] The calculated date of the holiday
  def calculate_islamic_date(base_date, year_difference)
    days_to_add = (year_difference * ISLAMIC_YEAR_DAYS).round
    base_date + days_to_add
  end

  # Generates an array of holiday dates
  #
  # @param [Date] start_date The start date of the holiday
  # @param [Integer] days The number of days the holiday lasts
  # @return [Array<Date>] An array of holiday dates
  def generate_holiday_dates(start_date, days)
    (0...days).map { |i| start_date + i }
  end
end
