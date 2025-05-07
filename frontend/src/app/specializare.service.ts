 import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SpecializareDto } from './models/specializare.dto';

@Injectable({
  providedIn: 'root'
})
export class SpecializareService {
  private apiUrl = 'http://localhost:8086/api/specializari';

  constructor(private http: HttpClient) { }

  getSpecializari(): Observable<SpecializareDto[]> {
    return this.http.get<SpecializareDto[]>(this.apiUrl);
  }
}
