import { Component, OnInit }        from '@angular/core';
import { CommonModule }             from '@angular/common';
import { HttpClientModule }         from '@angular/common/http';
import { RouterLink }               from '@angular/router';

import { SpecializareService }      from '../../specializare.service';
import { SpecialistService }        from '../../specialist.service';
import { SpecializareDto }          from '../../models/specializare.dto';
import { SpecialistCuNumeDto }      from '../../models/specialistCuNume.dto';


import { MatCardModule }            from '@angular/material/card';
import { MatButtonModule }          from '@angular/material/button';
import { MatIconModule }            from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatGridListModule }        from '@angular/material/grid-list';
import { MatDividerModule }         from '@angular/material/divider';
import { MatListModule }            from '@angular/material/list';

@Component({
  selector: 'app-specializari',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    RouterLink,

    MatProgressSpinnerModule,
    MatGridListModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
  ],
  templateUrl: './specializari.component.html',
  styleUrls: ['./specializari.component.css']
})
export class SpecializariComponent implements OnInit {
  specializari: SpecializareDto[] = [];

  specialistiMap: Record<number, SpecialistCuNumeDto[]> = {};
  openSpecializari = new Set<number>();
  openServicii = new Set<number>();

  loadingSpecializari = new Set<number>();

  constructor(
    private svcSp: SpecializareService,
    private svcSpec: SpecialistService
  ) {}

  ngOnInit() {
    this.svcSp.getSpecializari().subscribe({
      next: data => this.specializari = data,
      error: err => console.error('nu am putut incarca specializările', err)
    });
  }

  toggleSpecialisti(spId: number) {
    if (this.openSpecializari.has(spId)) {
      this.openSpecializari.delete(spId);
      delete this.specialistiMap[spId];
    } else {
      this.loadingSpecializari.add(spId);
      this.svcSpec.getSpecialisti(spId).subscribe({
        next: list => {
          this.specialistiMap[spId] = list;
          this.openSpecializari.add(spId);
          this.loadingSpecializari.delete(spId);
        },
        error: err => {
          console.error(`nu am putut încărca specialiști pentru sp ${spId}`, err);
          this.loadingSpecializari.delete(spId);
        }
      });
    }
  }

  isOpen(spId: number): boolean {
    return this.openSpecializari.has(spId);
  }

  /**
   * Nivel 2 → 3: toggle tabel servicii pentru un specialist
   */
  toggleServicii(specId: number) {
    if (this.openServicii.has(specId)) {
      this.openServicii.delete(specId);
    } else {
      this.openServicii.add(specId);
    }
  }

  isServiciiOpen(specId: number): boolean {
    return this.openServicii.has(specId);
  }

  /**
   * Buton “Afișează TOT”: deschide toate specializările (dar serviciile rămân închise)
   */
  expandAll() {
    this.specializari.forEach(sp => {
      if (!this.isOpen(sp.id)) {
        this.toggleSpecialisti(sp.id);
      }
    });
  }
}
