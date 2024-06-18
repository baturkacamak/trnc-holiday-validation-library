<?php

namespace HolidayChecker;

/**
 * TRNCHolidayValidator
 *
 * This class is used to determine if a given date is a holiday in TRNC.
 */
class TRNCHolidayValidator extends HolidayValidator
{
    protected const array FIXED_HOLIDAYS = [
        ...parent::FIXED_HOLIDAYS,
        '08-01', // TMT Günü
        '11-15', // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    ];

    protected const array BASE_DATES = [
        ...parent::BASE_DATES,
        ['name' => 'mevlidKandili', 'date' => '2024-09-15', 'days' => 1],
    ];
}
