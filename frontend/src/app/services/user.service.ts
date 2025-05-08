import { Injectable } from '@angular/core';
import { HttpClient }  from '@angular/common/http';
import { Observable }  from 'rxjs';
import { UtilizatorDto } from '../models/utilizator.dto';

@Injectable({ providedIn: 'root' })
export class UserService {
  // Ajustează URL-ul către micro-serviciul tău de Utilizator
  private apiUrl = 'http://localhost:8088/api/utilizatori';

  constructor(private http: HttpClient) {}

  create(user: Partial<UtilizatorDto>): Observable<UtilizatorDto> {
    return this.http.post<UtilizatorDto>(this.apiUrl, user);
  }

  // poți adăuga și getById, updateImageUrl etc.
}
