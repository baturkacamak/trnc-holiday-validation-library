package holidaychecker

import (
  "testing"
  "time"
)

func TestTRNCHolidayValidator(t *testing.T) {
  validator := NewTRNCHolidayValidator(false)

  if !validator.IsHoliday(time.Date(2024, 8, 1, 0, 0, 0, 0, time.UTC)) {
    t.Errorf("Expected 2024-08-01 to be a holiday")
  }

  if !validator.IsHoliday(time.Date(2024, 9, 15, 0, 0, 0, 0, time.UTC)) {
    t.Errorf("Expected 2024-09-15 to be a holiday")
  }
}
