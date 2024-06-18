import XCTest
@testable import YourModuleName

class TurkeyHolidayValidatorTests: XCTestCase {
  var validator: TurkeyHolidayValidator!

  override func setUp() {
    super.setUp()
    validator = TurkeyHolidayValidator()
  }

  func testTurkeyFixedHolidays() {
    XCTAssertTrue(validator.isHoliday(date: DateComponents(year: 2024, month: 4, day: 23).date!))
  }

  func testTurkeyIslamicHolidays() {
    XCTAssertTrue(validator.isHoliday(date: DateComponents(year: 2024, month: 4, day: 10).date!))
  }
}
