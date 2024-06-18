package holidaychecker

// TurkeyHolidayValidator provides methods to check if a given date is a holiday in Turkey
type TurkeyHolidayValidator struct {
  *HolidayValidator
}

// NewTurkeyHolidayValidator creates a new TurkeyHolidayValidator with the option to include Saturdays as holidays
func NewTurkeyHolidayValidator(includeSaturday bool) *TurkeyHolidayValidator {
  return &TurkeyHolidayValidator{NewHolidayValidator(includeSaturday)}
}
