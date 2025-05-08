import { Component }     from '@angular/core';
import { CommonModule }   from '@angular/common';
import { FormsModule }    from '@angular/forms';
import { Router }         from '@angular/router';

import { AuthService }    from '../services/auth.service';
import { UserService }    from '../services/user.service';
import { UtilizatorDto }  from '../models/utilizator.dto';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  nume = '';
  email = '';
  password = '';

  constructor(
    private auth: AuthService,
    private userSvc: UserService,
    private router: Router
  ) {}

  signup() {
    this.auth.signup(this.email, this.password).subscribe({
      next: cred => {
        const uid = cred.user.uid;
        const dto: Partial<UtilizatorDto> = {
          idFirebase:    uid,
          dataInreg:     new Date().toISOString(),
          nume:          this.nume,
          email:         this.email,
          parola:        null,
          tipUtilizator: 'client',
          sold:          0
        };
        this.userSvc.create(dto).subscribe({
          next: () => this.router.navigate(['/home']),
          error: err => console.error('Eroare la creare Utilizator:', err)
        });
      },
      error: err => console.error('Firebase signup error:', err)
    });
  }
}
