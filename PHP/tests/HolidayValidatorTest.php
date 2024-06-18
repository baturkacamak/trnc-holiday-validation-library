<?php

use PHPUnit\Framework\TestCase;
use HolidayChecker\Base\HolidayValidator;

class HolidayValidatorTest extends TestCase
{
    public function testIsWeekend()
    {
        $validator = new class extends HolidayValidator {
            public function isWeekendPublic(DateTime $date): bool
            {
                return $this->isWeekend($date);
            }
        };

        $sunday = new DateTime('2024-04-21');
        $saturday = new DateTime('2024-04-20');
        $weekday = new DateTime('2024-04-22');

        $this->assertTrue($validator->isWeekendPublic($sunday));
        $this->assertFalse($validator->isWeekendPublic($saturday));
        $this->assertFalse($validator->isWeekendPublic($weekday));

        $validatorWithSaturday = new class(true) extends HolidayValidator {
            public function isWeekendPublic(DateTime $date): bool
            {
                return $this->isWeekend($date);
            }
        };

        $this->assertTrue($validatorWithSaturday->isWeekendPublic($saturday));
    }

    // Add more tests for other methods if necessary
}
