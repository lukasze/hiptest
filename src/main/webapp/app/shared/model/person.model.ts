import { IGlasses } from 'app/shared/model/glasses.model';

export interface IPerson {
  id?: number;
  name?: string | null;
  lastName?: string | null;
  glasses?: IGlasses | null;
}

export const defaultValue: Readonly<IPerson> = {};
