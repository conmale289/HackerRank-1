import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICrawl } from '../crawl.model';
import { CrawlService } from '../service/crawl.service';

const crawlResolve = (route: ActivatedRouteSnapshot): Observable<null | ICrawl> => {
  const id = route.params['id'];
  if (id) {
    return inject(CrawlService)
      .find(id)
      .pipe(
        mergeMap((crawl: HttpResponse<ICrawl>) => {
          if (crawl.body) {
            return of(crawl.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default crawlResolve;
