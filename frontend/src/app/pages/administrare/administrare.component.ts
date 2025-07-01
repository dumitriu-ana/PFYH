import { Component, OnInit }        from '@angular/core';
import { CommonModule }             from '@angular/common';
import { FormsModule }              from '@angular/forms';
import { HttpClientModule }         from '@angular/common/http';

import { SpecialistService }        from '../../specialist.service';
import { SpecialistAdminDto }       from '../../models/specialist-admin.dto';
import { AuthService }              from '../../services/auth.service';
import { SpecializareService }      from '../../specializare.service';
import { SpecializareDto }          from '../../models/specializare.dto';
import { ServiciiService }          from '../../servicii.service';
import { ServiciuDto }              from '../../models/serviciu.dto';
import { GraficServiciiComponent } from './grafic-servicii/grafic-servicii.component';

@Component({
  selector: 'app-administrare',
  standalone: true,
  imports: [ CommonModule, HttpClientModule, FormsModule, GraficServiciiComponent ],
  templateUrl: './administrare.component.html'
})
export class AdministrareComponent implements OnInit {
  // liste specialiști
  listaInProces: SpecialistAdminDto[] = [];
  listaValidati : SpecialistAdminDto[] = [];

  // formular specializări
  newSpecializare = '';
  specializari: SpecializareDto[] = [];
  successMsgSpz?: string;
  errorMsgSpz?: string;

  // formular servicii
  newServiciu: Partial<ServiciuDto> = {
    idSpecializare: null,
    titlu: '',
    tipServiciu: '',
    pret: 0,
    durataRezolvare: '',
    durataSedinta: '',
    numarMaxCaractere: 0
  };
  successMsgSrv?: string;
  errorMsgSrv?: string;

  isLoading = true;
  errorMsg?: string;

  constructor(
    private svc:     SpecialistService,
    private svcSpz:  SpecializareService,
    private svcSrv:  ServiciiService,
    private auth:    AuthService
  ) {}

  ngOnInit(): void {
    this.load();
    this.loadSpecializari();
  }

  private load(): void {
    this.isLoading = true;
    this.errorMsg  = undefined;
    this.svc.getAllForAdmin().subscribe({
      next: data => {
        this.listaInProces = data.filter(sp => sp.statusValidare === 'IN_PROCES');
        this.listaValidati  = data.filter(sp => sp.statusValidare  === 'VALIDAT');
        this.isLoading      = false;
      },
      error: err => {
        console.error('Eroare încărcare specialiști:', err);
        this.errorMsg  = 'Nu am putut încărca specialiștii.';
        this.isLoading = false;
      }
    });
  }

  private loadSpecializari(): void {
    this.svcSpz.getSpecializari().subscribe({
      next: list => this.specializari = list,
      error: _    => console.error('Nu am putut încărca specializările.')
    });
  }

onValidate(sp: SpecialistAdminDto) {
  const adminId = this.auth.getCurrentUser()!.id;
  this.svc.validateAndPromote(sp.id, adminId).subscribe({
    next: () => this.load(),
    error: err => console.error('Validare eșuată', err)
  });
}

  addSpecializare(): void {
    this.successMsgSpz = undefined;
    this.errorMsgSpz   = undefined;
    this.svcSpz.createSpecializare({ denumire: this.newSpecializare }).subscribe({
      next: sp => {
        this.successMsgSpz    = `Specializare „${sp.denumire}” creată (ID: ${sp.id})`;
        this.newSpecializare = '';
        this.loadSpecializari();
      },
      error: err => {
        this.errorMsgSpz = err.error?.message || 'Eroare la adăugarea specializării.';
      }
    });
  }

  addServiciu(): void {
    this.successMsgSrv = undefined;
    this.errorMsgSrv   = undefined;

      const payload: ServiciuDto = { ...this.newServiciu as ServiciuDto };

      console.log('Payload efectiv trimis:', payload);

    this.svcSrv.createServiciu(this.newServiciu as ServiciuDto).subscribe({
      next: srv => {
        this.successMsgSrv = `Serviciu „${srv.titlu}” creat (ID: ${srv.id})`;
        // resetează câmpurile
        this.newServiciu = {
          idSpecializare: null,
          titlu: '',
          tipServiciu: '',
          pret: 0,
          durataRezolvare: '',
          durataSedinta: '',
          numarMaxCaractere: 0
        };
      },
      error: err => {
        this.errorMsgSrv = err.error?.message || 'Eroare la crearea serviciului.';
      }
    });
  }
}
