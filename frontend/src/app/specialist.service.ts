import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SpecialistFullDto } from './models/specialistFull.dto';
import { SpecialistCuNumeDto } from './models/specialistCuNume.dto';
import { SpecialistAdminDto } from './models/specialist-admin.dto';

@Injectable({
  providedIn: 'root'
})
export class SpecialistService {
  private apiUrl = 'http://localhost:8083/api/specialisti';

  constructor(private http: HttpClient) { }

  createSpecialist(s: Partial<SpecialistFullDto>): Observable<SpecialistFullDto> {  //creeaza spec
    return this.http.post<SpecialistFullDto>(this.apiUrl, s);
  }

  getSpecialisti(specializareId?: number): Observable<SpecialistCuNumeDto[]> {  //lista sp cu servicii aferente
    let url = `${this.apiUrl}/lista`;
    if (specializareId != null) {
      url += `?specializareId=${specializareId}`;
    }
    return this.http.get<SpecialistCuNumeDto[]>(url);
  }

  getAllSpecialistiFull(): Observable<SpecialistFullDto[]> {
    return this.http.get<SpecialistFullDto[]>(this.apiUrl);
  }

  getByService(serviciuId: number): Observable<SpecialistCuNumeDto[]> {  //sp cu un anumit serviciu
    const url = `${this.apiUrl}/lista/service/${serviciuId}`;
    return this.http.get<SpecialistCuNumeDto[]>(url);
  }

   getAllForAdmin(): Observable<SpecialistAdminDto[]> {
      return this.http.get<SpecialistAdminDto[]>(`${this.apiUrl}/admin`);
   }

  validateSpecialist(id: number, adminId: number): Observable<SpecialistFullDto> {
        return this.http.post<SpecialistFullDto>(
          `${this.apiUrl}/${id}/validare`,
          { idAdmin: adminId }
        );
  }
}
