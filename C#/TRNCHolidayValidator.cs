using System;
using System.Collections.Generic;
using NUnit.Framework;

public class TRNCHolidayValidator
{
    private static readonly List<string> FIXED_HOLIDAYS = new List<string>
    {
        "01-01", // Yeni Yıl
        "04-23", // Ulusal Egemenlik ve Çocuk Bayramı
        "05-01", // İşçi Bayramı
        "05-19", // Atatürk'ü Anma, Gençlik ve Spor Bayramı
        "07-20", // Barış ve Özgürlük Bayramı
        "08-01", // TMT Günü
        "08-30", // Zafer Bayramı
        "10-29", // Cumhuriyet Bayramı
        "11-15"  // Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı
    };

    private static readonly List<Holiday> BASE_DATES = new List<Holiday>
    {
        new Holiday("ramazanBayrami", new DateTime(2024, 4, 10), 3),
        new Holiday("kurbanBayrami", new DateTime(2024, 6, 28), 4),
        new Holiday("mevlidKandili", new DateTime(2024, 9, 15), 1)
    };

    private static readonly double ISLAMIC_YEAR_DAYS = 354.36667;
    private static readonly int BASE_YEAR = 2024;

    private readonly bool includeSaturday;
    private readonly List<CustomHoliday> customHolidays = new List<CustomHoliday>();

    public TRNCHolidayValidator(bool includeSaturday)
    {
        this.includeSaturday = includeSaturday;
    }

    public bool IsHoliday(DateTime date)
    {
        return IsWeekend(date) || IsFixedHoliday(date) || IsIslamicHoliday(date) || IsCustomHoliday(date);
    }

    public void AddCustomHoliday(DateTime date, bool recurring = false, bool isIslamic = false)
    {
        customHolidays.Add(new CustomHoliday(date, recurring, isIslamic));
    }

    private bool IsWeekend(DateTime date)
    {
        int dayOfWeek = (int)date.DayOfWeek;
        return dayOfWeek == 0 || (includeSaturday && dayOfWeek == 6);
    }

    private bool IsFixedHoliday(DateTime date)
    {
        string monthDay = date.ToString("MM-dd");
        return FIXED_HOLIDAYS.Contains(monthDay);
    }

    private bool IsIslamicHoliday(DateTime date)
    {
        List<DateTime> islamicHolidays = CalculateIslamicHolidays(date.Year);
        return islamicHolidays.Contains(date);
    }

    private bool IsCustomHoliday(DateTime date)
    {
        foreach (var holiday in customHolidays)
        {
            if (holiday.Recurring)
            {
                if (date.ToString("MM-dd") == holiday.Date.ToString("MM-dd"))
                {
                    return true;
                }
            }
            else
            {
                if (date.Date == holiday.Date.Date)
                {
                    return true;
                }
            }

            // Check if the custom holiday is an Islamic holiday
            if (holiday.IsIslamic)
            {
                if (IsIslamicHoliday(holiday.Date))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private List<DateTime> CalculateIslamicHolidays(int year)
    {
        int yearDifference = year - BASE_YEAR;

        List<DateTime> holidays = new List<DateTime>();

        foreach (var holiday in BASE_DATES)
        {
            DateTime startDate = CalculateIslamicDate(holiday.Date, yearDifference);
            holidays.AddRange(GenerateHolidayDates(startDate, holiday.Days));
        }

        return holidays;
    }

    private DateTime CalculateIslamicDate(DateTime baseDate, int yearDifference)
    {
        int daysToAdd = (int)Math.Round(yearDifference * ISLAMIC_YEAR_DAYS);
        return baseDate.AddDays(daysToAdd);
    }

    private List<DateTime> GenerateHolidayDates(DateTime startDate, int days)
    {
        List<DateTime> dates = new List<DateTime>();
        for (int i = 0; i < days; i++)
        {
            dates.Add(startDate.AddDays(i));
        }
        return dates;
    }

    private class Holiday
    {
        public string Name { get; }
        public DateTime Date { get; }
        public int Days { get; }

        public Holiday(string name, DateTime date, int days)
        {
            Name = name;
            Date = date;
            Days = days;
        }
    }

    private class CustomHoliday
    {
        public DateTime Date { get; }
        public bool Recurring { get; }
        public bool IsIslamic { get; }

        public CustomHoliday(DateTime date, bool recurring, bool isIslamic)
        {
            Date = date;
            Recurring = recurring;
            IsIslamic = isIslamic;
        }
    }
}