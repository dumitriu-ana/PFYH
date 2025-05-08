import { Routes }          from '@angular/router';
import { HomeComponent }   from './home/home.component';
import { SignupComponent } from './signup/signup.component';

export const routes: Routes = [
  { path: 'home',   component: HomeComponent },
  { path: 'signup', component: SignupComponent },
  { path: '',       redirectTo: 'home', pathMatch: 'full' },
  { path: '**',     redirectTo: 'home' }
];
