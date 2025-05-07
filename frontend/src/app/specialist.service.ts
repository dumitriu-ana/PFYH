import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SpecialistDto } from './models/specialist.dto';

@Injectable({
  providedIn: 'root'
})
export class SpecialistService {
  private apiUrl = 'http://localhost:8083/api/specialisti'; // Asigură-te că URL-ul este corect

  constructor(private http: HttpClient) { }

  getSpecialisti(): Observable<SpecialistDto[]> {
    return this.http.get<SpecialistDto[]>(this.apiUrl);
  }
}
