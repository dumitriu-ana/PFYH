import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent }         from './app/app.component';
import { importProvidersFrom }  from '@angular/core';
import { HttpClientModule, HTTP_INTERCEPTORS }     from '@angular/common/http';
import { provideRouter }        from '@angular/router';
import { routes }               from './app/app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';


bootstrapApplication(AppComponent, {
  providers: [
    importProvidersFrom(HttpClientModule),
    provideRouter(routes),
    provideAnimations(),
  ]
})
.catch(err => console.error(err));
