use crate::holiday_validator::HolidayValidator;
use chrono::NaiveDate;

#[test]
fn test_fixed_holidays() {
    let validator = HolidayValidator::new(false);
    assert!(validator.is_holiday(NaiveDate::from_ymd(2024, 4, 23)));
}

#[test]
fn test_islamic_holidays() {
    let validator = HolidayValidator::new(false);
    assert!(validator.is_holiday(NaiveDate::from_ymd(2024, 4, 10)));
}
