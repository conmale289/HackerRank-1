import dayjs from 'dayjs/esm';

import { ICrawl, NewCrawl } from './crawl.model';

export const sampleWithRequiredData: ICrawl = {
  id: '72cac995-2c74-4c5e-beb5-3a761053e340',
  crawledDate: dayjs('2024-06-29T01:13'),
};

export const sampleWithPartialData: ICrawl = {
  id: '15f01f96-5660-4e5d-8ef2-7b513b5c7235',
  crawledDate: dayjs('2024-06-28T08:25'),
};

export const sampleWithFullData: ICrawl = {
  id: 'd134e1f5-157d-40f5-be47-d402fa84e34f',
  crawledDate: dayjs('2024-06-29T04:18'),
  sold: 12446,
};

export const sampleWithNewData: NewCrawl = {
  crawledDate: dayjs('2024-06-28T21:30'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
