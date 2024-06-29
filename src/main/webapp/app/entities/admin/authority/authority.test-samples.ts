import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'e0b7e07e-dda2-4b2e-85a9-000aed516a83',
};

export const sampleWithPartialData: IAuthority = {
  name: '9db1d0f6-6d03-4394-ae1f-5255340a05fd',
};

export const sampleWithFullData: IAuthority = {
  name: '92c66e1a-f22f-4a2c-af12-13e13a33792a',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
