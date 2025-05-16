// src/app/signup/signup.component.ts
import { Component }             from '@angular/core';
import { CommonModule }          from '@angular/common';
import { FormsModule }           from '@angular/forms';
import { Router }                from '@angular/router';
import { AuthService }           from '../services/auth.service';
import { RegisterRequest }       from '../models/register-request.model';
import { PopupComponent }        from '../shared/popup/popup.component';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    PopupComponent      // ← aici
  ],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  nume = '';
  email = '';
  password = '';
  errorMsg?: string;

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  signup() {
    const req: RegisterRequest = {
      nume: this.nume,
      email: this.email,
      password: this.password
    };

    this.auth.register(req).subscribe({
      next: res => {
        this.auth.saveToken(res.token);
        this.router.navigate(['/home']);
      },
      error: err => {
        if (err.status === 409 && err.error?.message) {
          // afișează popup cu mesajul „Email deja folosit”
          this.errorMsg = err.error.message;
        } else {
          this.errorMsg = 'A apărut o eroare la înregistrare.';
          console.error(err);
        }
      }
    });
  }

  onClosePopup() {
    this.errorMsg = undefined;
  }
}
