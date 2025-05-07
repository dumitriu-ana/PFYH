// src/app/specialist.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SpecialistCuNumeDto } from './models/specialistCuNume.dto'; // Ajustează calea

@Injectable({
  providedIn: 'root'
})
export class SpecialistService {
  private apiUrlSpecialisti = 'http://localhost:8083/api/specialisti/lista';

  constructor(private http: HttpClient) { }

  getSpecialisti(): Observable<SpecialistCuNumeDto[]> {
    return this.http.get<SpecialistCuNumeDto[]>(this.apiUrlSpecialisti);
  }
}
