<?php

// Unit Tests
namespace PHP;
require 'TRNCHolidayValidator.php';

use PHPUnit\Framework\TestCase;

class TRNCHolidayValidatorTest extends TestCase
{
    public function testFixedHoliday()
    {
        $validator = new TRNCHolidayValidator();
        $date      = new DateTime('2024-01-01');
        $this->assertTrue($validator->isHoliday($date));
    }

    public function testWeekendHoliday()
    {
        $validator = new TRNCHolidayValidator();
        $date      = new DateTime('2024-07-07'); // Sunday
        $this->assertTrue($validator->isHoliday($date));
    }

    public function testCustomHoliday()
    {
        $validator = new TRNCHolidayValidator();
        $date      = new DateTime('2024-12-25');
        $validator->addCustomHoliday($date);
        $this->assertTrue($validator->isHoliday($date));
    }

    public function testRecurringCustomHoliday()
    {
        $validator = new TRNCHolidayValidator();
        $date      = new DateTime('2024-12-25');
        $validator->addCustomHoliday($date, true);
        $dateNextYear = new DateTime('2025-12-25');
        $this->assertTrue($validator->isHoliday($dateNextYear));
    }

    public function testIslamicHoliday()
    {
        $validator = new TRNCHolidayValidator();
        $date      = new DateTime('2024-04-10'); // Ramazan Bayramı
        $this->assertTrue($validator->isHoliday($date));
    }
}
