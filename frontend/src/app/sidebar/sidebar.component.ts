import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, NgClass],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  isMobileView = false;
  isSidebarOpen = true; // Implicit deschis pe desktop

  constructor() {
    this.updateMobileView();
    window.addEventListener('resize', this.updateMobileView);
  }

  updateMobileView = () => {
    this.isMobileView = window.innerWidth < 768; // Considerăm 768px ca breakpoint pentru mobil
    if (!this.isMobileView) {
      this.isSidebarOpen = true; // Asigură-te că e deschis pe desktop la resize
    }
  };

  toggleSidebar = () => {
    if (this.isMobileView) {
      this.isSidebarOpen = !this.isSidebarOpen;
    }
  };

  get sidebarClasses() {
    return {
      'sidebar': true,
      'open': !this.isMobileView || this.isSidebarOpen,
      'closed': this.isMobileView && !this.isSidebarOpen
    };
  }
}
