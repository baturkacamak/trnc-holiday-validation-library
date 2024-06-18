class TRNCHolidayValidator < HolidayValidator
  # Additional fixed holidays specific to TRNC
  FIXED_HOLIDAYS += [
    '08-01', # TMT Günü
    '11-15'  # Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
  ]

  # Additional base dates specific to TRNC
  BASE_DATES += [
    { name: 'mevlidKandili', date: Date.new(2024, 9, 15), days: 1 }
  ]

  # TRNCHolidayValidator
  #
  # This class extends HolidayValidator to determine if a given date is a holiday in TRNC.
  #
  # The additional holidays include specific fixed and Islamic holidays for TRNC.
end
