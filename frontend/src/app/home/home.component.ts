import { Component, OnInit } from '@angular/core';
import { SpecializareService } from '../specializare.service';
import { SpecializareDto } from '../models/specializare.dto';
import { NgIf, NgFor, NgClass } from '@angular/common';
import { SpecialistListComponent } from '../specialist-list/specialist-list.component'; // Importă SpecialistListComponent
import { SpecialistService } from '../specialist.service'; // Importă SpecialistService
import { SpecialistDto } from '../models/specialist.dto'; // Importă SpecialistDto

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgIf, NgFor, NgClass, SpecialistListComponent], // Asigură-te că SpecialistListComponent este importat
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  specializari: SpecializareDto[] = [];
  specialisti: SpecialistDto[] = []; // Adaugă proprietatea pentru lista de specialiști
  errorMessageSpecializari: string = '';
  errorMessageSpecialisti: string = ''; // Adaugă variabilă pentru erori de specialiști

  constructor(
    private specializareService: SpecializareService,
    private specialistService: SpecialistService // Injectează SpecialistService
  ) { }

  ngOnInit(): void {
    this.loadSpecializari();
    this.loadSpecialisti();
  }

  loadSpecializari(): void {
    this.specializareService.getSpecializari().subscribe({
      next: (specializari) => {
        this.specializari = specializari;
      },
      error: (error) => {
        this.errorMessageSpecializari = 'Eroare la încărcarea specializărilor.';
        console.error('Eroare încărcare specializări', error);
      }
    });
  }

  loadSpecialisti(): void {
    this.specialistService.getSpecialisti().subscribe({
      next: (specialisti) => {
        this.specialisti = specialisti;
      },
      error: (error) => {
        this.errorMessageSpecialisti = 'Eroare la încărcarea specialiștilor.';
        console.error('Eroare încărcare specialiști', error);
      }
    });
  }
}
