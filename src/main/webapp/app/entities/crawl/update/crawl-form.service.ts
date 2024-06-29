import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICrawl, NewCrawl } from '../crawl.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICrawl for edit and NewCrawlFormGroupInput for create.
 */
type CrawlFormGroupInput = ICrawl | PartialWithRequiredKeyOf<NewCrawl>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICrawl | NewCrawl> = Omit<T, 'crawledDate'> & {
  crawledDate?: string | null;
};

type CrawlFormRawValue = FormValueOf<ICrawl>;

type NewCrawlFormRawValue = FormValueOf<NewCrawl>;

type CrawlFormDefaults = Pick<NewCrawl, 'id' | 'crawledDate'>;

type CrawlFormGroupContent = {
  id: FormControl<CrawlFormRawValue['id'] | NewCrawl['id']>;
  crawledDate: FormControl<CrawlFormRawValue['crawledDate']>;
  sold: FormControl<CrawlFormRawValue['sold']>;
  product: FormControl<CrawlFormRawValue['product']>;
};

export type CrawlFormGroup = FormGroup<CrawlFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CrawlFormService {
  createCrawlFormGroup(crawl: CrawlFormGroupInput = { id: null }): CrawlFormGroup {
    const crawlRawValue = this.convertCrawlToCrawlRawValue({
      ...this.getFormDefaults(),
      ...crawl,
    });
    return new FormGroup<CrawlFormGroupContent>({
      id: new FormControl(
        { value: crawlRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      crawledDate: new FormControl(crawlRawValue.crawledDate, {
        validators: [Validators.required],
      }),
      sold: new FormControl(crawlRawValue.sold),
      product: new FormControl(crawlRawValue.product),
    });
  }

  getCrawl(form: CrawlFormGroup): ICrawl | NewCrawl {
    return this.convertCrawlRawValueToCrawl(form.getRawValue() as CrawlFormRawValue | NewCrawlFormRawValue);
  }

  resetForm(form: CrawlFormGroup, crawl: CrawlFormGroupInput): void {
    const crawlRawValue = this.convertCrawlToCrawlRawValue({ ...this.getFormDefaults(), ...crawl });
    form.reset(
      {
        ...crawlRawValue,
        id: { value: crawlRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CrawlFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      crawledDate: currentTime,
    };
  }

  private convertCrawlRawValueToCrawl(rawCrawl: CrawlFormRawValue | NewCrawlFormRawValue): ICrawl | NewCrawl {
    return {
      ...rawCrawl,
      crawledDate: dayjs(rawCrawl.crawledDate, DATE_TIME_FORMAT),
    };
  }

  private convertCrawlToCrawlRawValue(
    crawl: ICrawl | (Partial<NewCrawl> & CrawlFormDefaults),
  ): CrawlFormRawValue | PartialWithRequiredKeyOf<NewCrawlFormRawValue> {
    return {
      ...crawl,
      crawledDate: crawl.crawledDate ? crawl.crawledDate.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
