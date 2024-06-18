<?php

use PHPUnit\Framework\TestCase;
use HolidayChecker\Turkey\TurkeyHolidayValidator;

class TurkeyHolidayValidatorTest extends TestCase
{
    public function testIsHoliday()
    {
        $validator = new TurkeyHolidayValidator();

        $fixedHoliday = new DateTime('2024-04-23');
        $nonHoliday = new DateTime('2024-04-24');

        $this->assertTrue($validator->isHoliday($fixedHoliday));
        $this->assertFalse($validator->isHoliday($nonHoliday));
    }

    public function testCustomHoliday()
    {
        $validator = new TurkeyHolidayValidator();
        $customHoliday = new DateTime('2024-12-25');

        $validator->addCustomHoliday($customHoliday);
        $this->assertTrue($validator->isHoliday($customHoliday));
    }
}
