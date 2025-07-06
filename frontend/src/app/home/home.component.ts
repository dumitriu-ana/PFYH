import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { SpecializareService } from '../specializare.service';
import { SpecializareDto } from '../models/specializare.dto';

import { SpecialistListComponent } from '../specialist-list/specialist-list.component';
import { SpecializareListComponent } from '../specializare-list/specializare-list.component';
import { HttpClientModule } from '@angular/common/http';

import { SpecialistService } from '../specialist.service';
import { SpecialistCuNumeDto } from '../models/specialistCuNume.dto';


import { ServiciiService } from '../servicii.service';
import { ServiciuDto } from '../models/serviciu.dto';
import { ServiciiListComponent } from '../servicii-list/servicii-list.component';

import { MatCardModule }            from '@angular/material/card';
import { MatButtonModule }          from '@angular/material/button';
import { MatIconModule }            from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatGridListModule }        from '@angular/material/grid-list';
import { MatDividerModule }         from '@angular/material/divider';
import { MatListModule }            from '@angular/material/list';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    HttpClientModule,
    SpecialistListComponent,
    ServiciiListComponent,
    SpecializareListComponent,

    MatProgressSpinnerModule,
    MatGridListModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  //  specializări
  specializari: SpecializareDto[] = [];
  isLoadingSpecializari: boolean = true;
  errorMessageSpecializari: string = '';

  //  lista specialisti
  specialistiPentruLista: SpecialistCuNumeDto[] | null = null;
  isLoadingSpecialisti: boolean = true;
  errorSpecialisti: string | null = null;

    //  servicii
    servicii: ServiciuDto[] | null = null;
    isLoadingServicii:boolean = true;
    errorServicii: string | null = null;

  constructor(
    private specializareService: SpecializareService,
    private specialistService: SpecialistService,
    private serviciiService: ServiciiService
  ) { }

  ngOnInit(): void {
    this.loadSpecializari();
    this.loadSpecialistiPentruLista();
        this.loadServicii();

  }

  loadSpecializari(): void {
    this.isLoadingSpecializari = true;
    this.errorMessageSpecializari = '';
    this.specializareService.getSpecializari().subscribe({
      next: (data) => {
        this.specializari = data;
        this.isLoadingSpecializari = false;
      },
      error: (error) => {
        this.errorMessageSpecializari = 'Eroare la incarcarea specializarilor.';
        console.error('Eroare la incarcarea specializarilor:', error);
        this.isLoadingSpecializari = false;
      }
    });
  }

  loadSpecialistiPentruLista(): void {
    this.isLoadingSpecialisti = true;
    this.errorSpecialisti = null;
    this.specialistService.getSpecialisti().subscribe({
      next: (data) => {
        this.specialistiPentruLista = data;
        this.isLoadingSpecialisti = false;
      },
      error: (err) => {
        console.error("Eroare la incarcarea specialiștilor pentru lista:", err);
        this.errorSpecialisti = "Eroare la incarcarea specialiștilor pentru lista";
        this.specialistiPentruLista = [];
        this.isLoadingSpecialisti = false;
      }
    });
  }


loadServicii(): void {
  this.isLoadingServicii = true;
  this.errorServicii = null;
  this.serviciiService.getServicii().subscribe({
    next: (data: ServiciuDto[]) => {
      this.servicii = data;
      this.isLoadingServicii = false;
    },
    error: (err: any) => {
      console.error('Eroare incarcarea serviciilor:', err);
      this.errorServicii = 'Eroare incarcarea serviciilor';
      this.servicii = [];
      this.isLoadingServicii = false;
    }
  });
}

}
