import Foundation

/// Holiday represents a holiday with a name, date, and duration in days.
struct Holiday {
    var name: String
    var date: Date
    var days: Int
}

/// CustomHoliday represents a user-defined holiday with an option to recur annually and be an Islamic holiday.
struct CustomHoliday {
    var date: Date
    var recurring: Bool
    var isIslamic: Bool
}

/// HolidayValidator
///
/// This base class is used to determine if a given date is a holiday.
/// It supports fixed holidays, dynamic Islamic holidays, weekends, and custom holidays.
class HolidayValidator {
    /// List of fixed holidays in MM-dd format.
    static let fixedHolidays = [
        "01-01", // Yeni Yıl
        "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
        "05-01", // İşçi Bayramı
        "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
        "07-20", // Barış ve Özgürlük Bayramı
        "08-30", // Zafer Bayramı
        "10-29"  // Cumhuriyet Bayramı
    ]

    /// List of base dates for Islamic holidays.
    static let baseDates = [
        Holiday(name: "ramazanBayrami", date: DateComponents(year: 2024, month: 4, day: 10).date!, days: 3),
        Holiday(name: "kurbanBayrami", date: DateComponents(year: 2024, month: 6, day: 28).date!, days: 4)
    ]

    static let islamicYearDays = 354.36667
    static let baseYear = 2024

    var includeSaturday: Bool
    var customHolidays: [CustomHoliday]

    /// Initializes a new HolidayValidator.
    ///
    /// - Parameter includeSaturday: Whether to include Saturdays as holidays.
    init(includeSaturday: Bool = false) {
        self.includeSaturday = includeSaturday
        self.customHolidays = []
    }

    /// Checks if the given date is a holiday.
    ///
    /// - Parameter date: The date to check.
    /// - Returns: `true` if the date is a holiday, `false` otherwise.
    func isHoliday(date: Date) -> Bool {
        return isWeekend(date: date) || isFixedHoliday(date: date) || isIslamicHoliday(date: date) || isCustomHoliday(date: date)
    }

    /// Adds a custom holiday.
    ///
    /// - Parameters:
    ///   - date: The date of the custom holiday.
    ///   - recurring: Whether the holiday recurs every year.
    ///   - isIslamic: Whether the holiday should be considered as an Islamic holiday.
    func addCustomHoliday(date: Date, recurring: Bool = false, isIslamic: Bool = false) {
        customHolidays.append(CustomHoliday(date: date, recurring: recurring, isIslamic: isIslamic))
    }

    /// Checks if the given date is a weekend.
    ///
    /// - Parameter date: The date to check.
    /// - Returns: `true` if the date is a weekend, `false` otherwise.
    private func isWeekend(date: Date) -> Bool {
        let dayOfWeek = Calendar.current.component(.weekday, from: date)
        return dayOfWeek == 1 || (includeSaturday && dayOfWeek == 7)
    }

    /// Checks if the given date is a fixed holiday.
    ///
    /// - Parameter date: The date to check.
    /// - Returns: `true` if the date is a fixed holiday, `false` otherwise.
    private func isFixedHoliday(date: Date) -> Bool {
        let monthDay = DateFormatter.localizedString(from: date, dateStyle: .none, timeStyle: .none)
        return HolidayValidator.fixedHolidays.contains(monthDay)
    }

    /// Checks if the given date is an Islamic holiday.
    ///
    /// - Parameter date: The date to check.
    /// - Returns: `true` if the date is an Islamic holiday, `false` otherwise.
    private func isIslamicHoliday(date: Date) -> Bool {
        let islamicHolidays = calculateIslamicHolidays(year: Calendar.current.component(.year, from: date))
        return islamicHolidays.contains(date)
    }

    /// Checks if the given date is a custom holiday.
    ///
    /// - Parameter date: The date to check.
    /// - Returns: `true` if the date is a custom holiday, `false` otherwise.
    private func isCustomHoliday(date: Date) -> Bool {
        return customHolidays.contains { holiday in
            if holiday.recurring {
                let monthDay = DateFormatter.localizedString(from: date, dateStyle: .none, timeStyle: .none)
                return monthDay == DateFormatter.localizedString(from: holiday.date, dateStyle: .none, timeStyle: .none)
            } else {
                return date == holiday.date
            }
        }
    }

    /// Calculates the Islamic holidays for a given year.
    ///
    /// - Parameter year: The year to calculate the holidays for.
    /// - Returns: An array of dates representing the Islamic holidays.
    private func calculateIslamicHolidays(year: Int) -> [Date] {
        let yearDifference = year - HolidayValidator.baseYear
        return HolidayValidator.baseDates.flatMap { baseDate in
            generateHolidayDates(startDate: calculateIslamicDate(baseDate: baseDate.date, yearDifference: yearDifference), days: baseDate.days)
        }
    }

    /// Calculates the date of an Islamic holiday.
    ///
    /// - Parameters:
    ///   - baseDate: The base date of the holiday.
    ///   - yearDifference: The difference in years from the base year.
    /// - Returns: The calculated date of the holiday.
    private func calculateIslamicDate(baseDate: Date, yearDifference: Int) -> Date {
        let daysToAdd = Int(round(Double(yearDifference) * HolidayValidator.islamicYearDays))
        return Calendar.current.date(byAdding: .day, value: daysToAdd, to: baseDate)!
    }

    /// Generates an array of holiday dates.
    ///
    /// - Parameters:
    ///   - startDate: The start date of the holiday.
    ///   - days: The number of days the holiday lasts.
    /// - Returns: An array of dates representing the holiday.
    private func generateHolidayDates(startDate: Date, days: Int) -> [Date] {
        return (0..<days).map { Calendar.current.date(byAdding: .day, value: $0, to: startDate)! }
    }
}
