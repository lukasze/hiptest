import { IBike } from 'app/shared/model/bike.model';

export interface IOwner {
  id?: number;
  name?: string | null;
  lastName?: string | null;
  mileage?: number | null;
  bikes?: IBike[] | null;
}

export const defaultValue: Readonly<IOwner> = {};
