// src/app/pages/servicii/servicii.component.ts

import { Component, OnInit }   from '@angular/core';
import { CommonModule }         from '@angular/common';
import { HttpClientModule }     from '@angular/common/http';
import { RouterLink }           from '@angular/router';

import { ServiciiService }      from '../../servicii.service';
import { SpecialistService }    from '../../specialist.service';
import { ServiciuDto }          from '../../models/serviciu.dto';
import { SpecialistCuNumeDto }  from '../../models/specialistCuNume.dto';

@Component({
  selector: 'app-servicii',
  standalone: true,
  imports: [ CommonModule, HttpClientModule, RouterLink ],
  templateUrl: './servicii.component.html',
  styleUrls: ['./servicii.component.css']
})
export class ServiciiComponent implements OnInit {
  servicii: ServiciuDto[] = [];
  loadingServicii = true;
  errorServicii?: string;

  specialistiMap: Record<number, SpecialistCuNumeDto[]> = {};
  openServicii = new Set<number>();
  loadingSpecialisti = new Set<number>();

  constructor(
    private svcServ: ServiciiService,
    private svcSpec: SpecialistService
  ) {}

  ngOnInit() {
    this.svcServ.getServicii().subscribe({
      next: (data: ServiciuDto[]) => {
        this.servicii = data;
        this.loadingServicii = false;
      },
      error: (err: any) => {
        console.error('Eroare incarcare servicii', err);
        this.errorServicii = 'Nu am putut incarca serviciile';
        this.loadingServicii = false;
      }
    });
  }

  toggleSpecialisti(serviciuId: number) {
    if (this.openServicii.has(serviciuId)) {
      this.openServicii.delete(serviciuId);
      delete this.specialistiMap[serviciuId];
    } else {
      this.loadingSpecialisti.add(serviciuId);
      this.svcSpec.getByService(serviciuId).subscribe({
        next: (list: SpecialistCuNumeDto[]) => {
          this.specialistiMap[serviciuId] = list;
          this.openServicii.add(serviciuId);
          this.loadingSpecialisti.delete(serviciuId);
        },
        error: (err: any) => {
          console.error(`Eroare la încărcare specialiști pentru serviciu ${serviciuId}`, err);
          this.loadingSpecialisti.delete(serviciuId);
        }
      });
    }
  }

  isOpen(serviciuId: number): boolean {
    return this.openServicii.has(serviciuId);
  }
}
