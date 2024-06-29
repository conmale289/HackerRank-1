import { IImage, NewImage } from './image.model';

export const sampleWithRequiredData: IImage = {
  id: '935ffde5-7d02-4972-b428-e88cc7d06010',
  src: 'aha quintessential forenenst',
};

export const sampleWithPartialData: IImage = {
  id: '807b059d-f08a-4ad2-a9d6-229b447a50af',
  src: 'sharply',
};

export const sampleWithFullData: IImage = {
  id: 'bc342494-491c-44b6-9c01-e1096ff80cc8',
  src: 'ah off over',
};

export const sampleWithNewData: NewImage = {
  src: 'extinguish',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
