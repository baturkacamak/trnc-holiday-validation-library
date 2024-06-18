use crate::turkey_holiday_validator::TurkeyHolidayValidator;
use chrono::NaiveDate;

#[test]
fn test_turkey_fixed_holidays() {
    let validator = TurkeyHolidayValidator::new(false);
    assert!(validator.is_holiday(NaiveDate::from_ymd(2024, 4, 23)));
}

#[test]
fn test_turkey_islamic_holidays() {
    let validator = TurkeyHolidayValidator::new(false);
    assert!(validator.is_holiday(NaiveDate::from_ymd(2024, 4, 10)));
}
