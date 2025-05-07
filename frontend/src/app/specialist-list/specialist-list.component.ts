import { Component, Input } from '@angular/core';
import { SpecialistDto } from '../models/specialist.dto';
import { NgIf, NgFor } from '@angular/common';

@Component({
  selector: 'app-specialist-list',
  standalone: true,
  imports: [NgIf, NgFor],
  templateUrl: './specialist-list.component.html',
  styleUrls: ['./specialist-list.component.css']
})
export class SpecialistListComponent {
  @Input() specialisti: SpecialistDto[] | null = [];
}
