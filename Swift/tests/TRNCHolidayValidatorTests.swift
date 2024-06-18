import XCTest
@testable import YourModuleName

class TRNCHolidayValidatorTests: XCTestCase {
  var validator: TRNCHolidayValidator!

  override func setUp() {
    super.setUp()
    validator = TRNCHolidayValidator()
  }

  func testTRNCFixedHolidays() {
    XCTAssertTrue(validator.isHoliday(date: DateComponents(year: 2024, month: 8, day: 1).date!))
  }

  func testTRNCIslamicHolidays() {
    XCTAssertTrue(validator.isHoliday(date: DateComponents(year: 2024, month: 9, day: 15).date!))
  }
}
