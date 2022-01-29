import { IDriver } from 'app/shared/model/driver.model';

export interface ITruck {
  id?: number;
  model?: string | null;
  engine?: string | null;
  serialNo?: string | null;
  drivers?: IDriver[] | null;
}

export const defaultValue: Readonly<ITruck> = {};
