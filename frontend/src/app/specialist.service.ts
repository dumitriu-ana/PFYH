// src/app/specialist.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SpecialistCuNumeDto } from './models/specialistCuNume.dto';

@Injectable({
  providedIn: 'root'
})
export class SpecialistService {
  private apiUrlSpecialisti = 'http://localhost:8083/api/specialisti/lista';

  constructor(private http: HttpClient) { }

  getSpecialisti(specializareId?: number): Observable<SpecialistCuNumeDto[]> {
    let params = new HttpParams();
    if (specializareId != null) {
      params = params.set('specializareId', specializareId.toString());
    }
    return this.http.get<SpecialistCuNumeDto[]>(this.apiUrlSpecialisti, { params });
  }
}
