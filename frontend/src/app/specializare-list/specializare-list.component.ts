import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { SpecializareDto } from '../models/specializare.dto';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-specializare-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './specializare-list.component.html',
  styleUrls: ['./specializare-list.component.css']
})
export class SpecializareListComponent implements OnChanges {
  @Input() specializari: SpecializareDto[] = [];


  pagina = 1;
  itemsPerPage = 8;
  totalPagini = 0;
  pagini: number[] = [];
  paginaCurenta: SpecializareDto[] = [];

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['specializari']) {
      this.setupPagination();
    }
  }

  private setupPagination(): void {
    this.totalPagini = Math.ceil(this.specializari.length / this.itemsPerPage);
    this.pagini = Array.from({ length: this.totalPagini }, (_, i) => i + 1);
    this.changePage(1);
  }

  changePage(p: number): void {
    if (p < 1 || p > this.totalPagini) return;
    this.pagina = p;
    const start = (p - 1) * this.itemsPerPage;
    this.paginaCurenta = this.specializari.slice(start, start + this.itemsPerPage);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
