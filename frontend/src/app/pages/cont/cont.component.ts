// src/app/pages/cont/cont.component.ts
import { Component, OnInit }  from '@angular/core';
import { CommonModule }        from '@angular/common';
import { FormsModule }         from '@angular/forms';
import { Router }              from '@angular/router';

import { AuthService }         from '../../services/auth.service';
import { SpecialistService }   from '../../specialist.service';
import { SpecializareService } from '../../specializare.service';

import { UtilizatorDto }       from '../../models/utilizator.dto';
import { SpecializareDto }     from '../../models/specializare.dto';
import { SpecialistFullDto  }   from '../../models/specialistFull.dto';
import { SpecialistCuNumeDto } from '../../models/specialistCuNume.dto';

@Component({
  selector: 'app-cont',
  standalone: true,
  imports: [ CommonModule, FormsModule ],
  templateUrl: './cont.component.html',
  styleUrls: ['./cont.component.css']
})
export class ContComponent implements OnInit {
  user?: UtilizatorDto;

  specializari: SpecializareDto[]        = [];
  mySpecialist: SpecialistFullDto | null = null;

  showForm = false;
  specialistDto: Partial<SpecialistFullDto> = {
    specializareId: 0,
    serviciuIds:    [],
    atestat:        '',
    statusValidare: 'IN_PROCES',
    descriere:      '',
    soldAcumulat:   '0.00',
    idAdmin:        null,
    dataValidare:   null
  };

  errorMsg?:   string;
  successMsg?: string;

  constructor(
    private auth:    AuthService,
    private svcSpz:  SpecializareService,
    private svcSpec: SpecialistService,
    private router:  Router
  ) {}

  ngOnInit() {
    this.user = this.auth.getCurrentUser()!;
    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }

    this.svcSpz.getSpecializari().subscribe({
      next: list => this.specializari = list,
      error: _    => console.error('Nu am putut încărca specializările')
    });

    // **noul apel**: preluăm toți specialiștii „full” și găsim pe al nostru
    this.svcSpec.getAllSpecialistiFull().subscribe({
      next: list => {
        this.mySpecialist = list.find(s => s.idUtilizator === this.user!.id) || null;
      },
      error: err => console.error('Eroare la încărcarea specialistului', err)
    });
  }

  toggleForm() {
    this.showForm   = !this.showForm;
    this.errorMsg   = undefined;
    this.successMsg = undefined;
    this.specialistDto.idUtilizator = this.user!.id;
  }

  submitSpecialist() {
    if (!this.user) return;

    const body: SpecialistFullDto = {
      id:              0,
      idUtilizator:    this.user.id,
      specializareId:  this.specialistDto.specializareId!,
      serviciuIds:     this.specialistDto.serviciuIds!,
      atestat:         this.specialistDto.atestat!,
      statusValidare:  'IN_PROCES',
      descriere:       this.specialistDto.descriere!,
      soldAcumulat:    '0.00',
      idAdmin:         null,
      dataValidare:    null
    };

    this.svcSpec.createSpecialist(body).subscribe({
      next: spec => {
        this.successMsg    = 'Cerere trimisă! Vei fi notificat când eşti validat.';
        this.mySpecialist  = spec;
        this.showForm      = false;
      },
      error: err => {
        this.errorMsg = err.error?.message || 'Eroare la trimiterea cererii.';
      }
    });
  }

  getSpecializareDenumire(id: number): string {
    const sp = this.specializari.find(s => s.id === id);
    return sp ? sp.denumire : '–';
  }

  isClient(): boolean {
    return this.user?.tipUtilizator?.toLowerCase() === 'client';
  }
}
