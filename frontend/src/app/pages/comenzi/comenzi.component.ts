import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ComenziService } from '../../services/comenzi.service';
import { ComandaDto } from '../../models/comanda.dto';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-comenzi',
    standalone: true,
      imports: [ CommonModule ],
  templateUrl: './comenzi.component.html',
  styleUrls: ['./comenzi.component.css']
})
export class ComenziComponent implements OnInit {
  comenzi: ComandaDto[] = [];
  idUtilizator: number | null = null;
  comandaDeschisa: number | null = null;

  constructor(
    private comenziService: ComenziService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const user = this.authService.getCurrentUser();
    if (user) {
      this.idUtilizator = user.id;
      this.comenziService.getComenziByClientId(user.id).subscribe({
        next: (data) => this.comenzi = data,
        error: (err) => console.error('Eroare la încărcarea comenzilor:', err)
      });
    }
  }

  toggleComanda(id: number) {
    this.comandaDeschisa = this.comandaDeschisa === id ? null : id;
  }
}
