import { HolidayValidator } from './HolidayValidator';

export class TurkeyHolidayValidator extends HolidayValidator {
  constructor(includeSaturday: boolean = false) {
    super(includeSaturday);
  }
}
