
import { Component, OnInit, OnDestroy }  from '@angular/core';
import { CommonModule }        from '@angular/common';
import { FormsModule }         from '@angular/forms';
import { Router }              from '@angular/router';
import { Subscription } from 'rxjs';

import { AuthService }         from '../../services/auth.service';
import { SpecialistService }   from '../../specialist.service';
import { SpecializareService } from '../../specializare.service';
import { ServiciiService }     from '../../servicii.service';
import { ServiciuDto }         from '../../models/serviciu.dto';

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
export class ContComponent implements OnInit, OnDestroy {
  user?: UtilizatorDto;
  private userSubscription?: Subscription;

  specializari: SpecializareDto[]        = [];
  mySpecialist: SpecialistFullDto | null = null;

  showAddTable = false;
  toateServiciile: ServiciuDto[] = [];

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
    private svcSrv:  ServiciiService,
    private router:  Router
  ) {}

  ngOnInit() {
    this.svcSrv.getServicii().subscribe(list => this.toateServiciile = list);

    this.userSubscription = this.auth.currentUser$.subscribe(user => {
      if (user) {
        this.user = user;
        // Re-incarcam datele de specialist daca user-ul se schimba
        if (this.user.tipUtilizator === 'specialist') {
          this.loadSpecialistData(this.user.id);
        }
      } else {
        this.router.navigate(['/login']);
      }
    });

    this.svcSpz.getSpecializari().subscribe({
      next: list => this.specializari = list,
      error: _    => console.error('Nu am putut încărca specializările')
    });

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
        this.successMsg    = 'Cerere trimisa.';
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

  get serviciiDisponibile(): ServiciuDto[] {
      if (!this.mySpecialist) {
        return [];
      }
      return this.toateServiciile.filter(
        srv => srv.idSpecializare === this.mySpecialist!.specializareId
      );
    }

    //serviciile existente
      get serviciiAdaugate(): ServiciuDto[] {
        if (!this.mySpecialist) return [];
        return this.toateServiciile
          .filter(srv => this.mySpecialist!.serviciuIds.includes(srv.id));
      }

//apel back pt elim serviciu
  onRemoveService(srv: ServiciuDto) {
    if (!this.mySpecialist) return;
    this.svcSpec
      .removeServiciuFromSpecialist(this.mySpecialist.id, srv.id)
      .subscribe(updated => this.mySpecialist = updated);
  }

  // --- Adaugă serviciu ---
    toggleAddTable() {
      this.showAddTable = !this.showAddTable;
    }
    isAlreadyAdded(srvId: number): boolean {
      return !!this.mySpecialist?.serviciuIds.includes(srvId);
    }
    onAddService(srv: ServiciuDto) {
      if (!this.mySpecialist) return;
      this.svcSpec.addServiciuToSpecialist(this.mySpecialist.id, srv.id)
        .subscribe(updated => this.mySpecialist = updated);
    }
  loadSpecialistData(userId: number) {
    this.svcSpec.getAllSpecialistiFull().subscribe({
      next: list => {
        this.mySpecialist = list.find(s => s.idUtilizator === userId) || null;
      },
      error: err => console.error('Eroare la încărcarea datelor de specialist', err)
    });
  }
   ngOnDestroy() {
      if (this.userSubscription) {
        this.userSubscription.unsubscribe();
      }
    }
}
