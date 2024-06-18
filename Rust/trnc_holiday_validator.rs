use crate::holiday_validator::{Holiday, HolidayValidator, BASE_DATES, FIXED_HOLIDAYS};
use chrono::NaiveDate;

/// Additional fixed holidays specific to TRNC.
static TRNC_FIXED_HOLIDAYS: &[&str] = &["08-01", "11-15"];

/// Additional base dates for Islamic holidays specific to TRNC.
static TRNC_BASE_DATES: &[Holiday] = &[
    Holiday::new("mevlidKandili", NaiveDate::from_ymd(2024, 9, 15), 1)
];

/// TRNCHolidayValidator
///
/// This class is used to determine if a given date is a holiday in TRNC.
/// It extends the base functionality provided by `HolidayValidator` to include
/// holidays specific to TRNC.
pub struct TRNCHolidayValidator {
    base_validator: HolidayValidator,
}

impl TRNCHolidayValidator {
    /// Creates a new TRNCHolidayValidator instance.
    ///
    /// # Arguments
    ///
    /// * `include_saturday` - A boolean indicating whether Saturdays should be considered holidays.
    ///
    /// # Returns
    ///
    /// * A new instance of TRNCHolidayValidator.
    pub fn new(include_saturday: bool) -> Self {
        Self {
            base_validator: HolidayValidator::new(include_saturday),
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
        self.base_validator.is_holiday(date) || self.is_trnc_fixed_holiday(date)
    }

    /// Checks if a given date is a fixed holiday specific to TRNC.
    ///
    /// # Arguments
    ///
    /// * `date` - The date to check.
    ///
    /// # Returns
    ///
    /// * `true` if the date is a TRNC-specific fixed holiday, `false` otherwise.
    fn is_trnc_fixed_holiday(&self, date: NaiveDate) -> bool {
        let month_day = format!("{:02}-{:02}", date.month(), date.day());
        TRNC_FIXED_HOLIDAYS.contains(&month_day.as_str())
    }
}
