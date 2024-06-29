import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICrawl } from '../crawl.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../crawl.test-samples';

import { CrawlService, RestCrawl } from './crawl.service';

const requireRestSample: RestCrawl = {
  ...sampleWithRequiredData,
  crawledDate: sampleWithRequiredData.crawledDate?.toJSON(),
};

describe('Crawl Service', () => {
  let service: CrawlService;
  let httpMock: HttpTestingController;
  let expectedResult: ICrawl | ICrawl[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CrawlService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Crawl', () => {
      const crawl = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(crawl).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Crawl', () => {
      const crawl = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(crawl).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Crawl', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Crawl', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Crawl', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCrawlToCollectionIfMissing', () => {
      it('should add a Crawl to an empty array', () => {
        const crawl: ICrawl = sampleWithRequiredData;
        expectedResult = service.addCrawlToCollectionIfMissing([], crawl);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(crawl);
      });

      it('should not add a Crawl to an array that contains it', () => {
        const crawl: ICrawl = sampleWithRequiredData;
        const crawlCollection: ICrawl[] = [
          {
            ...crawl,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCrawlToCollectionIfMissing(crawlCollection, crawl);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Crawl to an array that doesn't contain it", () => {
        const crawl: ICrawl = sampleWithRequiredData;
        const crawlCollection: ICrawl[] = [sampleWithPartialData];
        expectedResult = service.addCrawlToCollectionIfMissing(crawlCollection, crawl);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(crawl);
      });

      it('should add only unique Crawl to an array', () => {
        const crawlArray: ICrawl[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const crawlCollection: ICrawl[] = [sampleWithRequiredData];
        expectedResult = service.addCrawlToCollectionIfMissing(crawlCollection, ...crawlArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const crawl: ICrawl = sampleWithRequiredData;
        const crawl2: ICrawl = sampleWithPartialData;
        expectedResult = service.addCrawlToCollectionIfMissing([], crawl, crawl2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(crawl);
        expect(expectedResult).toContain(crawl2);
      });

      it('should accept null and undefined values', () => {
        const crawl: ICrawl = sampleWithRequiredData;
        expectedResult = service.addCrawlToCollectionIfMissing([], null, crawl, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(crawl);
      });

      it('should return initial array if no Crawl is added', () => {
        const crawlCollection: ICrawl[] = [sampleWithRequiredData];
        expectedResult = service.addCrawlToCollectionIfMissing(crawlCollection, undefined, null);
        expect(expectedResult).toEqual(crawlCollection);
      });
    });

    describe('compareCrawl', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCrawl(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareCrawl(entity1, entity2);
        const compareResult2 = service.compareCrawl(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareCrawl(entity1, entity2);
        const compareResult2 = service.compareCrawl(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareCrawl(entity1, entity2);
        const compareResult2 = service.compareCrawl(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
