import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { saveAs } from 'file-saver';

// Material Modules
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatIconModule } from '@angular/material/icon';

// App Services and Models
import { ComenziService } from '../../services/comenzi.service';
import { AuthService } from '../../services/auth.service';
import { ComandaDto } from '../../models/comanda.dto';

@Component({
  selector: 'app-comenzi-specialist',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatIconModule
  ],
  templateUrl: './comenzi-specialist.component.html',
  styleUrls: ['./comenzi-specialist.component.css'] // Vom folosi un CSS similar
})
export class ComenziSpecialistComponent implements OnInit {
  comenzi: ComandaDto[] = [];
  loading = true;
  error: string | null = null;
  comandaDeschisa: number | null = null;

  constructor(
    private comenziService: ComenziService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    if (user && user.id) {
      this.loading = true;
      this.error = null;
      // Apelam noua metoda din serviciu
      this.comenziService.getComenziBySpecialistId(user.id).subscribe({
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
    } else {
      this.error = 'Utilizator neautentificat sau ID invalid.';
      this.loading = false;
    }
  }

  toggleComanda(id: number) {
    this.comandaDeschisa = this.comandaDeschisa === id ? null : id;
  }

  descarcaFisier(event: MouseEvent, comanda: ComandaDto, tipFisier: 'client' | 'specialist') {
    event.stopPropagation();
    if (!comanda.id) return;

    const numeFisier = tipFisier === 'client' ? comanda.numeFisierClient : comanda.numeFisierSpecialist;

    this.comenziService.descarcaFisier(comanda.id, tipFisier).subscribe({
      next: (blob) => {
        saveAs(blob, numeFisier);
      },
      error: (err) => {
        console.error(`Eroare la descarcarea fisierului de la ${tipFisier}`, err);
        alert('Fișierul nu a putut fi descărcat.');
      }
    });
  }

  // Aici se va adauga logica pentru schimbarea statusului comenzii
  schimbaStatus(comandaId: number, statusNou: string) {
    console.log(`Schimbă status pentru comanda ${comandaId} la ${statusNou}`);
    // TODO: Implementare apel serviciu pentru update status
  }
}
