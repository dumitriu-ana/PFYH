// src/main.ts
import { bootstrapApplication }              from '@angular/platform-browser';
import { importProvidersFrom }               from '@angular/core';
import { HttpClientModule }                  from '@angular/common/http';
import { provideRouter }                     from '@angular/router';

import { provideFirebaseApp, initializeApp } from '@angular/fire/app';
import { provideAuth, getAuth }              from '@angular/fire/auth';
import { provideStorage, getStorage }        from '@angular/fire/storage';

import { AppComponent } from './app/app.component';
import { appConfig }    from './app/app.config';
import { routes }       from './app/app.routes';
import { environment }  from './environments/environment';

bootstrapApplication(AppComponent, {
  ...appConfig,
  providers: [
    ...appConfig.providers,
    importProvidersFrom(HttpClientModule),
    provideRouter(routes),
    // ── Firebase ──────────────────────────────────────────────────────────
    provideFirebaseApp(() => initializeApp(environment.firebase)),
    provideAuth(()               => getAuth()),
    provideStorage(()            => getStorage()),
    // ──────────────────────────────────────────────────────────────────────
  ]
})
.catch(err => console.error(err));
