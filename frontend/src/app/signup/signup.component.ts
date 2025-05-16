// src/app/signup/signup.component.ts
import { Component }           from '@angular/core';
import { CommonModule }        from '@angular/common';
import { FormsModule }         from '@angular/forms';
import { Router }              from '@angular/router';
import { AuthService }         from '../services/auth.service';
import { RegisterRequest }     from '../models/register-request.model';
import { PopupComponent }      from '../shared/popup/popup.component';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule, PopupComponent],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  nume     = '';
  email    = '';
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
        this.auth.handleAuthResponse(res);
        this.router.navigate(['/home']);
      },
      error: err => {
        this.errorMsg = err.error?.message || 'Eroare la înregistrare.';
      }
    });
  }

  onClosePopup() {
    this.errorMsg = undefined;
  }
}
