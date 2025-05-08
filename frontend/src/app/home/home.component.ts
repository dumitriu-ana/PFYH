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



@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    HttpClientModule,
    SpecialistListComponent,
    ServiciiListComponent,
    SpecializareListComponent
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  // Pentru secțiunea de specializări
  specializari: SpecializareDto[] = [];
  isLoadingSpecializari: boolean = true;
  errorMessageSpecializari: string = '';

  // Pentru lista de specialiști pasată componentei SpecialistListComponent
  specialistiPentruLista: SpecialistCuNumeDto[] | null = null;
  isLoadingSpecialisti: boolean = true; // <-- Denumire corectată
  errorSpecialisti: string | null = null;   // <-- Denumire corectată

    // pentru servicii
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
    this.loadSpecialistiPentruLista(); // <-- Denumire corectată pentru apelul metodei
        this.loadServicii();               // << apel nou

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

  // Metoda pentru încărcarea specialiștilor pentru componenta listă
  loadSpecialistiPentruLista(): void { // <-- Denumire corectată a metodei
    this.isLoadingSpecialisti = true;   // Folosește proprietatea corectată
    this.errorSpecialisti = null;       // Folosește proprietatea corectată
    this.specialistService.getSpecialisti().subscribe({
      next: (data) => {
        this.specialistiPentruLista = data;
        this.isLoadingSpecialisti = false; // Folosește proprietatea corectată
      },
      error: (err) => {
        console.error("Eroare la încărcarea specialiștilor pentru listă în Home:", err);
        this.errorSpecialisti = "Nu s-au putut încărca specialiștii relevanți."; // Folosește proprietatea corectată
        this.specialistiPentruLista = [];
        this.isLoadingSpecialisti = false; // Folosește proprietatea corectată
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
