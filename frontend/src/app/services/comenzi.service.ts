import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ComandaDto } from '../models/comanda.dto';

@Injectable({ providedIn: 'root' })
export class ComenziService {
  private apiUrl = 'http://localhost:8081/api/comenzi';

  constructor(private http: HttpClient) {}

  creeazaComanda(dto: ComandaDto): Observable<ComandaDto> {
    return this.http.post<ComandaDto>(this.apiUrl, dto);
  }

  getComenzi(): Observable<ComandaDto[]> {
    return this.http.get<ComandaDto[]>(this.apiUrl);
  }

  getComandaById(id: number): Observable<ComandaDto> {
    return this.http.get<ComandaDto>(`${this.apiUrl}/${id}`);
  }

  creeazaComandaCuFisier(formData: FormData): Observable<ComandaDto> {
    return this.http.post<ComandaDto>(`${this.apiUrl}/cu-fisier`, formData);
  }

  getComenziByClientId(clientId: number): Observable<ComandaDto[]> {
    return this.http.get<ComandaDto[]>(`${this.apiUrl}/client/${clientId}`);
  }

  getComenziBySpecialistId(utilizatorId: number): Observable<ComandaDto[]> {
    const url = `${this.apiUrl}/specialist/utilizator/${utilizatorId}`;
    return this.http.get<ComandaDto[]>(url);
  }

  descarcaFisier(idComanda: number, tipFisier: 'client' | 'specialist'): Observable<Blob> {
    const url = `${this.apiUrl}/${idComanda}/fisier/${tipFisier}`;
    return this.http.get(url, {
      responseType: 'blob'
    });
  }

  trimiteRaspuns(idComanda: number, formData: FormData): Observable<ComandaDto> {
      const url = `${this.apiUrl}/${idComanda}/raspuns`;
      return this.http.post<ComandaDto>(url, formData);
    }
}
