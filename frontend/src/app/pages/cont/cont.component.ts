// src/app/pages/cont/cont.component.ts
import { Component, OnInit }   from '@angular/core';
import { CommonModule }        from '@angular/common';
import { AuthService }         from '../../services/auth.service';
import { UtilizatorDto }       from '../../models/utilizator.dto';

@Component({
  selector: 'app-cont',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="cont-container">
      <h2>Contul meu: {{ user?.nume }}</h2>
      <dl>
        <dt>ID</dt><dd>{{ user?.id }}</dd>
        <dt>Email</dt><dd>{{ user?.email }}</dd>
        <dt>Data înregistrării</dt><dd>{{ user?.dataInreg | date:'medium' }}</dd>
        <dt>Tip Utilizator</dt><dd>{{ user?.tipUtilizator }}</dd>
        <dt>Sold</dt><dd>{{ user?.sold | currency:'RON' }}</dd>
      </dl>
    </div>
  `,
  styles: [`
    .cont-container {
      max-width: 600px;
      margin: 2rem auto;
      padding: 1.5rem;
      border: 2px solid #2e7d32;
      border-radius: 8px;
      background: #f9fff9;
    }
    h2 {
      color: #2e7d32; font-weight: bold; text-align: center;
    }
    dl { display: grid; grid-template-columns: auto 1fr; row-gap: .5rem; column-gap: 1rem; }
    dt { font-weight: bold; color: #005f00; }
    dd { margin: 0; }
  `]
})
export class ContComponent implements OnInit {
  user?: UtilizatorDto;
  constructor(private auth: AuthService) {}
  ngOnInit() {
    this.user = this.auth.getCurrentUser()!;
  }
}
