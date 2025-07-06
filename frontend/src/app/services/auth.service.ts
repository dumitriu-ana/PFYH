import { Injectable }       from '@angular/core';
import { HttpClient }       from '@angular/common/http';
import { environment }      from '../../environments/environment';
import { LoginRequest }     from '../models/login-request.model';
import { RegisterRequest }  from '../models/register-request.model';
import { AuthResponse }     from '../models/auth-response.model';
import { UtilizatorDto }    from '../models/utilizator.dto';
import { Observable }       from 'rxjs';
import { BehaviorSubject, tap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private base      = `${environment.apiUrl}/api/auth`;
  private readonly TOKEN_KEY = 'JWT';
  private readonly USER_KEY  = 'USER';
  private userApiUrl = `${environment.apiUrl}/api/utilizatori`;
  private currentUserSubject: BehaviorSubject<UtilizatorDto | null>;
  public currentUser$: Observable<UtilizatorDto | null>;

  constructor(private http: HttpClient) {
   this.currentUserSubject = new BehaviorSubject<UtilizatorDto | null>(this.getCurrentUser());
   this.currentUser$ = this.currentUserSubject.asObservable();
   }

  register(req: RegisterRequest): Observable<AuthResponse> {   //register
    return this.http.post<AuthResponse>(`${this.base}/register`, req);
  }

  login(req: LoginRequest): Observable<AuthResponse> {    //login
    return this.http.post<AuthResponse>(`${this.base}/login`, req);
  }

  handleAuthResponse(res: AuthResponse) {            //salvare user si token
    localStorage.setItem(this.TOKEN_KEY, res.token);
    localStorage.setItem(this.USER_KEY, JSON.stringify(res.user));
    this.currentUserSubject.next(res.user);
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

  hasToken(): boolean {
    return !!localStorage.getItem(this.TOKEN_KEY);
  }

  clearToken(): void {
    localStorage.removeItem(this.TOKEN_KEY);
    localStorage.removeItem(this.USER_KEY);
    this.currentUserSubject.next(null);
  }

 public refreshUserData(): Observable<UtilizatorDto> | undefined {
    const currentUser = this.getCurrentUser();
    if (!currentUser) {
      return undefined;
    }
    return this.http.get<UtilizatorDto>(`${this.userApiUrl}/${currentUser.id}`).pipe(
      tap(freshUser => {
        localStorage.setItem(this.USER_KEY, JSON.stringify(freshUser));
        this.currentUserSubject.next(freshUser);
      })
    );
  }
}
