use crate::trnc_holiday_validator::TRNCHolidayValidator;
use chrono::NaiveDate;

#[test]
fn test_trnc_fixed_holidays() {
    let validator = TRNCHolidayValidator::new(false);
    assert!(validator.is_holiday(NaiveDate::from_ymd(2024, 8, 1)));
}

#[test]
fn test_trnc_islamic_holidays() {
    let validator = TRNCHolidayValidator::new(false);
    assert!(validator.is_holiday(NaiveDate::from_ymd(2024, 9, 15)));
}
