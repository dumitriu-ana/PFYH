import { Component }            from '@angular/core';
import { Router,                RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule,          NgClass } from '@angular/common';

import { AuthService }          from '../services/auth.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    RouterLinkActive,
    NgClass
  ],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  isMobileView = false;
  isSidebarOpen = true;

  constructor(
    public auth: AuthService,
    private router: Router
  ) {
    this.updateMobileView();
    window.addEventListener('resize', this.updateMobileView);
  }

  updateMobileView = () => {
    this.isMobileView = window.innerWidth < 768;
    if (!this.isMobileView) {
      this.isSidebarOpen = true;
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
      'open':   !this.isMobileView || this.isSidebarOpen,
      'closed': this.isMobileView && !this.isSidebarOpen
    };
  }

  // --- Adăugările pentru login/logout ---
  isLoggedIn(): boolean {
    return this.auth.hasToken();
  }

  logout(): void {
    this.auth.clearToken();
    this.router.navigate(['/home']);
  }
}
