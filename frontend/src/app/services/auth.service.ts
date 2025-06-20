// src/app/services/auth.service.ts
import { Injectable }       from '@angular/core';
import { HttpClient }       from '@angular/common/http';
import { environment }      from '../../environments/environment';
import { LoginRequest }     from '../models/login-request.model';
import { RegisterRequest }  from '../models/register-request.model';
import { AuthResponse }     from '../models/auth-response.model';
import { UtilizatorDto }    from '../models/utilizator.dto';
import { Observable }       from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private base      = `${environment.apiUrl}/api/auth`;
  private readonly TOKEN_KEY = 'JWT';
  private readonly USER_KEY  = 'USER';

  constructor(private http: HttpClient) {}

  register(req: RegisterRequest): Observable<AuthResponse> {   //register
    return this.http.post<AuthResponse>(`${this.base}/register`, req);
  }

  login(req: LoginRequest): Observable<AuthResponse> {    //login
    return this.http.post<AuthResponse>(`${this.base}/login`, req);
  }

  handleAuthResponse(res: AuthResponse) {            //salvare user si token
    localStorage.setItem(this.TOKEN_KEY, res.token);
    localStorage.setItem(this.USER_KEY, JSON.stringify(res.user));
  }

  getCurrentUser(): UtilizatorDto | null {
    const str = localStorage.getItem(this.USER_KEY);
    if (!str || str === 'undefined') {
      return null;
    }
    try {
      return JSON.parse(str) as UtilizatorDto;
    } catch {
      return null;
    }
  }

  /** verifică existența unui token */
  hasToken(): boolean {
    return !!localStorage.getItem(this.TOKEN_KEY);
  }

  /** logout */
  clearToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
  }
}
