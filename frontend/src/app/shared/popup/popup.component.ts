import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './popup.component.html',
  styleUrls: ['./popup.component.css']
})
export class PopupComponent {
  /** Mesajul care se va afișa în popup */
  @Input() message = '';
  /** Eveniment emis la închiderea popup-ului */
  @Output() close = new EventEmitter<void>();

  onClose() {
    this.close.emit();
  }
}
