import dayjs from 'dayjs/esm';
import { IProduct } from 'app/entities/product/product.model';

export interface ICrawl {
  id: string;
  crawledDate?: dayjs.Dayjs | null;
  sold?: number | null;
  product?: Pick<IProduct, 'id'> | null;
}

export type NewCrawl = Omit<ICrawl, 'id'> & { id: null };
