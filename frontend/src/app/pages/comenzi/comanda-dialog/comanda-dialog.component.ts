import { Component, OnInit, Inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatDividerModule } from '@angular/material/divider';

import { ComenziService } from '../../../services/comenzi.service';
import { AuthService } from '../../../services/auth.service';
import { TranzactieService } from '../../../services/tranzactie.service';
import { ComandaDto } from '../../../models/comanda.dto';
import { ServiciuDto } from '../../../models/serviciu.dto';
import { SpecialistCuNumeDto } from '../../../models/specialistCuNume.dto';

import { loadStripe, Stripe, StripeCardElement, StripeCardElementChangeEvent } from '@stripe/stripe-js';

@Component({
  selector: 'app-comanda-dialog',
  standalone: true,
  imports: [
    CommonModule, ReactiveFormsModule, MatDialogModule, MatFormFieldModule,
    MatInputModule, MatButtonModule, MatIconModule, MatProgressSpinnerModule, MatDividerModule
  ],
  templateUrl: './comanda-dialog.component.html',
  styleUrls: ['./comanda-dialog.component.css']
})
export class ComandaDialogComponent implements OnInit {
  form: FormGroup;
  fisierSelectat?: File;

  stripe: Stripe | null = null;
  cardElement: StripeCardElement | null = null;
  isLoading = false;
  cardError: string | null = null;

  constructor(
    private dialogRef: MatDialogRef<ComandaDialogComponent>,
    private fb: FormBuilder,
    private comenziService: ComenziService,
    private authService: AuthService,
    private tranzactieService: TranzactieService,
    @Inject(MAT_DIALOG_DATA) public data: { serviciu: ServiciuDto, specialist: SpecialistCuNumeDto }
  ) {
    this.form = this.fb.group({
      mesaj: [''],
      fisier: [null]
    });
  }

  async ngOnInit() {
//cheie Stripe pk

    if (this.stripe) {
      const elements = this.stripe.elements();
      this.cardElement = elements.create('card', { hidePostalCode: true });

      setTimeout(() => {
        this.cardElement?.mount('#card-element');
        this.cardElement?.on('change', (event: StripeCardElementChangeEvent) => {
          this.cardError = event.error ? event.error.message : null;
          const displayError = document.getElementById('card-errors');
          if (displayError) displayError.textContent = this.cardError;
        });
      }, 100);
    }
  }

  onFisierSelectat(event: any) {
    const file = event.target.files[0];
    if (file && file.type === 'application/pdf') {
      if (file.size > 10 * 1024 * 1024) {
        alert('Fișierul este prea mare. Dimensiunea maximă permisă este 10MB.');
        return;
      }
      this.fisierSelectat = file;
    }
  }

  async gestioneazaPlataSiComanda() {
    this.isLoading = true;
    if (!this.stripe || !this.cardElement || !this.data.serviciu.pret) {
      alert('Eroare la inițializarea plății. Vă rugăm reîncărcați pagina.');
      this.isLoading = false;
      return;
    }

    this.tranzactieService.createPaymentIntent(this.data.serviciu.pret).subscribe({
      next: async (response) => {
        const clientSecret = response.clientSecret;
        if (!clientSecret) {
          alert('Eroare server: nu s-a putut obține cheia de plată.');
          this.isLoading = false;
          return;
        }

        const result = await this.stripe!.confirmCardPayment(clientSecret, {
          payment_method: { card: this.cardElement! }
        });

        if (result.error) {
          this.cardError = result.error.message || 'A apărut o eroare la plată.';
          this.isLoading = false;
        } else if (result.paymentIntent?.status === 'succeeded') {
          this.creeazaComandaFinala();
        }
      },
      error: (err) => {
        alert('Eroare de comunicare cu serverul de tranzacții.');
        this.isLoading = false;
      }
    });
  }

  creeazaComandaFinala() {
    const currentUser = this.authService.getCurrentUser();
    if (!currentUser) {
      alert('Sesiune expirată. Vă rugăm să vă relogați.');
      this.isLoading = false;
      return;
    }

    const formData = new FormData();
    const comanda: Partial<ComandaDto> = {
      idClient: currentUser.id,
      idSpecialist: this.data.specialist.id,
      idServiciu: this.data.serviciu.id,
      pret: this.data.serviciu.pret,
      mesajClient: this.form.value.mesaj
    };
    formData.append('comanda', new Blob([JSON.stringify(comanda)], { type: 'application/json' }));
    if (this.fisierSelectat) {
      formData.append('fisier', this.fisierSelectat, this.fisierSelectat.name);
    }

    this.comenziService.creeazaComandaCuFisier(formData).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.dialogRef.close(response);
      },
      error: (err) => {
        alert('Plata a fost efectuată, dar a apărut o eroare la crearea comenzii. Vă rugăm contactați suportul.');
        this.isLoading = false;
      }
    });
  }

  inchide() {
    this.dialogRef.close();
  }
}
