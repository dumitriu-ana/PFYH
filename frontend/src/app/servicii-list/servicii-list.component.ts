import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule, NgForOf, NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ServiciuDto } from '../models/serviciu.dto';

@Component({
  selector: 'app-servicii-list',
  standalone: true,
  imports: [CommonModule, NgForOf, NgIf, RouterLink],
  templateUrl: './servicii-list.component.html',
  styleUrls: ['./servicii-list.component.css']
})
export class ServiciiListComponent implements OnInit, OnChanges {
  @Input() servicii: ServiciuDto[] | null = [];

  // paginare
  toate: ServiciuDto[] = [];
  paginaCurenta: ServiciuDto[] = [];
  pagina = 1;
  itemsPerPage = 6;
  totalPagini = 0;
  pagini: number[] = [];

  ngOnInit() {
    this.setupPagination();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['servicii'] && this.servicii) {
      this.toate = this.servicii;
      this.setupPagination();
    }
  }

  private setupPagination() {
    this.totalPagini = Math.ceil(this.toate.length / this.itemsPerPage);
    this.pagini = Array.from({ length: this.totalPagini }, (_, i) => i + 1);
    this.schimbaPagina(1);
  }

  schimbaPagina(num: number) {
    if (num < 1 || num > this.totalPagini) return;
    this.pagina = num;
    const start = (num - 1) * this.itemsPerPage;
    this.paginaCurenta = this.toate.slice(start, start + this.itemsPerPage);
  }
}
