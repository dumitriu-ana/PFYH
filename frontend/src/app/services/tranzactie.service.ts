import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface CreatePaymentRequest {
  suma: number;
}

// dto pt clientSecret din back
interface CreatePaymentResponse {
  clientSecret: string;
}

@Injectable({
  providedIn: 'root'
})
export class TranzactieService {
  private apiUrl = 'http://localhost:8087/api/tranzactii';

  constructor(private http: HttpClient) { }
  //paym intent
  createPaymentIntent(sumaDePlatit: number): Observable<CreatePaymentResponse> {
    const request: CreatePaymentRequest = { suma: sumaDePlatit };
    return this.http.post<CreatePaymentResponse>(`${this.apiUrl}/create-payment-intent`, request);
  }
}
