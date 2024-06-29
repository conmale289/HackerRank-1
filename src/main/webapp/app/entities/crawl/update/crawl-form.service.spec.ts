import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../crawl.test-samples';

import { CrawlFormService } from './crawl-form.service';

describe('Crawl Form Service', () => {
  let service: CrawlFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CrawlFormService);
  });

  describe('Service methods', () => {
    describe('createCrawlFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCrawlFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            crawledDate: expect.any(Object),
            sold: expect.any(Object),
            product: expect.any(Object),
          }),
        );
      });

      it('passing ICrawl should create a new form with FormGroup', () => {
        const formGroup = service.createCrawlFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            crawledDate: expect.any(Object),
            sold: expect.any(Object),
            product: expect.any(Object),
          }),
        );
      });
    });

    describe('getCrawl', () => {
      it('should return NewCrawl for default Crawl initial value', () => {
        const formGroup = service.createCrawlFormGroup(sampleWithNewData);

        const crawl = service.getCrawl(formGroup) as any;

        expect(crawl).toMatchObject(sampleWithNewData);
      });

      it('should return NewCrawl for empty Crawl initial value', () => {
        const formGroup = service.createCrawlFormGroup();

        const crawl = service.getCrawl(formGroup) as any;

        expect(crawl).toMatchObject({});
      });

      it('should return ICrawl', () => {
        const formGroup = service.createCrawlFormGroup(sampleWithRequiredData);

        const crawl = service.getCrawl(formGroup) as any;

        expect(crawl).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICrawl should not enable id FormControl', () => {
        const formGroup = service.createCrawlFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCrawl should disable id FormControl', () => {
        const formGroup = service.createCrawlFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
