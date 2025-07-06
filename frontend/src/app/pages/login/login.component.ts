import { Component }       from '@angular/core';
import { CommonModule }    from '@angular/common';
import { FormsModule }     from '@angular/forms';
import { Router }          from '@angular/router';
import { AuthService }     from '../../services/auth.service';
import { LoginRequest }    from '../../models/login-request.model';
import { PopupComponent }  from '../../shared/popup/popup.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, PopupComponent],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email    = '';
  password = '';
  errorMsg?: string;

  constructor(
    private auth: AuthService,
    private router: Router
  ) {}

  login() {
    const req: LoginRequest = {
      email: this.email,
      password: this.password
    };

    this.auth.login(req).subscribe({
      next: res => {
        this.auth.handleAuthResponse(res);
        this.router.navigate(['/home']);
      },
      error: err => {
        this.errorMsg = err.error?.message || 'Credentialele folosite sunt invalide.';
      }
    });
  }

  onClosePopup() {
    this.errorMsg = undefined;
  }
}
