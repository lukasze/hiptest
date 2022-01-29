import { IPerson } from 'app/shared/model/person.model';

export interface IGlasses {
  id?: number;
  model?: string | null;
  front?: string | null;
  temples?: string | null;
  lenses?: string | null;
  person?: IPerson | null;
}

export const defaultValue: Readonly<IGlasses> = {};
