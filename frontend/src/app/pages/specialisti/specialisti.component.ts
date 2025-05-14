import { Component, OnInit }            from '@angular/core';
import { CommonModule }                 from '@angular/common';
import { HttpClientModule }             from '@angular/common/http';

import { SpecialistService }            from '../../specialist.service';
import { SpecialistCuNumeDto }          from '../../models/specialistCuNume.dto';
import { SpecialistListComponent }      from '../../specialist-list/specialist-list.component';

@Component({
  selector: 'app-specialisti',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    SpecialistListComponent
  ],
  templateUrl: './specialisti.component.html',
  styleUrls: ['./specialisti.component.css']
})
export class SpecialistiComponent implements OnInit {
  specialisti: SpecialistCuNumeDto[] = [];
  isLoading = true;
  errorMsg?: string;

  constructor(private svc: SpecialistService) {}

  ngOnInit(): void {
    this.svc.getSpecialisti().subscribe({
      next: data => {
        this.specialisti = data;
        this.isLoading = false;
      },
      error: err => {
        console.error('Eroare încărcare specialiști:', err);
        this.errorMsg = 'Nu am putut încărca specialiștii.';
        this.isLoading = false;
      }
    });
  }
}
