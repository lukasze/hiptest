import { IOwner } from 'app/shared/model/owner.model';

export interface IBike {
  id?: number;
  model?: string | null;
  serialNo?: string | null;
  owner?: IOwner | null;
}

export const defaultValue: Readonly<IBike> = {};
