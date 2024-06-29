import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICrawl } from '../crawl.model';
import { CrawlService } from '../service/crawl.service';

@Component({
  standalone: true,
  templateUrl: './crawl-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CrawlDeleteDialogComponent {
  crawl?: ICrawl;

  protected crawlService = inject(CrawlService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.crawlService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
