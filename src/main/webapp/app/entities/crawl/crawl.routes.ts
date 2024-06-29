import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CrawlComponent } from './list/crawl.component';
import { CrawlDetailComponent } from './detail/crawl-detail.component';
import { CrawlUpdateComponent } from './update/crawl-update.component';
import CrawlResolve from './route/crawl-routing-resolve.service';

const crawlRoute: Routes = [
  {
    path: '',
    component: CrawlComponent,
    data: {},
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CrawlDetailComponent,
    resolve: {
      crawl: CrawlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CrawlUpdateComponent,
    resolve: {
      crawl: CrawlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CrawlUpdateComponent,
    resolve: {
      crawl: CrawlResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default crawlRoute;
