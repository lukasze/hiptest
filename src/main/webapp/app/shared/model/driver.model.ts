import { ITruck } from 'app/shared/model/truck.model';

export interface IDriver {
  id?: number;
  name?: string | null;
  lastName?: string | null;
  mileage?: number | null;
  trucks?: ITruck[] | null;
}

export const defaultValue: Readonly<IDriver> = {};
