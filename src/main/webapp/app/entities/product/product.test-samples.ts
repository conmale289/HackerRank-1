import { IProduct, NewProduct } from './product.model';

export const sampleWithRequiredData: IProduct = {
  id: '5a5a99dd-38e4-4745-a240-1ec163dae19a',
  productId: 'bounce yahoo',
  name: 'wowee under',
};

export const sampleWithPartialData: IProduct = {
  id: '2b04d735-054d-4e26-9224-e3ce0db36038',
  productId: 'heyday feud until',
  name: 'although',
  comment: 5058,
  sku: 'by wetly',
  productLink: 'poorly',
};

export const sampleWithFullData: IProduct = {
  id: 'a4274211-9f29-4b89-9b16-863bd0bc3c3c',
  productId: 'yum among times',
  name: 'carbonize nutritious lazily',
  shop: 'dump',
  sold: 10260,
  comment: 23954,
  sku: 'fondly',
  productLink: 'an',
};

export const sampleWithNewData: NewProduct = {
  productId: 'whose',
  name: 'energetically flavour',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
