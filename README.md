# Türkiye ve KKTC Tatil Kontrolü Sınıfları 🌟

TRNCHolidayValidator ve TurkeyHolidayValidator, belirli tarihlerdeki tatilleri kontrol eden sınıf örnekleridir. Hem sabit tarihlerdeki tatilleri (yeni yıl, milli bayramlar vb.) hem de dinamik İslami tatilleri (Ramazan Bayramı ve Kurban Bayramı) hesaplar ve kontrol ederler. Ayrıca, isteğe bağlı olarak Cumartesi gününü de tatil olarak değerlendirebilirler. Bu sınıflar, her yıl otomatik olarak tatilleri hesaplar, bu yüzden yıllık güncellemeler yapmanıza gerek kalmaz. Kullanıcıların özel tatiller eklemesi de mümkündür ve bu tatiller her yıl tekrarlanabilir veya tek seferlik olabilir. Ayrıca, eklenen özel tatillerin İslami tatil olup olmadığını kontrol etme seçeneği de mevcuttur. Varsayılan olarak, Pazar günleri tatil olarak kabul edilir.

TRNCHolidayValidator sadece Kuzey Kıbrıs Türk Cumhuriyeti (KKTC) için, TurkeyHolidayValidator ise sadece Türkiye için kullanılabilir. KKTC için bazı ek tatiller mevcuttur. Bu sınıflar, sadece Türkiye ve KKTC için değil, diğer ülkeler için de benzer şekilde genişletilebilir.

## Sorunun Tanımı ve Çözümün Zorluğu 🔍
Birçok kurum ve işletme, resmi tatiller ve özel tatiller için özel düzenlemeler yapar. Bu tatillerin doğru şekilde hesaplanması ve kontrol edilmesi, çalışanların doğru yönetilmesi, maaş hesaplamalarının doğru yapılması ve işletme operasyonlarının sorunsuz yürütülmesi için kritik öneme sahiptir. Ancak, tatillerin farklı ülkelerde farklı tarihlerde olması ve bazı tatillerin dinamik olarak belirlenmesi, bu süreci oldukça karmaşık hale getirir. Örneğin, İslami tatiller gibi hareketli tatillerin doğru bir şekilde hesaplanması, özellikle zordur. Bu sınıflar, bu karmaşıklığı azaltmak ve tatil hesaplamalarını otomatikleştirerek işletmelere ve geliştiricilere büyük kolaylık sağlar.

## Sabit Tatiller 🚀
### Türkiye
- 01-01: Yeni Yıl
- 04-23: Ulusal Egemenlik ve Çocuk Bayramı
- 05-01: İşçi Bayramı
- 05-19: Atatürk'ü Anma, Gençlik ve Spor Bayramı
- 07-20: Barış ve Özgürlük Bayramı
- 08-30: Zafer Bayramı
- 10-29: Cumhuriyet Bayramı

### KKTC
- Türkiye tatillerine ek olarak:
- 08-01: TMT Günü
- 11-15: Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı

## İslami Tatiller 🌙
- Ramazan Bayramı (3 gün)
- Kurban Bayramı (4 gün)
- Mevlid Kandili (sadece KKTC)

## 📚 İçindekiler
- [Desteklenen Diller 🛠️](#desteklenen-diller-🛠️)
- [Kurulum ve Kullanım 📦](#kurulum-ve-kullanım-📦)
    - [PHP](#php)
    - [Python](#python)
    - [C#](#c)
    - [Java](#java)
    - [JavaScript](#javascript)
    - [Kotlin](#kotlin)
    - [Ruby](#ruby)
    - [Rust](#rust)
    - [Swift](#swift)
    - [TypeScript](#typescript)
- [Testler 🧪](#testler-🧪)
- [Katkıda Bulunma 🤝](#katkıda-bulunma-🤝)
- [Lisans 📄](#lisans-📄)
- [Destek ❤️](#destek-❤️)
- [İletişim 📧](#iletişim-📧)


## Desteklenen Diller 🛠️
- PHP
- Python
- C#
- C++
- Go
- Java
- JavaScript
- Kotlin
- Ruby
- Rust
- Swift
- TypeScript

## Kurulum ve Kullanım 📦

<details>
<summary>PHP</summary>

```php
<?php
use PHP\TRNCHolidayValidator;
use PHP\TurkeyHolidayValidator;
require 'TRNCHolidayValidator.php';
require 'TurkeyHolidayValidator.php';

// KKTC örneği
$trncValidator = new TRNCHolidayValidator(true);
$date = new DateTime('2024-11-15');
$isHoliday = $trncValidator->isHoliday($date);
echo $isHoliday ? 'Tatil' : 'Tatil değil';

// Türkiye örneği
$turkeyValidator = new TurkeyHolidayValidator(true);
$date = new DateTime('2024-04-23');
$isHoliday = $turkeyValidator->isHoliday($date);
echo $isHoliday ? 'Tatil' : 'Tatil değil';

// Özel tatil ekleme (opsiyonel)
$trncValidator->addCustomHoliday(new DateTime('2024-12-25'), true, true);
$date = new DateTime('2024-12-25');
$isHoliday = $trncValidator->isHoliday($date);
echo $isHoliday ? 'Özel Tatil' : 'Tatil değil';
?>
```
</details>

<details>
<summary>Python</summary>

```python
from datetime import datetime
from trnc_holiday_validator import TRNCHolidayValidator
from turkey_holiday_validator import TurkeyHolidayValidator

# KKTC örneği
trnc_validator = TRNCHolidayValidator(include_saturday=True)
date = datetime(2024, 11, 15)
is_holiday = trnc_validator.is_holiday(date)
print('Tatil' if is_holiday else 'Tatil değil')

# Türkiye örneği
turkey_validator = TurkeyHolidayValidator(include_saturday=True)
date = datetime(2024, 4, 23)
is_holiday = turkey_validator.is_holiday(date)
print('Tatil' if is_holiday else 'Tatil değil')

# Özel tatil ekleme (opsiyonel)
trnc_validator.add_custom_holiday(datetime(2024, 12, 25), recurring=True, is_islamic=True)
date = datetime(2024, 12, 25)
is_holiday = trnc_validator.is_holiday(date)
print('Özel Tatil' if is_holiday else 'Tatil değil')
```
</details>

<details>
<summary>C#</summary>

```csharp
using System;

// KKTC örneği
var trncValidator = new TRNCHolidayValidator(includeSaturday: true);
var date = new DateTime(2024, 11, 15);
bool isHoliday = trncValidator.IsHoliday(date);
Console.WriteLine(isHoliday ? "Tatil" : "Tatil değil");

// Türkiye örneği
var turkeyValidator = new TurkeyHolidayValidator(includeSaturday: true);
date = new DateTime(2024, 4, 23);
isHoliday = turkeyValidator.IsHoliday(date);
Console.WriteLine(isHoliday ? "Tatil" : "Tatil değil");

// Özel tatil ekleme (opsiyonel)
trncValidator.AddCustomHoliday(new DateTime(2024, 12, 25), true, true);
date = new DateTime(2024, 12, 25);
isHoliday = trncValidator.IsHoliday(date);
Console.WriteLine(isHoliday ? "Özel Tatil" : "Tatil değil");
```
</details>

<details>
<summary>C++</summary>

```cpp
#include <iostream>
#include "trnc_holiday_validator.h"
#include "turkey_holiday_validator.h"

int main() {
    // KKTC örneği
    TRNCHolidayValidator trncValidator(true);
    std::tm date = {};
    date.tm_year = 2024 - 1900; // Years since 1900
    date.tm_mon = 10 - 1; // Months since January (0-11)
    date.tm_mday = 15;

    bool isHoliday = trncValidator.isHoliday(date);
    std::cout << (isHoliday ? "Tatil" : "Tatil değil") << std::endl;

    // Türkiye örneği
    TurkeyHolidayValidator turkeyValidator(true);
    date.tm_mon = 3 - 1; // April
    date.tm_mday = 23;

    isHoliday = turkeyValidator.isHoliday(date);
    std::cout << (isHoliday ? "Tatil" : "Tatil değil") << std::endl;

    // Özel tatil ekleme (opsiyonel)
    date.tm_year = 2024 - 1900; // Years since 1900
    date.tm_mon = 12 - 1; // December
    date.tm_mday = 25;
    trncValidator.addCustomHoliday(date, true, true);

    isHoliday = trncValidator.isHoliday(date);
    std::cout << (isHoliday ? "Özel Tatil" : "Tatil değil") << std::endl;

    return 0;
}
```
</details>

<details>
<summary>Go</summary>

```go
package main

import (
    "fmt"
    "time"

    "./trnc_holiday_validator"
    "./turkey_holiday_validator"
)

func main() {
    // KKTC örneği
    trncValidator := trnc_holiday_validator.New(true)
    date := time.Date(2024, time.November, 15, 0, 0, 0, 0, time.UTC)
    isHoliday := trncValidator.IsHoliday(date)
    fmt.Println(if isHoliday { "Tatil" } else { "Tatil değil" })

    // Türkiye örneği
    turkeyValidator := turkey_holiday_validator.New(true)
    date = time.Date(2024, time.April, 23, 0, 0, 0, 0, time.UTC)
    isHoliday = turkeyValidator.IsHoliday(date)
    fmt.Println(if isHoliday { "Tatil" } else { "Tatil değil" })

    // Özel tatil ekleme (opsiyonel)
    date = time.Date(2024, time.December, 25, 0, 0, 0, 0, time.UTC)
    trncValidator.AddCustomHoliday(date, true, true)
    isHoliday = trncValidator.IsHoliday(date)
    fmt.Println(if isHoliday { "Özel Tatil" } else { "Tatil değil" })
}
```
</details>

<details>
<summary>Java</summary>

```java
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // KKTC örneği
        TRNCHolidayValidator trncValidator = new TRNCHolidayValidator(true);
        LocalDate date = LocalDate.of(2024, 11, 15);
        boolean isHoliday = trncValidator.isHoliday(date);
        System.out.println(isHoliday ? "Tatil" : "Tatil değil");

        // Türkiye örneği
        TurkeyHolidayValidator turkeyValidator = new TurkeyHolidayValidator(true);
        date = LocalDate.of(2024, 4, 23);
        isHoliday = turkeyValidator.isHoliday(date);
        System.out.println(isHoliday ? "Tatil" : "Tatil değil");

        // Özel tatil ekleme (opsiyonel)
        trncValidator.addCustomHoliday(LocalDate.of(2024, 12, 25), true, true);
        date = LocalDate.of(2024, 12, 25);
        isHoliday = trncValidator.isHoliday(date);
        System.out.println(isHoliday ? "Özel Tatil" : "Tatil değil");
    }
}
```
</details>

<details>
<summary>JavaScript</summary>

```javascript
// KKTC örneği
const trncValidator = new TRNCHolidayValidator(true);
let date = new Date('2024-11-15');
let isHoliday = trncValidator.isHoliday(date);
console.log(isHoliday ? 'Tatil' : 'Tatil değil');

// Türkiye örneği
const turkeyValidator = new TurkeyHolidayValidator(true);
date = new Date('2024-04-23');
isHoliday = turkeyValidator.isHoliday(date);
console.log(isHoliday ? 'Tatil' : 'Tatil değil');

// Özel tatil ekleme (opsiyonel)
trncValidator.addCustomHoliday(new Date('2024-12-25'), true, true);
date = new Date('2024-12-25');
isHoliday = trncValidator.isHoliday(date);
console.log(isHoliday ? 'Özel Tatil' : 'Tatil değil');
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
import java.time.LocalDate

fun main() {
    // KKTC örneği
    val trncValidator = TRNCHolidayValidator(true)
    var date = LocalDate.of(2024, 11, 15)
    var isHoliday = trncValidator.isHoliday(date)
    println(if (isHoliday) "Tatil" else "Tatil değil")

    // Türkiye örneği
    val turkeyValidator = TurkeyHolidayValidator(true)
    date = LocalDate.of(2024, 4, 23

)
    isHoliday = turkeyValidator.isHoliday(date)
    println(if (isHoliday) "Tatil" else "Tatil değil")

    // Özel tatil ekleme (opsiyonel)
    trncValidator.addCustomHoliday(LocalDate.of(2024, 12, 25), true, true)
    date = LocalDate.of(2024, 12, 25)
    isHoliday = trncValidator.isHoliday(date)
    println(if (isHoliday) "Özel Tatil" else "Tatil değil")
}
```
</details>

<details>
<summary>Ruby</summary>

```ruby
require 'date'
require_relative 'trnc_holiday_validator'
require_relative 'turkey_holiday_validator'

# KKTC örneği
trnc_validator = TRNCHolidayValidator.new(true)
date = Date.new(2024, 11, 15)
is_holiday = trnc_validator.holiday?(date)
puts is_holiday ? 'Tatil' : 'Tatil değil'

# Türkiye örneği
turkey_validator = TurkeyHolidayValidator.new(true)
date = Date.new(2024, 4, 23)
is_holiday = turkey_validator.holiday?(date)
puts is_holiday ? 'Tatil' : 'Tatil değil'

# Özel tatil ekleme (opsiyonel)
trnc_validator.add_custom_holiday(Date.new(2024, 12, 25), recurring: true, islamic: true)
date = Date.new(2024, 12, 25)
is_holiday = trnc_validator.holiday?(date)
puts is_holiday ? 'Özel Tatil' : 'Tatil değil'
```
</details>

<details>
<summary>Rust</summary>

```rust
extern crate chrono;

use chrono::NaiveDate;
use trnc_holiday_validator::TRNCHolidayValidator;
use turkey_holiday_validator::TurkeyHolidayValidator;

fn main() {
    // KKTC örneği
    let trnc_validator = TRNCHolidayValidator::new(true);
    let date = NaiveDate::from_ymd(2024, 11, 15);
    let is_holiday = trnc_validator.is_holiday(date);
    println!("{}", if is_holiday { "Tatil" } else { "Tatil değil" });

    // Türkiye örneği
    let turkey_validator = TurkeyHolidayValidator::new(true);
    let date = NaiveDate::from_ymd(2024, 4, 23);
    let is_holiday = turkey_validator.is_holiday(date);
    println!("{}", if is_holiday { "Tatil" } else { "Tatil değil" });

    // Özel tatil ekleme (opsiyonel)
    trnc_validator.add_custom_holiday(NaiveDate::from_ymd(2024, 12, 25), true, true);
    let date = NaiveDate::from_ymd(2024, 12, 25);
    let is_holiday = trnc_validator.is_holiday(date);
    println!("{}", if is_holiday { "Özel Tatil" } else { "Tatil değil" });
}
```
</details>

<details>
<summary>Swift</summary>

```swift
import Foundation

// KKTC örneği
let trncValidator = TRNCHolidayValidator(includeSaturday: true)
let date = DateComponents(calendar: Calendar.current, year: 2024, month: 11, day: 15).date!
let isHoliday = trncValidator.isHoliday(date: date)
print(isHoliday ? "Tatil" : "Tatil değil")

// Türkiye örneği
let turkeyValidator = TurkeyHolidayValidator(includeSaturday: true)
let date = DateComponents(calendar: Calendar.current, year: 2024, month: 4, day: 23).date!
let isHoliday = turkeyValidator.isHoliday(date: date)
print(isHoliday ? "Tatil" : "Tatil değil")

// Özel tatil ekleme (opsiyonel)
trncValidator.addCustomHoliday(date: DateComponents(calendar: Calendar.current, year: 2024, month: 12, day: 25).date!, recurring: true, isIslamic: true)
let date = DateComponents(calendar: Calendar.current, year: 2024, month: 12, day: 25).date!
let isHoliday = trncValidator.isHoliday(date: date)
print(isHoliday ? "Özel Tatil" : "Tatil değil")
```
</details>

<details>
<summary>TypeScript</summary>

```typescript
// KKTC örneği
const trncValidator = new TRNCHolidayValidator(true);
let date = new Date('2024-11-15');
let isHoliday = trncValidator.isHoliday(date);
console.log(isHoliday ? 'Tatil' : 'Tatil değil');

// Türkiye örneği
const turkeyValidator = new TurkeyHolidayValidator(true);
date = new Date('2024-04-23');
isHoliday = turkeyValidator.isHoliday(date);
console.log(isHoliday ? 'Tatil' : 'Tatil değil');

// Özel tatil ekleme (opsiyonel)
trncValidator.addCustomHoliday(new Date('2024-12-25'), true, true);
date = new Date('2024-12-25');
isHoliday = trncValidator.isHoliday(date);
console.log(isHoliday ? 'Özel Tatil' : 'Tatil değil');
```
</details>

## Testler 🧪

Bu projeyi test etmek için her dilde birim testleri yazılmıştır. Test dosyaları ve testlerin nasıl çalıştırılacağına dair bilgiler aşağıda verilmiştir:

### PHP

```sh
vendor/bin/phpunit --bootstrap src/autoload.php tests/HolidayValidatorTest.php
```

### Python

```sh
python -m unittest discover -s tests
```

### C#

```sh
dotnet test
```

### Java

```sh
./gradlew test
```

### JavaScript

```sh
npm test
```

### Kotlin

```sh
./gradlew test
```

### Ruby

```sh
rake test
```

### Rust

```sh
cargo test
```

### Swift

```sh
swift test
```

### TypeScript

```sh
npm test
```

### Test Dosyaları

- PHP: `tests/HolidayValidatorTest.php`, `tests/TRNCHolidayValidatorTest.php`, `tests/TurkeyHolidayValidatorTest.php`
- Python: `tests/test_holiday_validator.py`, `tests/test_trnc_holiday_validator.py`, `tests/test_turkey_holiday_validator.py`
- C#: `Tests/HolidayValidatorTest.cs`, `Tests/TRNCHolidayValidatorTest.cs`, `Tests/TurkeyHolidayValidatorTest.cs`
- Java: `src/test/java/HolidayChecker/HolidayValidatorTest.java`, `src/test/java/HolidayChecker/TRNC/TRNCHolidayValidatorTest.java`, `src/test/java/HolidayChecker/Turkey/TurkeyHolidayValidatorTest.java`
- JavaScript: `tests/holidayValidator.test.js`, `tests/trncHolidayValidator.test.js`, `tests/turkeyHolidayValidator.test.js`
- Kotlin: `tests/holidayValidatorTest.kt`, `tests/trncHolidayValidatorTest.kt`, `tests/turkeyHolidayValidatorTest.kt`
- Ruby: `tests/holiday_validator_test.rb`, `tests/trnc_holiday_validator_test.rb`, `tests/turkey_holiday_validator_test.rb`
- Rust: `tests/holiday_validator_test.rs`, `tests/trnc_holiday_validator_test.rs`, `tests/turkey_holiday_validator_test.rs`
- Swift: `tests/HolidayValidatorTests.swift`, `tests/TRNCHolidayValidatorTests.swift`, `tests/TurkeyHolidayValidatorTests.swift`
- TypeScript: `tests/HolidayValidator.test.ts`, `tests/TRNCHolidayValidator.test.ts`, `tests/TurkeyHolidayValidator.test.ts`

## Katkıda Bulunma 🤝

Bu projeyi geliştirmeme yardımcı olmak için katkılarınızı bekliyorum! Lütfen pull request'lerinizi gönderin ve projeye katkıda bulunun.

## Lisans 📄

Bu proje MIT Lisansı ile lisanslanmıştır. Daha fazla bilgi için LICENSE dosyasına bakabilirsiniz.

## Destek ❤️

Bu sınıfların sizin için faydalı olduğunu düşünüyorsanız, lütfen bu repo'yu ⭐️ yıldızlayarak bana destek olun. Yorumlarınız ve geri bildirimleriniz beni mutlu eder!

---

Bu sınıfları, zaman yönetimi, personel takibi, ödeme sistemleri gibi birçok farklı projede kullanabilirsiniz.

### İletişim 📧

Herhangi bir sorunuz veya geri bildiriminiz için lütfen benimle iletişime geçin.

Teşekkürler! 🎉