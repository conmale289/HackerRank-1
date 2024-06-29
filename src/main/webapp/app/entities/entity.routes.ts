import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'dmmtiktokApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'product',
    data: { pageTitle: 'dmmtiktokApp.product.home.title' },
    loadChildren: () => import('./product/product.routes'),
  },
  {
    path: 'image',
    data: { pageTitle: 'dmmtiktokApp.image.home.title' },
    loadChildren: () => import('./image/image.routes'),
  },
  {
    path: 'crawl',
    data: { pageTitle: 'dmmtiktokApp.crawl.home.title' },
    loadChildren: () => import('./crawl/crawl.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
