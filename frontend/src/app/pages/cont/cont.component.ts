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

@Component({
  selector: 'app-cont',
  standalone: true,
  imports: [ CommonModule, FormsModule ],
  templateUrl: './cont.component.html',
  styleUrls: ['./cont.component.css']
})
export class ContComponent implements OnInit {
  user?: UtilizatorDto;

  // for the „Devino specialist” form
  showForm = false;
  specializari: SpecializareDto[] = [];

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

  // feedback
  errorMsg?: string;
  successMsg?: string;

  constructor(
    private auth: AuthService,
    private svcSpec: SpecialistService,
    private svcSpz: SpecializareService,
    private router: Router
  ) {}

  ngOnInit() {
    // 1) must be logged in
    this.user = this.auth.getCurrentUser()!;
    if (!this.user) {
      this.router.navigate(['/login']);
      return;
    }

    // 2) fetch all specializări for dropdown
    this.svcSpz.getSpecializari().subscribe({
      next: list => this.specializari = list,
      error: err => console.error('Nu am putut încărca specializările', err)
    });
  }

  toggleForm() {
    this.showForm = !this.showForm;
    this.errorMsg = undefined;
    this.successMsg = undefined;
    if (this.user) {
      this.specialistDto.idUtilizator = this.user.id;
    }
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
      next: () => {
        this.successMsg = 'Cerere trimisă! Vei fi notificat când eşti validat.';
        this.showForm  = false;
      },
      error: err => {
        this.errorMsg = err.error?.message || 'Eroare la trimiterea cererii.';
      }
    });
  }
}
