package holidaychecker

import (
  "testing"
  "time"
)

func TestTurkeyHolidayValidator(t *testing.T) {
  validator := NewTurkeyHolidayValidator(false)

  if !validator.IsHoliday(time.Date(2024, 4, 23, 0, 0, 0, 0, time.UTC)) {
    t.Errorf("Expected 2024-04-23 to be a holiday")
  }

  if !validator.IsHoliday(time.Date(2024, 4, 10, 0, 0, 0, 0, time.UTC)) {
    t.Errorf("Expected 2024-04-10 to be a holiday")
  }
}
