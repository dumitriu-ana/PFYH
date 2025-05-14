import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule }  from '@angular/forms';
import { Router }       from '@angular/router';
import { AuthService }  from '../services/auth.service';
import { RegisterRequest } from '../models/register-request.model';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './signup.component.html'
})
export class SignupComponent {
  nume = '';
  email = '';
  password = '';

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
      error: err => console.error('Signup error', err)
    });
  }
}
