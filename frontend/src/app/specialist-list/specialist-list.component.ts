import { Component, Input, OnInit, OnChanges, SimpleChanges } from '@angular/core';
import { NgIf, NgForOf } from '@angular/common';
import { RouterLink } from '@angular/router';
import { SpecialistCuNumeDto } from '../models/specialistCuNume.dto';


@Component({
  selector: 'app-specialist-list',
  standalone: true,
  imports: [NgIf, NgForOf, RouterLink],
  templateUrl: './specialist-list.component.html',
  styleUrls: ['./specialist-list.component.css']
})

export class SpecialistListComponent implements OnInit, OnChanges {
  @Input() specialistiCuNume: SpecialistCuNumeDto[] | null = [];

  toate: SpecialistCuNumeDto[] = [];
  paginaCurenta: SpecialistCuNumeDto[] = [];
  pagina = 1;
  itemsPerPage = 6;
  totalPagini = 0;
  pagini: number[] = [];

  ngOnInit() {
    this.setupPagination();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['specialistiCuNume'] && this.specialistiCuNume) {
      this.toate = this.specialistiCuNume;
      this.setupPagination();
    }
  }

  private setupPagination() {
    this.totalPagini = Math.ceil(this.toate.length / this.itemsPerPage);
    this.pagini = Array.from({ length: this.totalPagini }, (_, i) => i + 1);
    this.schimbaPagina(1);
  }

  schimbaPagina(nr: number) {
    if (nr < 1 || nr > this.totalPagini) return;
    this.pagina = nr;
    const start = (nr - 1) * this.itemsPerPage;
    this.paginaCurenta = this.toate.slice(start, start + this.itemsPerPage);
  }
}
