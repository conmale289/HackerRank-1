import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICrawl, NewCrawl } from '../crawl.model';

export type PartialUpdateCrawl = Partial<ICrawl> & Pick<ICrawl, 'id'>;

type RestOf<T extends ICrawl | NewCrawl> = Omit<T, 'crawledDate'> & {
  crawledDate?: string | null;
};

export type RestCrawl = RestOf<ICrawl>;

export type NewRestCrawl = RestOf<NewCrawl>;

export type PartialUpdateRestCrawl = RestOf<PartialUpdateCrawl>;

export type EntityResponseType = HttpResponse<ICrawl>;
export type EntityArrayResponseType = HttpResponse<ICrawl[]>;

@Injectable({ providedIn: 'root' })
export class CrawlService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/crawls');

  create(crawl: NewCrawl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crawl);
    return this.http.post<RestCrawl>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(crawl: ICrawl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crawl);
    return this.http
      .put<RestCrawl>(`${this.resourceUrl}/${this.getCrawlIdentifier(crawl)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(crawl: PartialUpdateCrawl): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(crawl);
    return this.http
      .patch<RestCrawl>(`${this.resourceUrl}/${this.getCrawlIdentifier(crawl)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<RestCrawl>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCrawl[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCrawlIdentifier(crawl: Pick<ICrawl, 'id'>): string {
    return crawl.id;
  }

  compareCrawl(o1: Pick<ICrawl, 'id'> | null, o2: Pick<ICrawl, 'id'> | null): boolean {
    return o1 && o2 ? this.getCrawlIdentifier(o1) === this.getCrawlIdentifier(o2) : o1 === o2;
  }

  addCrawlToCollectionIfMissing<Type extends Pick<ICrawl, 'id'>>(
    crawlCollection: Type[],
    ...crawlsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const crawls: Type[] = crawlsToCheck.filter(isPresent);
    if (crawls.length > 0) {
      const crawlCollectionIdentifiers = crawlCollection.map(crawlItem => this.getCrawlIdentifier(crawlItem));
      const crawlsToAdd = crawls.filter(crawlItem => {
        const crawlIdentifier = this.getCrawlIdentifier(crawlItem);
        if (crawlCollectionIdentifiers.includes(crawlIdentifier)) {
          return false;
        }
        crawlCollectionIdentifiers.push(crawlIdentifier);
        return true;
      });
      return [...crawlsToAdd, ...crawlCollection];
    }
    return crawlCollection;
  }

  protected convertDateFromClient<T extends ICrawl | NewCrawl | PartialUpdateCrawl>(crawl: T): RestOf<T> {
    return {
      ...crawl,
      crawledDate: crawl.crawledDate?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCrawl: RestCrawl): ICrawl {
    return {
      ...restCrawl,
      crawledDate: restCrawl.crawledDate ? dayjs(restCrawl.crawledDate) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCrawl>): HttpResponse<ICrawl> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCrawl[]>): HttpResponse<ICrawl[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
