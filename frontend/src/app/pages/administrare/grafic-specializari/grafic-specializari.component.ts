import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxChartsModule, Color, ScaleType } from '@swimlane/ngx-charts';
import { SpecialistService } from '../../../specialist.service';
import { SpecializareStatisticiDto } from '../../../models/specializare-statistici.dto';


@Component({
  selector: 'app-grafic-specializari',
  standalone: true,
  imports: [CommonModule, NgxChartsModule],
  template: `
    <div *ngIf="chartData.length > 0" class="chart-container">
      <h3 class="chart-title">Distribuția specialiștilor pe specializari</h3>
      <ngx-charts-pie-chart
        [view]="[700, 400]"
        [results]="chartData"
        [gradient]="true"
        [legend]="true"
        [labels]="true"
        [doughnut]="true"
        [scheme]="colorScheme">
      </ngx-charts-pie-chart>
    </div>
  `,
  styleUrls: ['./grafic-specializari.component.css']
})
export class GraficSpecializariComponent implements OnInit {
  chartData: SpecializareStatisticiDto[] = [];
  colorScheme: Color = {
    name: 'fyhPieScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#2e7d32', '#4caf50', '#81c784', '#a5d6a7', '#c8e6c9', '#ffa726'],
  };

  constructor(private specialistService: SpecialistService) {}

  ngOnInit() {
    this.specialistService.getStatisticiSpecializari().subscribe(data => {
      this.chartData = data;
    });
  }

}
