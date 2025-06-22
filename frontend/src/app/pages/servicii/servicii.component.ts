import { Component, OnInit }   from '@angular/core';
import { CommonModule }         from '@angular/common';
import { HttpClientModule }     from '@angular/common/http';
import { RouterLink }           from '@angular/router';
import { ServiciiService }      from '../../servicii.service';
import { SpecialistService }    from '../../specialist.service';
import { ServiciuDto }          from '../../models/serviciu.dto';
import { SpecialistCuNumeDto }  from '../../models/specialistCuNume.dto';
import { MatCardModule }            from '@angular/material/card';
import { MatButtonModule }          from '@angular/material/button';
import { MatIconModule }            from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatGridListModule }        from '@angular/material/grid-list';
import { MatDividerModule }         from '@angular/material/divider';
import { MatListModule }            from '@angular/material/list';

import { MatDialog } from '@angular/material/dialog';
import { ComandaDialogComponent } from '../comenzi/comanda-dialog/comanda-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import { PopupComponent }           from '../../shared/popup/popup.component';

@Component({
  selector: 'app-servicii',
  standalone: true,
  imports: [ CommonModule, HttpClientModule, RouterLink,   MatProgressSpinnerModule, MatGridListModule, MatCardModule,
            MatButtonModule, MatIconModule, MatDividerModule, MatListModule,
             MatDialogModule, ComandaDialogComponent,   PopupComponent, ],
  templateUrl: './servicii.component.html',
  styleUrls: ['./servicii.component.css']
})
export class ServiciiComponent implements OnInit {
  servicii: ServiciuDto[] = [];
  loadingServicii = true;
  errorServicii?: string;

  specialistiMap: Record<number, SpecialistCuNumeDto[]> = {};
  openServicii = new Set<number>();
  loadingSpecialisti = new Set<number>();

  showSuccessPopup = false;
  popupMessage = '';

  constructor(
    private svcServ: ServiciiService,
    private svcSpec: SpecialistService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.svcServ.getServicii().subscribe({
      next: (data: ServiciuDto[]) => {
        this.servicii = data;
        this.loadingServicii = false;
      },
      error: (err: any) => {
        console.error('Eroare la incarcare servicii', err);
        this.errorServicii = 'Nu s-au incarcat serviciile';
        this.loadingServicii = false;
      }
    });
  }

  toggleSpecialisti(serviciuId: number) {
    if (this.openServicii.has(serviciuId)) {
      this.openServicii.delete(serviciuId);
      delete this.specialistiMap[serviciuId];
    } else {
      this.loadingSpecialisti.add(serviciuId);
      this.svcSpec.getByService(serviciuId).subscribe({
        next: (list: SpecialistCuNumeDto[]) => {
          this.specialistiMap[serviciuId] = list;
          this.openServicii.add(serviciuId);
          this.loadingSpecialisti.delete(serviciuId);
        },
        error: (err: any) => {
          console.error(`Eroare la incarcare specialisti pentru serviciu ${serviciuId}`, err);
          this.loadingSpecialisti.delete(serviciuId);
        }
      });
    }
  }
  isOpen(serviciuId: number): boolean {
    return this.openServicii.has(serviciuId);
  }

deschideDialog(serviciu: ServiciuDto, specialist: SpecialistCuNumeDto) {
  const dialogRef = this.dialog.open(ComandaDialogComponent, {
    width: '600px',
    data: { serviciu, specialist }
  });

  dialogRef.afterClosed().subscribe(result => {
    if (result) {
       this.popupMessage = 'Comanda a fost plasată cu succes!';
       this.showSuccessPopup = true;
    }
  });
}

closePopupAndNavigate() {
   this.showSuccessPopup = false;
  }
}
