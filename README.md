### Türkçe Tatil Kontrolü Sınıfı 🌟

TRNCHolidayValidator, belirli tarihlerdeki tatilleri kontrol eden bir sınıf örneğidir. Hem sabit tarihlerdeki tatilleri (yeni yıl, milli bayramlar vb.) hem de dinamik İslami tatilleri (Ramazan Bayramı, Kurban Bayramı, Mevlid Kandili) hesaplar ve kontrol eder. Ayrıca, isteğe bağlı olarak Cumartesi gününü de tatil olarak değerlendirebilir. Bu sınıf, her yıl otomatik olarak tatilleri hesaplar, bu yüzden yıllık güncellemeler yapmanıza gerek kalmaz. Kullanıcıların özel tatiller eklemesi de mümkündür ve bu tatiller her yıl tekrarlanabilir veya tek seferlik olabilir. Ayrıca, eklenen özel tatillerin İslami tatil olup olmadığını kontrol etme seçeneği de mevcuttur.

## Desteklenen Diller 🛠️
- PHP
- Python
- C#
- Java
- JavaScript

## Sabit Tatiller 🚀
- 01-01: Yeni Yıl
- 04-23: Ulusal Egemenlik ve Çocuk Bayramı
- 05-01: İşçi Bayramı
- 05-19: Atatürk'ü Anma, Gençlik ve Spor Bayramı
- 07-20: Barış ve Özgürlük Bayramı
- 08-01: TMT Günü
- 08-30: Zafer Bayramı
- 10-29: Cumhuriyet Bayramı
- 11-15: Kuzey Kıbrıs Türk Cumhuriyeti'nin İlanı

## İslami Tatiller 🌙
- Ramazan Bayramı (3 gün)
- Kurban Bayramı (4 gün)
- Mevlid Kandili

## Kurulum ve Kullanım 📦

### PHP

```php
<?php
use PHP\TRNCHolidayValidator;require 'TRNCHolidayValidator.php';

$validator = new TRNCHolidayValidator(true);
$date = new DateTime('2024-04-23');
$isHoliday = $validator->isHoliday($date);
echo $isHoliday ? 'Tatil' : 'Tatil değil';

// Özel tatil ekleme (opsiyonel)
$validator->addCustomHoliday(new DateTime('2024-12-25'), true, true);
$date = new DateTime('2024-12-25');
$isHoliday = $validator->isHoliday($date);
echo $isHoliday ? 'Özel Tatil' : 'Tatil değil';
?>
```

### Python
```python
from datetime import datetime
from trnc_holiday_validator import TRNCHolidayValidator

validator = TRNCHolidayValidator(include_saturday=True)
date = datetime(2024, 4, 23)
is_holiday = validator.is_holiday(date)
print('Tatil' if is_holiday else 'Tatil değil')

# Özel tatil ekleme (opsiyonel)
validator.add_custom_holiday(datetime(2024, 12, 25), recurring=True, is_islamic=True)
date = datetime(2024, 12, 25)
is_holiday = validator.is_holiday(date)
print('Özel Tatil' if is_holiday else 'Tatil değil')
```

### C#
```csharp
using System;

var validator = new TRNCHolidayValidator(includeSaturday: true);
var date = new DateTime(2024, 4, 23);
bool isHoliday = validator.IsHoliday(date);
Console.WriteLine(isHoliday ? "Tatil" : "Tatil değil");

// Özel tatil ekleme (opsiyonel)
validator.AddCustomHoliday(new DateTime(2024, 12, 25), true, true);
date = new DateTime(2024, 12, 25);
isHoliday = validator.IsHoliday(date);
Console.WriteLine(isHoliday ? "Özel Tatil" : "Tatil değil");
```

### Java
```java
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        TRNCHolidayValidator validator = new TRNCHolidayValidator(true);
        LocalDate date = LocalDate.of(2024, 4, 23);
        boolean isHoliday = validator.isHoliday(date);
        System.out.println(isHoliday ? "Tatil" : "Tatil değil");

        // Özel tatil ekleme (opsiyonel)
        validator.addCustomHoliday(LocalDate.of(2024, 12, 25), true, true);
        date = LocalDate.of(2024, 12, 25);
        isHoliday = validator.isHoliday(date);
        System.out.println(isHoliday ? "Özel Tatil" : "Tatil değil");
    }
}
```

### JavaScript
```javascript
const validator = new TRNCHolidayValidator(true);
let date = new Date('2024-04-23');
let isHoliday = validator.isHoliday(date);
console.log(isHoliday ? 'Tatil' : 'Tatil değil');

// Özel tatil ekleme (opsiyonel)
validator.addCustomHoliday(new Date('2024-12-25'), true, true);
date = new Date('2024-12-25');
isHoliday = validator.isHoliday(date);
console.log(isHoliday ? 'Özel Tatil' : 'Tatil değil');
```

## Katkıda Bulunma 🤝
Bu projeyi geliştirmemize yardımcı olmak için katkılarınızı bekliyoruz! Lütfen pull request'lerinizi gönderin ve projeye katkıda bulunun.

## Lisans 📄
Bu proje MIT Lisansı ile lisanslanmıştır. Daha fazla bilgi için LICENSE dosyasına bakabilirsiniz.

## Destek 🌟
Bu sınıfın sizin için faydalı olduğunu düşünüyorsanız, lütfen bu repo'yu ⭐️ yıldızlayarak bana destek olun. Yorumlarınız ve geri bildirimleriniz beni mutlu eder!

---

Bu sınıfı, zaman yönetimi, personel takibi, ödeme sistemleri gibi birçok farklı projede kullanabilirsiniz.

### İletişim 📧
Herhangi bir sorunuz veya geri bildiriminiz için lütfen benimle iletişime geçin.

Teşekkürler! 🎉
