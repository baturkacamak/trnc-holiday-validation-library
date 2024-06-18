require 'minitest/autorun'
require_relative '../HolidayValidator'

class HolidayValidatorTest < Minitest::Test
  def setup
    @validator = HolidayValidator.new
  end

  def test_fixed_holidays
    assert @validator.holiday?(Date.new(2024, 4, 23))
  end

  def test_islamic_holidays
    assert @validator.holiday?(Date.new(2024, 4, 10))
  end
end
