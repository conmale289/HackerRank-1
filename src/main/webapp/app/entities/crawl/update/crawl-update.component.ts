import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { ICrawl } from '../crawl.model';
import { CrawlService } from '../service/crawl.service';
import { CrawlFormService, CrawlFormGroup } from './crawl-form.service';

@Component({
  standalone: true,
  selector: 'jhi-crawl-update',
  templateUrl: './crawl-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CrawlUpdateComponent implements OnInit {
  isSaving = false;
  crawl: ICrawl | null = null;

  productsSharedCollection: IProduct[] = [];

  protected crawlService = inject(CrawlService);
  protected crawlFormService = inject(CrawlFormService);
  protected productService = inject(ProductService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: CrawlFormGroup = this.crawlFormService.createCrawlFormGroup();

  compareProduct = (o1: IProduct | null, o2: IProduct | null): boolean => this.productService.compareProduct(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ crawl }) => {
      this.crawl = crawl;
      if (crawl) {
        this.updateForm(crawl);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const crawl = this.crawlFormService.getCrawl(this.editForm);
    if (crawl.id !== null) {
      this.subscribeToSaveResponse(this.crawlService.update(crawl));
    } else {
      this.subscribeToSaveResponse(this.crawlService.create(crawl));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICrawl>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(crawl: ICrawl): void {
    this.crawl = crawl;
    this.crawlFormService.resetForm(this.editForm, crawl);

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing<IProduct>(
      this.productsSharedCollection,
      crawl.product,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing<IProduct>(products, this.crawl?.product)))
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));
  }
}
