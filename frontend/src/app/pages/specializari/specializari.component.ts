import { Component, OnInit }        from '@angular/core';
import { CommonModule }             from '@angular/common';
import { HttpClientModule }         from '@angular/common/http';

import { SpecializareService }      from '../../specializare.service';
import { SpecializareDto }          from '../../models/specializare.dto';
import { SpecializareListComponent} from '../../specializare-list/specializare-list.component';

@Component({
  selector: 'app-specializari',
  standalone: true,
  imports: [
    CommonModule,
    HttpClientModule,
    SpecializareListComponent
  ],
  templateUrl: './specializari.component.html',
  styleUrls: ['./specializari.component.css']
})
export class SpecializariComponent implements OnInit {
  specializari: SpecializareDto[] = [];
  isLoading = true;
  errorMsg?: string;

  constructor(private svc: SpecializareService) {}

  ngOnInit() {
    this.svc.getSpecializari().subscribe({
      next: data => {
        this.specializari = data;
        this.isLoading = false;
      },
      error: err => {
        this.errorMsg = 'Nu am putut încărca specializările.';
        console.error(err);
        this.isLoading = false;
      }
    });
  }
}
