import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServiciuDto } from '../models/serviciu.dto';

@Injectable({ providedIn: 'root' })
export class ServiciiService {
  private apiUrl = 'http://localhost:8082/api/servicii';

  constructor(private http: HttpClient) {}

  getServicii(): Observable<ServiciuDto[]> {
    return this.http.get<ServiciuDto[]>(this.apiUrl);
  }
}
