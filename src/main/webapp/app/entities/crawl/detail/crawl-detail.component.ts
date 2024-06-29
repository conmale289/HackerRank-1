import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { ICrawl } from '../crawl.model';

@Component({
  standalone: true,
  selector: 'jhi-crawl-detail',
  templateUrl: './crawl-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CrawlDetailComponent {
  crawl = input<ICrawl | null>(null);

  previousState(): void {
    window.history.back();
  }
}
