import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { CrawlService } from '../service/crawl.service';
import { ICrawl } from '../crawl.model';
import { CrawlFormService } from './crawl-form.service';

import { CrawlUpdateComponent } from './crawl-update.component';

describe('Crawl Management Update Component', () => {
  let comp: CrawlUpdateComponent;
  let fixture: ComponentFixture<CrawlUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let crawlFormService: CrawlFormService;
  let crawlService: CrawlService;
  let productService: ProductService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CrawlUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CrawlUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CrawlUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    crawlFormService = TestBed.inject(CrawlFormService);
    crawlService = TestBed.inject(CrawlService);
    productService = TestBed.inject(ProductService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const crawl: ICrawl = { id: 'CBA' };
      const product: IProduct = { id: 'f02ce56e-e1c7-444f-9917-788069baec47' };
      crawl.product = product;

      const productCollection: IProduct[] = [{ id: '79a4dbb4-0a52-4519-aa02-a5f26a9e4d82' }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ crawl });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(
        productCollection,
        ...additionalProducts.map(expect.objectContaining),
      );
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const crawl: ICrawl = { id: 'CBA' };
      const product: IProduct = { id: 'a10a892c-1c36-4b8f-8952-b427927124f9' };
      crawl.product = product;

      activatedRoute.data = of({ crawl });
      comp.ngOnInit();

      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.crawl).toEqual(crawl);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICrawl>>();
      const crawl = { id: 'ABC' };
      jest.spyOn(crawlFormService, 'getCrawl').mockReturnValue(crawl);
      jest.spyOn(crawlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ crawl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: crawl }));
      saveSubject.complete();

      // THEN
      expect(crawlFormService.getCrawl).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(crawlService.update).toHaveBeenCalledWith(expect.objectContaining(crawl));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICrawl>>();
      const crawl = { id: 'ABC' };
      jest.spyOn(crawlFormService, 'getCrawl').mockReturnValue({ id: null });
      jest.spyOn(crawlService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ crawl: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: crawl }));
      saveSubject.complete();

      // THEN
      expect(crawlFormService.getCrawl).toHaveBeenCalled();
      expect(crawlService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ICrawl>>();
      const crawl = { id: 'ABC' };
      jest.spyOn(crawlService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ crawl });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(crawlService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareProduct', () => {
      it('Should forward to productService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(productService, 'compareProduct');
        comp.compareProduct(entity, entity2);
        expect(productService.compareProduct).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
