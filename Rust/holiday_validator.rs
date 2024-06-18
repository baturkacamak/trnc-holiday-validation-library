use chrono::{Datelike, NaiveDate};

/// A list of fixed holidays.
static FIXED_HOLIDAYS: &[&str] = &[
    "01-01", // Yeni Yıl
    "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
    "05-01", // İşçi Bayramı
    "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
    "07-20", // Barış ve Özgürlük Bayramı
    "08-30", // Zafer Bayramı
    "10-29", // Cumhuriyet Bayramı
];

/// A list of base Islamic holidays.
static BASE_DATES: &[Holiday] = &[
    Holiday::new("ramazanBayrami", NaiveDate::from_ymd(2024, 4, 10), 3),
    Holiday::new("kurbanBayrami", NaiveDate::from_ymd(2024, 6, 28), 4),
];

const ISLAMIC_YEAR_DAYS: f64 = 354.36667;
const BASE_YEAR: i32 = 2024;

/// Represents a holiday with a name, date, and duration in days.
pub struct Holiday {
    pub name: &'static str,
    pub date: NaiveDate,
    pub days: u32,
}

impl Holiday {
    /// Creates a new Holiday instance.
    pub const fn new(name: &'static str, date: NaiveDate, days: u32) -> Self {
        Self { name, date, days }
    }
}

/// Represents a custom holiday with a date, recurrence flag, and Islamic flag.
pub struct CustomHoliday {
    pub date: NaiveDate,
    pub recurring: bool,
    pub is_islamic: bool,
}

/// Base class for validating holidays.
pub struct HolidayValidator {
    include_saturday: bool,
    custom_holidays: Vec<CustomHoliday>,
}

impl HolidayValidator {
    /// Creates a new HolidayValidator instance.
    ///
    /// # Arguments
    ///
    /// * `include_saturday` - A boolean indicating whether Saturdays should be considered holidays.
    pub fn new(include_saturday: bool) -> Self {
        Self {
            include_saturday,
            custom_holidays: Vec::new(),
        }
    }

    /// Checks if a given date is a holiday.
    ///
    /// # Arguments
    ///
    /// * `date` - The date to check.
    ///
    /// # Returns
    ///
    /// * `true` if the date is a holiday, `false` otherwise.
    pub fn is_holiday(&self, date: NaiveDate) -> bool {
        self.is_weekend(date)
            || self.is_fixed_holiday(date)
            || self.is_islamic_holiday(date)
            || self.is_custom_holiday(date)
    }

    /// Adds a custom holiday.
    ///
    /// # Arguments
    ///
    /// * `date` - The date of the custom holiday.
    /// * `recurring` - Whether the holiday recurs every year.
    /// * `is_islamic` - Whether the holiday should be considered as an Islamic holiday.
    pub fn add_custom_holiday(&mut self, date: NaiveDate, recurring: bool, is_islamic: bool) {
        self.custom_holidays.push(CustomHoliday {
            date,
            recurring,
            is_islamic,
        });
    }

    /// Checks if a given date is a weekend.
    ///
    /// # Arguments
    ///
    /// * `date` - The date to check.
    ///
    /// # Returns
    ///
    /// * `true` if the date is a weekend, `false` otherwise.
    fn is_weekend(&self, date: NaiveDate) -> bool {
        let day_of_week = date.weekday().number_from_monday();
        day_of_week == 7 || (self.include_saturday && day_of_week == 6)
    }

    /// Checks if a given date is a fixed holiday.
    ///
    /// # Arguments
    ///
    /// * `date` - The date to check.
    ///
    /// # Returns
    ///
    /// * `true` if the date is a fixed holiday, `false` otherwise.
    fn is_fixed_holiday(&self, date: NaiveDate) -> bool {
        let month_day = format!("{:02}-{:02}", date.month(), date.day());
        FIXED_HOLIDAYS.contains(&month_day.as_str())
    }

    /// Checks if a given date is an Islamic holiday.
    ///
    /// # Arguments
    ///
    /// * `date` - The date to check.
    ///
    /// # Returns
    ///
    /// * `true` if the date is an Islamic holiday, `false` otherwise.
    fn is_islamic_holiday(&self, date: NaiveDate) -> bool {
        let islamic_holidays = self.calculate_islamic_holidays(date.year());
        islamic_holidays.contains(&date)
    }

    /// Checks if a given date is a custom holiday.
    ///
    /// # Arguments
    ///
    /// * `date` - The date to check.
    ///
    /// # Returns
    ///
    /// * `true` if the date is a custom holiday, `false` otherwise.
    fn is_custom_holiday(&self, date: NaiveDate) -> bool {
        for holiday in &self.custom_holidays {
            if holiday.recurring {
                if format!("{:02}-{:02}", date.month(), date.day())
                    == format!("{:02}-{:02}", holiday.date.month(), holiday.date.day())
                {
                    return true;
                }
            } else if date == holiday.date {
                return true;
            }

            if holiday.is_islamic && self.is_islamic_holiday(holiday.date) {
                return true;
            }
        }
        false
    }

    /// Calculates Islamic holidays for a given year.
    ///
    /// # Arguments
    ///
    /// * `year` - The year to calculate the holidays for.
    ///
    /// # Returns
    ///
    /// * A vector of Islamic holiday dates.
    fn calculate_islamic_holidays(&self, year: i32) -> Vec<NaiveDate> {
        let year_difference = year - BASE_YEAR;
        BASE_DATES
            .iter()
            .flat_map(|base_date| {
                self.generate_holiday_dates(
                    self.calculate_islamic_date(base_date.date, year_difference),
                    base_date.days,
                )
            })
            .collect()
    }

    /// Calculates the date of an Islamic holiday.
    ///
    /// # Arguments
    ///
    /// * `base_date` - The base date of the holiday.
    /// * `year_difference` - The difference in years from the base year.
    ///
    /// # Returns
    ///
    /// * The calculated date of the holiday.
    fn calculate_islamic_date(&self, base_date: NaiveDate, year_difference: i32) -> NaiveDate {
        base_date + chrono::Duration::days((year_difference as f64 * ISLAMIC_YEAR_DAYS).round() as i64)
    }

    /// Generates an array of holiday dates.
    ///
    /// # Arguments
    ///
    /// * `start_date` - The start date of the holiday.
    /// * `days` - The number of days the holiday lasts.
    ///
    /// # Returns
    ///
    /// * A vector of holiday dates.
    fn generate_holiday_dates(&self, start_date: NaiveDate, days: u32) -> Vec<NaiveDate> {
        (0..days).map(|i| start_date + chrono::Duration::days(i as i64)).collect()
    }
}
