import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

import { SpecializareService } from '../specializare.service';
import { SpecializareDto } from '../models/specializare.dto';

import { SpecialistListComponent } from '../specialist-list/specialist-list.component';
import { SpecializareListComponent } from '../specializare-list/specializare-list.component';
import { HttpClientModule } from '@angular/common/http';

import { SpecialistService } from '../specialist.service';
import { SpecialistCuNumeDto } from '../models/specialistCuNume.dto'; // Asigură-te că această cale și nume de fișier sunt corecte


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
  //  secțiunea de specializări
  specializari: SpecializareDto[] = [];
  isLoadingSpecializari: boolean = true;
  errorMessageSpecializari: string = '';

  //  lista de specialiști
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
        this.errorMessageSpecializari = 'Eroare la încărcarea specializărilor.';
        console.error('Eroare încărcare specializări:', error);
        this.isLoadingSpecializari = false;
      }
    });
  }

  //
  loadSpecialistiPentruLista(): void {
    this.isLoadingSpecialisti = true;
    this.errorSpecialisti = null;
    this.specialistService.getSpecialisti().subscribe({
      next: (data) => {
        this.specialistiPentruLista = data;
        this.isLoadingSpecialisti = false;
      },
      error: (err) => {
        console.error("Eroare la încărcarea specialiștilor pentru listă în Home:", err);
        this.errorSpecialisti = "Nu s-au putut încărca specialiștii relevanți.";
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
      console.error('Eroare încărcare servicii:', err);
      this.errorServicii = 'Nu am putut prelua lista de servicii.';
      this.servicii = [];
      this.isLoadingServicii = false;
    }
  });
}

}
