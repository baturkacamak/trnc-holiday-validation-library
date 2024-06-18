package holidaychecker

import (
  "time"
)

var (
  FixedHolidays = []string{
    "01-01", // Yeni Yıl
    "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
    "05-01", // İşçi Bayramı
    "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
    "07-20", // Barış ve Özgürlük Bayramı
    "08-30", // Zafer Bayramı
    "10-29", // Cumhuriyet Bayramı
  }
  BaseDates = []Holiday{
    {"ramazanBayrami", time.Date(2024, 4, 10, 0, 0, 0, 0, time.UTC), 3},
    {"kurbanBayrami", time.Date(2024, 6, 28, 0, 0, 0, 0, time.UTC), 4},
  }
  IslamicYearDays = 354.36667
  BaseYear        = 2024
)

// Holiday represents a holiday with a name, date, and duration in days
type Holiday struct {
  Name string
  Date time.Time
  Days int
}

// CustomHoliday represents a user-defined holiday with additional options for recurrence and Islamic holiday check
type CustomHoliday struct {
  Date      time.Time
  Recurring bool
  IsIslamic bool
}

// HolidayValidator provides methods to check if a given date is a holiday
type HolidayValidator struct {
  IncludeSaturday bool
  CustomHolidays  []CustomHoliday
}

// NewHolidayValidator creates a new HolidayValidator with the option to include Saturdays as holidays
func NewHolidayValidator(includeSaturday bool) *HolidayValidator {
  return &HolidayValidator{IncludeSaturday: includeSaturday}
}

// IsHoliday checks if the given date is a holiday
func (hv *HolidayValidator) IsHoliday(date time.Time) bool {
  return hv.isWeekend(date) || hv.isFixedHoliday(date) || hv.isIslamicHoliday(date) || hv.isCustomHoliday(date)
}

// AddCustomHoliday adds a custom holiday to the list of holidays
func (hv *HolidayValidator) AddCustomHoliday(date time.Time, recurring bool, isIslamic bool) {
  hv.CustomHolidays = append(hv.CustomHolidays, CustomHoliday{Date: date, Recurring: recurring, IsIslamic: isIslamic})
}

// isWeekend checks if the given date is a weekend
func (hv *HolidayValidator) isWeekend(date time.Time) bool {
  dayOfWeek := date.Weekday()
  return dayOfWeek == time.Sunday || (hv.IncludeSaturday && dayOfWeek == time.Saturday)
}

// isFixedHoliday checks if the given date is a fixed holiday
func (hv *HolidayValidator) isFixedHoliday(date time.Time) bool {
  monthDay := date.Format("01-02")
  for _, holiday := range FixedHolidays {
    if holiday == monthDay {
      return true
    }
  }
  return false
}

// isIslamicHoliday checks if the given date is an Islamic holiday
func (hv *HolidayValidator) isIslamicHoliday(date time.Time) bool {
  islamicHolidays := hv.calculateIslamicHolidays(date.Year())
  for _, holiday := range islamicHolidays {
    if holiday.Equal(date) {
      return true
    }
  }
  return false
}

// isCustomHoliday checks if the given date is a custom holiday
func (hv *HolidayValidator) isCustomHoliday(date time.Time) bool {
  for _, holiday := range hv.CustomHolidays {
    if holiday.Recurring {
      if date.Format("01-02") == holiday.Date.Format("01-02") {
        return true
      }
    } else {
      if date.Equal(holiday.Date) {
        return true
      }
    }
  }
  return false
}

// calculateIslamicHolidays calculates the Islamic holidays for a given year
func (hv *HolidayValidator) calculateIslamicHolidays(year int) []time.Time {
  yearDifference := year - BaseYear
  var holidays []time.Time
  for _, baseDate := range BaseDates {
    startDate := hv.calculateIslamicDate(baseDate.Date, yearDifference)
    holidays = append(holidays, hv.generateHolidayDates(startDate, baseDate.Days)...)
  }
  return holidays
}

// calculateIslamicDate calculates the date of an Islamic holiday based on the year difference
func (hv *HolidayValidator) calculateIslamicDate(baseDate time.Time, yearDifference int) time.Time {
  daysToAdd := int(yearDifference * int(IslamicYearDays))
  return baseDate.AddDate(0, 0, daysToAdd)
}

// generateHolidayDates generates an array of holiday dates starting from a given date
func (hv *HolidayValidator) generateHolidayDates(startDate time.Time, days int) []time.Time {
  var dates []time.Time
  for i := 0; i < days; i++ {
    dates = append(dates, startDate.AddDate(0, 0, i))
  }
  return dates
}
