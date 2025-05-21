import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SpecialistFullDto } from './models/specialistFull.dto';
import { SpecialistCuNumeDto } from './models/specialistCuNume.dto';

@Injectable({
  providedIn: 'root'
})
export class SpecialistService {
  private apiUrl = 'http://localhost:8083/api/specialisti';

  constructor(private http: HttpClient) { }

  /** Creează un nou specialist */
  createSpecialist(s: Partial<SpecialistFullDto>): Observable<SpecialistFullDto> {
    return this.http.post<SpecialistFullDto>(this.apiUrl, s);
  }

  /** Preia toți specialiștii (opțional filtrați după specializare) */
  getSpecialisti(specializareId?: number): Observable<SpecialistCuNumeDto[]> {
    let url = `${this.apiUrl}/lista`;
    if (specializareId != null) {
      url += `?specializareId=${specializareId}`;
    }
    return this.http.get<SpecialistCuNumeDto[]>(url);
  }

  /** Preia specialiștii care oferă un anumit serviciu */
  getByService(serviciuId: number): Observable<SpecialistCuNumeDto[]> {
    const url = `${this.apiUrl}/lista/service/${serviciuId}`;
    return this.http.get<SpecialistCuNumeDto[]>(url);
  }
}
