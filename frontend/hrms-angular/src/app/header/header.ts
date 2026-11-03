import { Component, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  imports: [MatIconModule, RouterLink, RouterLinkActive],
  selector: 'app-header',
  styleUrl: './header.scss',
  templateUrl: './header.html',
})
export class Header {
  readonly openModule = signal<string | null>('employee-service');

  toggleModule(module: string): void {
    this.openModule.update((open) => (open === module ? null : module));
  }
}
