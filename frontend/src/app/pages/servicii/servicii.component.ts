import { Component, OnInit }           from '@angular/core';
import { CommonModule }                from '@angular/common';
import { HttpClientModule }            from '@angular/common/http';

import { ServiciiService }             from '../../servicii.service';
import { ServiciuDto }                 from '../../models/serviciu.dto';
import { ServiciiListComponent }       from '../../servicii-list/servicii-list.component';

@Component({
  selector: 'app-servicii',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    ServiciiListComponent
  ],
  templateUrl: './servicii.component.html',
  styleUrls: ['./servicii.component.css']
})
export class ServiciiComponent implements OnInit {
  servicii: ServiciuDto[] = [];
  isLoading = true;
  errorMsg?: string;

  constructor(private svc: ServiciiService) {}

  ngOnInit(): void {
    this.svc.getServicii().subscribe({
      next: data => {
        this.servicii = data;
        this.isLoading = false;
      },
      error: err => {
        console.error('Eroare încărcare servicii:', err);
        this.errorMsg = 'Nu am putut încărca serviciile.';
        this.isLoading = false;
      }
    });
  }
}
