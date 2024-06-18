require 'minitest/autorun'
require_relative '../TRNCHolidayValidator'

class TRNCHolidayValidatorTest < Minitest::Test
  def setup
    @validator = TRNCHolidayValidator.new
  end

  def test_trnc_fixed_holidays
    assert @validator.holiday?(Date.new(2024, 8, 1))
  end

  def test_trnc_islamic_holidays
    assert @validator.holiday?(Date.new(2024, 9, 15))
  end
end
