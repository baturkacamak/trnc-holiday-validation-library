<?php

use PHPUnit\Framework\TestCase;
use HolidayChecker\TRNC\TRNCHolidayValidator;

class TRNCHolidayValidatorTest extends TestCase
{
    public function testIsHoliday()
    {
        $validator = new TRNCHolidayValidator();

        $fixedHoliday = new DateTime('2024-04-23');
        $nonHoliday = new DateTime('2024-04-24');

        $this->assertTrue($validator->isHoliday($fixedHoliday));
        $this->assertFalse($validator->isHoliday($nonHoliday));
    }

    public function testCustomHoliday()
    {
        $validator = new TRNCHolidayValidator();
        $customHoliday = new DateTime('2024-12-25');

        $validator->addCustomHoliday($customHoliday);
        $this->assertTrue($validator->isHoliday($customHoliday));
    }

    public function testIslamicHoliday()
    {
        $validator = new TRNCHolidayValidator();
        $islamicHoliday = new DateTime('2024-04-10'); // Assume this is an Islamic holiday

        $this->assertTrue($validator->isHoliday($islamicHoliday));
    }
}
