import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { RegisterRequest } from '../models/register-request.model';
import { AuthResponse }    from '../models/auth-response.model';
import { Observable }      from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private base = `${environment.apiUrl}/auth`;
  private readonly TOKEN_KEY = 'JWT';

  constructor(private http: HttpClient) {}

  register(req: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.base}/register`, req);
  }

  login(email: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(
      `${this.base}/login`,
      { email, password }
    );
  }

  saveToken(token: string): void {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  /** Returnează token-ul JWT sau null dacă nu există */
  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  /** Returnează true dacă există un JWT valid (cel puțin stocat) */
  hasToken(): boolean {
    return !!this.getToken();
  }

  /** Şterge token-ul din localStorage (logout) */
  clearToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
  }
}
