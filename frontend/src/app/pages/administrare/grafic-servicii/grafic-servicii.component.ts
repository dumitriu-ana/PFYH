import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgxChartsModule, Color, ScaleType } from '@swimlane/ngx-charts';
import { ComenziService } from '../../../services/comenzi.service';
import { ServiciuNumarDto } from '../../../models/serviciu-numar.dto';

@Component({
  selector: 'app-grafic-servicii',
  standalone: true,
  imports: [CommonModule, NgxChartsModule],
  template: `
    <div *ngIf="chartData.length > 0; else noData" class="chart-container">
      <h3 class="chart-title">Numărul de comenzi pentru fiecare serviciu</h3>

      <ngx-charts-bar-horizontal
        [view]="[700, 400]"
        [results]="chartData"
        [xAxis]="true"
        [yAxis]="true"
        [showXAxisLabel]="true"
        [showYAxisLabel]="true"
        xAxisLabel="Număr Comenzi"
        yAxisLabel="Serviciu"
        [barPadding]="8"
        [showDataLabel]="true"
        [scheme]="colorScheme"

        [trimYAxisTicks]="false"
        [yAxisTickFormatting]="formatLabel"
        >
      </ngx-charts-bar-horizontal>

    </div>

    <ng-template #noData>
        <div class="no-data-container">
          <p>Nu există date.</p>
        </div>
    </ng-template>
  `,
  styleUrls: ['./grafic-servicii.component.css']
})
export class GraficServiciiComponent implements OnInit {
  chartData: any[] = [];

  colorScheme: Color = {
    name: 'fyhScheme',
    selectable: true,
    group: ScaleType.Ordinal,
    domain: ['#2e7d32', '#ffa726', '#4caf50', '#ffb74d', '#66bb6a'],
  };

  constructor(private comenziService: ComenziService) {}

  ngOnInit() {
    this.comenziService.getStatisticiServiciiNumar().subscribe(data => {
      this.chartData = data.map((item: ServiciuNumarDto) => ({
        name: item.numeServiciu,
        value: item.numarComenzi
      }));
    });
  }


  formatLabel(label: string): string {
    return label;
  }
}
