import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: '29cee6d1-7233-4363-bf59-83aa1db630cc',
  login: 'sOb@UGMbA\\(gbhUzb',
};

export const sampleWithPartialData: IUser = {
  id: '37db4caf-cb93-482f-b0ab-c158e9e2d6d2',
  login: 'h.Y',
};

export const sampleWithFullData: IUser = {
  id: 'c3adaeb1-78ef-4ce3-9b39-f6c95371065e',
  login: 'RVCS@V\\>LULyCE\\qmXWK\\moTZFRr',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
