use crate::holiday_validator::HolidayValidator;

pub struct TurkeyHolidayValidator {
    base_validator: HolidayValidator,
}

impl TurkeyHolidayValidator {
    pub fn new(include_saturday: bool) -> Self {
        Self {
            base_validator: HolidayValidator::new(include_saturday),
        }
    }

    pub fn is_holiday(&self, date: chrono::NaiveDate) -> bool {
        self.base_validator.is_holiday(date)
    }
}
