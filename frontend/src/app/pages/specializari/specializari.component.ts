import { Component, OnInit }        from '@angular/core';
import { CommonModule }             from '@angular/common';
import { HttpClientModule }         from '@angular/common/http';
import { RouterLink }               from '@angular/router';

import { SpecializareService }      from '../../specializare.service';
import { SpecialistService }        from '../../specialist.service';
import { SpecializareDto }          from '../../models/specializare.dto';
import { SpecialistCuNumeDto }      from '../../models/specialistCuNume.dto';
import { ServiciuDto }              from '../../models/serviciu.dto';

import { MatCardModule }            from '@angular/material/card';
import { MatButtonModule }          from '@angular/material/button';
import { MatIconModule }            from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatGridListModule }        from '@angular/material/grid-list';
import { MatDividerModule }         from '@angular/material/divider';
import { MatListModule }            from '@angular/material/list';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ComandaDialogComponent }   from '../comenzi/comanda-dialog/comanda-dialog.component';
import { PopupComponent }           from '../../shared/popup/popup.component';

@Component({
  selector: 'app-specializari',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    RouterLink,

    MatProgressSpinnerModule,
    MatGridListModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatListModule,
    MatDialogModule,
    PopupComponent,
  ],
  templateUrl: './specializari.component.html',
  styleUrls: ['./specializari.component.css']
})
export class SpecializariComponent implements OnInit {
  specializari: SpecializareDto[] = [];

  specialistiMap: Record<number, SpecialistCuNumeDto[]> = {};
  openSpecializari = new Set<number>();
  openServicii = new Set<number>();

  loadingSpecializari = new Set<number>();

  showSuccessPopup = false;
  popupMessage = '';

  constructor(
    private svcSp: SpecializareService,
    private svcSpec: SpecialistService,
    private dialog: MatDialog,
  ) {}

  ngOnInit() {
    this.svcSp.getSpecializari().subscribe({
      next: data => this.specializari = data,
      error: err => console.error('nu am putut incarca specializările', err)
    });
  }

  toggleSpecialisti(spId: number) {
    if (this.openSpecializari.has(spId)) {
      this.openSpecializari.delete(spId);
      delete this.specialistiMap[spId];
    } else {
      this.loadingSpecializari.add(spId);
      this.svcSpec.getSpecialisti(spId).subscribe({
        next: list => {
          this.specialistiMap[spId] = list;
          this.openSpecializari.add(spId);
          this.loadingSpecializari.delete(spId);
        },
        error: err => {
          console.error(`nu am putut încărca specialiști pentru sp ${spId}`, err);
          this.loadingSpecializari.delete(spId);
        }
      });
    }
  }

  isOpen(spId: number): boolean {
    return this.openSpecializari.has(spId);
  }


  toggleServicii(specId: number) {
    if (this.openServicii.has(specId)) {
      this.openServicii.delete(specId);
    } else {
      this.openServicii.add(specId);
    }
  }

  isServiciiOpen(specId: number): boolean {
    return this.openServicii.has(specId);
  }

// buton afisare tot
  expandAll() {
    this.specializari.forEach(sp => {
      if (!this.isOpen(sp.id)) {
        this.toggleSpecialisti(sp.id);
      }
    });
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
