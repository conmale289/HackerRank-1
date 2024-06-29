export interface IProduct {
  id: string;
  productId?: string | null;
  name?: string | null;
  shop?: string | null;
  sold?: number | null;
  comment?: number | null;
  sku?: string | null;
  productLink?: string | null;
}

export type NewProduct = Omit<IProduct, 'id'> & { id: null };
