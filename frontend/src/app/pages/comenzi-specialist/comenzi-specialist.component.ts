import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { saveAs } from 'file-saver';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatDividerModule } from '@angular/material/divider';

import { ComenziService } from '../../services/comenzi.service';
import { AuthService } from '../../services/auth.service';
import { ComandaDto } from '../../models/comanda.dto';
import { PopupComponent }           from '../../shared/popup/popup.component';

@Component({
  selector: 'app-comenzi-specialist',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, MatCardModule, MatButtonModule,
    MatProgressSpinnerModule, MatIconModule, MatFormFieldModule,
    MatInputModule, MatProgressBarModule, MatTooltipModule, MatDividerModule, PopupComponent,
  ],
  templateUrl: './comenzi-specialist.component.html',
  styleUrls: ['./comenzi-specialist.component.css']
})
export class ComenziSpecialistComponent implements OnInit {
  comenzi: ComandaDto[] = [];
  loading = true;
  error: string | null = null;

  comandaDeschisa: number | null = null;
  raspunsForm!: FormGroup;
  fisierSelectat?: File;
  isSubmitting = false;

  showSuccessPopup = false;
  popupMessage = '';

  constructor(
    private comenziService: ComenziService,
    private authService: AuthService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    if (user && user.id) {
      this.incarcaComenzi(user.id);
    } else {
      this.error = 'Utilizator neautentificat sau ID invalid.';
      this.loading = false;
    }

    this.raspunsForm = this.fb.group({
      raspuns: ['', Validators.required]
    });
  }

  incarcaComenzi(userId: number): void {
    this.loading = true;
    this.error = null;
    this.comenziService.getComenziBySpecialistId(userId).subscribe({
      next: (data) => {
        this.comenzi = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Eroare la încărcarea comenzilor de specialist:', err);
        this.error = 'Nu am putut încărca comenzile. Vă rugăm încercați din nou.';
        this.loading = false;
      }
    });
  }

  toggleDetalii(comanda: ComandaDto): void {
    if (comanda.id === undefined) return;
    if (this.comandaDeschisa === comanda.id) {
      this.comandaDeschisa = null;
    } else {
      this.comandaDeschisa = comanda.id;
      this.raspunsForm.reset();
      this.fisierSelectat = undefined;
      this.isSubmitting = false;
    }
  }

  onFisierSelectat(event: any): void {
    this.fisierSelectat = event.target.files[0];
  }

  finalizeazaComanda(comanda: ComandaDto): void {
    if (this.raspunsForm.invalid || !comanda.id) return;
    this.isSubmitting = true;

    const formData = new FormData();
    formData.append('raspuns', this.raspunsForm.get('raspuns')?.value);

    if (this.fisierSelectat) {
      formData.append('fisier', this.fisierSelectat, this.fisierSelectat.name);
    }

    this.comenziService.trimiteRaspuns(comanda.id, formData).subscribe({
      next: (comandaActualizata: ComandaDto) => {
        const index = this.comenzi.findIndex(c => c.id === comanda.id);
        if (index > -1) {
          this.comenzi[index] = comandaActualizata;
        }
        this.authService.refreshUserData()?.subscribe();
        this.isSubmitting = false;

        this.popupMessage = 'Comanda a fost finalizată și trimisă cu succes!';
        this.showSuccessPopup = true;
      },
      error: (err) => {
        console.error('Eroare la trimiterea raspunsului', err);
        alert('Nu s-a putut trimite raspunsul. Încercați din nou.');
        this.isSubmitting = false;
      }
    });
  }

  descarcaFisier(event: MouseEvent, comanda: ComandaDto, tipFisier: 'client' | 'specialist'): void {
    event.stopPropagation();
    if (!comanda.id) return;

    const numeFisier = tipFisier === 'client' ? comanda.numeFisierClient : comanda.numeFisierSpecialist;
    if (!numeFisier) return;

    this.comenziService.descarcaFisier(comanda.id, tipFisier).subscribe({
      next: (blob) => saveAs(blob, numeFisier),
      error: (err) => alert('Fișierul nu a putut fi descărcat.')
    });
  }

  closePopup(): void {
      this.showSuccessPopup = false;
    }
}


