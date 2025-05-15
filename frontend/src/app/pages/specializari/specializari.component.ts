import { Component, OnInit }        from '@angular/core';
import { CommonModule }             from '@angular/common';
import { HttpClientModule }         from '@angular/common/http';
import { RouterLink }               from '@angular/router';

import { SpecializareService }      from '../../specializare.service';
import { SpecialistService }        from '../../specialist.service';
import { SpecializareDto }          from '../../models/specializare.dto';
import { SpecialistCuNumeDto }      from '../../models/specialistCuNume.dto';

@Component({
  selector: 'app-specializari',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    RouterLink
  ],
  templateUrl: './specializari.component.html',
  styleUrls: ['./specializari.component.css']
})
export class SpecializariComponent implements OnInit {
  specializari: SpecializareDto[] = [];

  /** map specializareId → lista de specialiști */
  specialistiMap: Record<number, SpecialistCuNumeDto[]> = {};

  /** ce specializări sunt extinse (nivelul 2) */
  openSpecializari = new Set<number>();

  /** ce specialiști au serviciile extinse (nivelul 3) */
  openServicii = new Set<number>();

  /** set de id-uri pentru care încărcăm specialiști (dezactivează butonul) */
  loadingSpecializari = new Set<number>();

  constructor(
    private svcSp: SpecializareService,
    private svcSpec: SpecialistService
  ) {}

  ngOnInit() {
    this.svcSp.getSpecializari().subscribe({
      next: data => this.specializari = data,
      error: err => console.error('nu am putut încărca specializările', err)
    });
  }

  /**
   * Nivel 1 → 2: toggle tabel specialiști pentru o specializare
   */
  toggleSpecialisti(spId: number) {
    if (this.openSpecializari.has(spId)) {
      // închidem: ştergem datele
      this.openSpecializari.delete(spId);
      delete this.specialistiMap[spId];
    } else {
      // deschidem: încărcăm
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
