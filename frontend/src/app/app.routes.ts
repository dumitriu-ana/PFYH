import { Routes }              from '@angular/router';
import { HomeComponent }       from './home/home.component';
import { SignupComponent }     from './signup/signup.component';
import { SpecialistiComponent} from './pages/specialisti/specialisti.component';
import { SpecializariComponent}from './pages/specializari/specializari.component';
import { ServiciiComponent }   from './pages/servicii/servicii.component';

export const routes: Routes = [
  { path: 'home',         component: HomeComponent },
  { path: 'specialisti',  component: SpecialistiComponent },
  { path: 'specializari', component: SpecializariComponent },
  { path: 'servicii',     component: ServiciiComponent },
  { path: 'signup',       component: SignupComponent },
  { path: '',             redirectTo: 'home', pathMatch: 'full' },
  { path: '**',           redirectTo: 'home' }
];
