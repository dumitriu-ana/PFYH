import { Component, Input } from '@angular/core';
import { SpecializareDto } from '../models/specializare.dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-specializare-list',
  standalone: true,
imports: [CommonModule],
  templateUrl: './specializare-list.component.html',
  styleUrls: ['./specializare-list.component.css']
})
export class SpecializareListComponent {
  @Input() specializari: SpecializareDto[] = [];

  grupuriSpecializari(): SpecializareDto[][] {
    const grupSize = 4;
    const grupuri: SpecializareDto[][] = [];
    for (let i = 0; i < this.specializari.length; i += grupSize) {
      grupuri.push(this.specializari.slice(i, i + grupSize));
    }
    return grupuri;
  }
}
