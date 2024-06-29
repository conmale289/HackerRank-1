import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { CrawlDetailComponent } from './crawl-detail.component';

describe('Crawl Management Detail Component', () => {
  let comp: CrawlDetailComponent;
  let fixture: ComponentFixture<CrawlDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrawlDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CrawlDetailComponent,
              resolve: { crawl: () => of({ id: 'ABC' }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CrawlDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CrawlDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load crawl on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CrawlDetailComponent);

      // THEN
      expect(instance.crawl()).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
