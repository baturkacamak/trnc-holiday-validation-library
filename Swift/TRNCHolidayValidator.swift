import Foundation

/// TRNCHolidayValidator
///
/// This class is used to determine if a given date is a holiday in TRNC.
class TRNCHolidayValidator: HolidayValidator {
    /// Additional fixed holidays specific to TRNC.
    static let trncFixedHolidays = fixedHolidays + [
        "08-01", // TMT Günü
        "11-15"  // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    ]

    /// Additional base dates for Islamic holidays specific to TRNC.
    static let trncBaseDates = baseDates + [
        Holiday(name: "mevlidKandili", date: DateComponents(year: 2024, month: 9, day: 15).date!, days: 1)
    ]

    /// Checks if the given date is a fixed holiday.
    ///
    /// - Parameter date: The date to check.
    /// - Returns: `true` if the date is a fixed holiday, `false` otherwise.
    override func isFixedHoliday(date: Date) -> Bool {
        let monthDay = DateFormatter.localizedString(from: date, dateStyle: .none, timeStyle: .none)
        return super.isFixedHoliday(date: date) || TRNCHolidayValidator.trncFixedHolidays.contains(monthDay)
    }
}
