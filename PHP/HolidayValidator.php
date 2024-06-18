<?php

namespace HolidayChecker;

use DateTime;
use Exception;

/**
 * HolidayValidator
 *
 * This base class is used to determine if a given date is a holiday.
 * It supports fixed holidays, dynamic Islamic holidays, weekends, and custom holidays.
 */
abstract class HolidayValidator
{
    protected const FIXED_HOLIDAYS = [
        '01-01', // Yeni Yıl
        '04-23', // Ulusal Egemenlik ve Çocuk Bayramı
        '05-01', // İşçi Bayramı
        '05-19', // Atatürk'ü Anma, Gençlik ve Spor Bayramı
        '07-20', // Barış ve Özgürlük Bayramı
        '08-30', // Zafer Bayramı
        '10-29', // Cumhuriyet Bayramı
    ];

    protected const BASE_DATES = [
        ['name' => 'ramazanBayrami', 'date' => '2024-04-10', 'days' => 3],
        ['name' => 'kurbanBayrami', 'date' => '2024-06-28', 'days' => 4],
    ];

    private const float ISLAMIC_YEAR_DAYS = 354.36667; // Islamic lunar year in days
    private const int BASE_YEAR = 2024;

    protected bool $includeSaturday;
    protected array $customHolidays = [];

    /**
     * Constructor
     *
     * @param bool $includeSaturday Include Saturdays as holidays
     */
    public function __construct(bool $includeSaturday = false)
    {
        $this->includeSaturday = $includeSaturday;
    }

    /**
     * Check if the given date is a holiday
     *
     * @param DateTime $date The date to check
     * @return bool True if the date is a holiday, false otherwise
     * @throws Exception
     */
    public function isHoliday(DateTime $date): bool
    {
        return $this->isWeekend($date) || $this->isFixedHoliday($date) || $this->isIslamicHoliday($date) || $this->isCustomHoliday($date);
    }

    /**
     * Add a custom holiday
     *
     * @param DateTime $date The date of the custom holiday
     * @param bool $recurring Whether the holiday recurs every year
     * @param bool $isIslamic Whether the holiday should be considered as an Islamic holiday
     */
    public function addCustomHoliday(DateTime $date, bool $recurring = false, bool $isIslamic = false): void
    {
        $this->customHolidays[] = [
            'date'      => $date->format('Y-m-d'),
            'recurring' => $recurring,
            'isIslamic' => $isIslamic,
        ];
    }

    /**
     * Check if the given date is a weekend
     *
     * @param DateTime $date The date to check
     * @return bool True if the date is a weekend, false otherwise
     */
    private function isWeekend(DateTime $date): bool
    {
        $dayOfWeek = $date->format('N');
        return $dayOfWeek == 7 || ($this->includeSaturday && $dayOfWeek == 6);
    }

    /**
     * Check if the given date is a fixed holiday
     *
     * @param DateTime $date The date to check
     * @return bool True if the date is a fixed holiday, false otherwise
     */
    protected function isFixedHoliday(DateTime $date): bool
    {
        return in_array($date->format('m-d'), static::FIXED_HOLIDAYS);
    }

    /**
     * Check if the given date is an Islamic holiday
     *
     * @param DateTime $date The date to check
     * @return bool True if the date is an Islamic holiday, false otherwise
     * @throws Exception
     */
    private function isIslamicHoliday(DateTime $date): bool
    {
        $islamicHolidays = $this->calculateIslamicHolidays($date->format('Y'));
        foreach ($islamicHolidays as $holidayDates) {
            foreach ($holidayDates as $holidayDate) {
                if ($date->format('Y-m-d') == $holidayDate->format('Y-m-d')) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if the given date is a custom holiday
     *
     * @param DateTime $date The date to check
     * @return bool True if the date is a custom holiday, false otherwise
     * @throws Exception
     */
    private function isCustomHoliday(DateTime $date): bool
    {
        foreach ($this->customHolidays as $holiday) {
            if ($holiday['recurring']) {
                if ($date->format('m-d') == (new DateTime($holiday['date']))->format('m-d')) {
                    return true;
                }
            } else {
                if ($date->format('Y-m-d') == $holiday['date']) {
                    return true;
                }
            }

            // Check if the custom holiday is an Islamic holiday
            if ($holiday['isIslamic']) {
                if ($this->isIslamicHoliday(new DateTime($holiday['date']))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Calculate Islamic holidays for a given year
     *
     * @param int $year The year to calculate the holidays for
     * @return array An array of Islamic holidays
     * @throws Exception
     */
    private function calculateIslamicHolidays(int $year): array
    {
        $yearDifference = $year - self::BASE_YEAR;

        $holidays = [];

        foreach (static::BASE_DATES as $baseDate) {
            $startDate = $this->calculateIslamicDate($baseDate['date'], $yearDifference);
            $holidays[$baseDate['name']] = $this->generateHolidayDates($startDate, $baseDate['days']);
        }

        return $holidays;
    }

    /**
     * Calculate the date of an Islamic holiday
     *
     * @param string $baseDate The base date of the holiday
     * @param int $yearDifference The difference in years from the base year
     * @return DateTime The calculated date of the holiday
     * @throws Exception
     */
    private function calculateIslamicDate(string $baseDate, int $yearDifference): DateTime
    {
        $date = new DateTime($baseDate);
        $date->modify('+' . round($yearDifference * self::ISLAMIC_YEAR_DAYS) . ' days');
        return $date;
    }

    /**
     * Generate an array of holiday dates
     *
     * @param DateTime $startDate The start date of the holiday
     * @param int $days The number of days the holiday lasts
     * @return array An array of holiday dates
     */
    private function generateHolidayDates(DateTime $startDate, int $days): array
    {
        $dates = [];
        for ($i = 0; $i < $days; $i++) {
            $date = clone $startDate;
            $date->modify("+$i day");
            $dates[] = $date;
        }
        return $dates;
    }
}
