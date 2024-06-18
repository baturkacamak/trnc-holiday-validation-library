import XCTest
@testable import YourModuleName

class HolidayValidatorTests: XCTestCase {
  var validator: HolidayValidator!

  override func setUp() {
    super.setUp()
    validator = HolidayValidator()
  }

  func testFixedHolidays() {
    XCTAssertTrue(validator.isHoliday(date: DateComponents(year: 2024, month: 4, day: 23).date!))
  }

  func testIslamicHolidays() {
    XCTAssertTrue(validator.isHoliday(date: DateComponents(year: 2024, month: 4, day: 10).date!))
  }
}
