import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import { SidebarComponent } from './sidebar/sidebar.component';
import { NgClass } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, SidebarComponent, NgClass],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  isMobileView = false;
  isSidebarOpen = true;

  constructor() {
    this.updateMobileView();
    window.addEventListener('resize', this.updateMobileView);
  }

  updateMobileView = () => {
    this.isMobileView = window.innerWidth < 768;
    if (!this.isMobileView) {
      this.isSidebarOpen = true;
    }
  };

  toggleSidebarMobile = () => {
    if (this.isMobileView) {
      this.isSidebarOpen = !this.isSidebarOpen;
    }
  };

  get mainContentClasses() {
    return {
      'main-content': true,
      'sidebar-open-mobile': this.isMobileView && this.isSidebarOpen
    };
  }
}
