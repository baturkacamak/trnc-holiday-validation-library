package holidaychecker

import "time"

var (
  // TrncFixedHolidays includes both the common fixed holidays and specific holidays for TRNC
  TrncFixedHolidays = append(FixedHolidays, "08-01", "11-15")

  // TrncBaseDates includes both the common Islamic holidays and specific holidays for TRNC
  TrncBaseDates = append(BaseDates, Holiday{"mevlidKandili", time.Date(2024, 9, 15, 0, 0, 0, 0, time.UTC), 1})
)

// TRNCHolidayValidator provides methods to check if a given date is a holiday in TRNC
type TRNCHolidayValidator struct {
  *HolidayValidator
}

// NewTRNCHolidayValidator creates a new TRNCHolidayValidator with the option to include Saturdays as holidays
func NewTRNCHolidayValidator(includeSaturday bool) *TRNCHolidayValidator {
  return &TRNCHolidayValidator{NewHolidayValidator(includeSaturday)}
}

// isFixedHoliday checks if the given date is a fixed holiday in TRNC
// It overrides the method from HolidayValidator to include TRNC-specific holidays
func (hv *TRNCHolidayValidator) isFixedHoliday(date time.Time) bool {
  monthDay := date.Format("01-02")
  for _, holiday := range TrncFixedHolidays {
    if holiday == monthDay {
      return true
    }
  }
  return hv.HolidayValidator.isFixedHoliday(date)
}
