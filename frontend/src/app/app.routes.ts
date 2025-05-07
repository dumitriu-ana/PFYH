import { Routes } from '@angular/router';
//import { SpecialistListComponent } from './specialist-list/specialist-list.component';
//import { SpecialistDetailsComponent } from './specialist-details/specialist-details.component';
import { HomeComponent } from './home/home.component'; // Asigură-te că importi HomeComponent
//import { AddSpecializareComponent } from './add-specializare/add-specializare.component';

export const routes: Routes = [
  { path: '', component: HomeComponent }, // Ruta pentru pagina de acasă (calea implicită)
 // { path: 'specialisti', component: SpecialistListComponent },
  //{ path: 'specialisti/:id', component: SpecialistDetailsComponent },
 // { path: 'specialisti/:id/adauga-specializare', component: AddSpecializareComponent },
  // ... alte rute pe care le vei adăuga
];
