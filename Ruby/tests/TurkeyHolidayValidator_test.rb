require 'minitest/autorun'
require_relative '../TurkeyHolidayValidator'

class TurkeyHolidayValidatorTest < Minitest::Test
  def setup
    @validator = TurkeyHolidayValidator.new
  end

  def test_turkey_fixed_holidays
    assert @validator.holiday?(Date.new(2024, 4, 23))
  end

  def test_turkey_islamic_holidays
    assert @validator.holiday?(Date.new(2024, 4, 10))
  end
end
