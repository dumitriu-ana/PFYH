import { Component, OnInit } from '@angular/core';
import { CommonModule }      from '@angular/common';
import { HttpClientModule }  from '@angular/common/http';

import { SpecialistService }                       from '../../specialist.service';
import { SpecialistAdminDto }        from '../../models/specialist-admin.dto';

@Component({
  selector: 'app-administrare',
  standalone: true,
  imports: [CommonModule, HttpClientModule],
  templateUrl: './administrare.component.html'
})
export class AdministrareComponent implements OnInit {
  // două liste separate
  listaInProces: SpecialistAdminDto[] = [];
  listaValidati: SpecialistAdminDto[] = [];

  isLoading = true;
  errorMsg?: string;

  constructor(private svc: SpecialistService) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.isLoading = true;
    this.errorMsg = undefined;
    this.svc.getAllForAdmin().subscribe({
      next: (data: SpecialistAdminDto[]) => {
        // separăm în cele două tabele
        this.listaInProces = data.filter(sp => sp.statusValidare === 'IN_PROCES');
        this.listaValidati  = data.filter(sp => sp.statusValidare  === 'VALIDAT');
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
