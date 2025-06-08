import { Routes } from '@angular/router';
import { HomeComponent }       from './home/home.component';
import { SignupComponent }     from './signup/signup.component';
import { SpecialistiComponent} from './pages/specialisti/specialisti.component';
import { SpecializariComponent}from './pages/specializari/specializari.component';
import { ServiciiComponent }   from './pages/servicii/servicii.component';
import { AboutFyhComponent }   from './pages/about-fyh/about-fyh.component';
import { LoginComponent }      from './pages/login/login.component';
import { ContComponent }        from './pages/cont/cont.component';
import { AdministrareComponent }        from './pages/administrare/administrare.component';
import { ComenziComponent }        from './pages/comenzi/comenzi.component';

export const routes: Routes = [
  { path: 'home',         component: HomeComponent },
  { path: 'specialisti',  component: SpecialistiComponent },
  { path: 'specializari', component: SpecializariComponent },
  { path: 'servicii',     component: ServiciiComponent },
  { path: 'despre-fyh',   component: AboutFyhComponent },
  { path: 'login',        component: LoginComponent },
  { path: 'signup',       component: SignupComponent },
  { path: 'cont',         component: ContComponent },
  { path: 'logout',       redirectTo: 'home', pathMatch: 'full' },

    { path: 'administrare', component: AdministrareComponent },
    { path: 'comenzi', component: ComenziComponent },

  { path: '',             redirectTo: 'home', pathMatch: 'full' },
  { path: '**',           redirectTo: 'home' }
];
