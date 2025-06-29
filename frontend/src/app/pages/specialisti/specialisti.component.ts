import { Component, OnInit } from '@angular/core';
import { CommonModule }       from '@angular/common';
import { HttpClientModule }   from '@angular/common/http';

import { SpecialistService }   from '../../specialist.service';
import { SpecialistCuNumeDto } from '../../models/specialistCuNume.dto';
import { ServiciuDto }         from '../../models/serviciu.dto';
import { MatCardModule }            from '@angular/material/card';
import { MatButtonModule }          from '@angular/material/button';
import { MatIconModule }            from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatGridListModule }        from '@angular/material/grid-list';
import { MatDividerModule }         from '@angular/material/divider';
import { MatListModule }            from '@angular/material/list';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ComandaDialogComponent } from '../comenzi/comanda-dialog/comanda-dialog.component';
import { PopupComponent }           from '../../shared/popup/popup.component';


@Component({
  selector: 'app-specialisti',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,

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
  templateUrl: './specialisti.component.html',
  styleUrls: ['./specialisti.component.css']
})
export class SpecialistiComponent implements OnInit {
  specialisti: SpecialistCuNumeDto[] = [];
  isLoading = true;
  errorMsg?: string;

  expanded = new Set<number>();

  showSuccessPopup = false;
  popupMessage = '';

  constructor(
  private svc: SpecialistService,
   private dialog: MatDialog
   ) {}

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.isLoading = true;
    this.errorMsg = undefined;
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

  toggle(specialistId: number) {
    if (this.expanded.has(specialistId)) this.expanded.delete(specialistId);
    else this.expanded.add(specialistId);
  }

  isExpanded(specialistId: number): boolean {
    return this.expanded.has(specialistId);
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
