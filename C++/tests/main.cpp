#define CATCH_CONFIG_MAIN
#include "catch.hpp"
#include "../include/HolidayValidator.h"
#include "../include/TRNCHolidayValidator.h"
#include "../include/TurkeyHolidayValidator.h"
#include <ctime>

TEST_CASE("Test TRNC Fixed Holidays") {
    TRNCHolidayValidator validator(false);
    std::tm date = {0, 0, 0, 1, 7, 124}; // 2024-08-01
    REQUIRE(validator.isHoliday(date));
}

TEST_CASE("Test TRNC Islamic Holidays") {
    TRNCHolidayValidator validator(false);
    std::tm date = {0, 0, 0, 15, 8, 124}; // 2024-09-15
    REQUIRE(validator.isHoliday(date));
}

TEST_CASE("Test Turkey Fixed Holidays") {
    TurkeyHolidayValidator validator(false);
    std::tm date = {0, 0, 0, 23, 3, 124}; // 2024-04-23
    REQUIRE(validator.isHoliday(date));
}
