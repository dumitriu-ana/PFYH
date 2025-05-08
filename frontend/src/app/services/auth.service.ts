// src/app/services/auth.service.ts
import { Injectable, inject } from '@angular/core';
import { Auth, createUserWithEmailAndPassword, signInWithEmailAndPassword, signOut, onAuthStateChanged, getIdTokenResult } from '@angular/fire/auth';
import { User } from 'firebase/auth';
import { from, Observable, of, switchMap } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private auth = inject(Auth);

  // Observabil care emite user-ul curent Firebase, tipat corect
  user$: Observable<User | null> = new Observable<User | null>(observer =>
    onAuthStateChanged(this.auth, user => observer.next(user))
  );

  /** Înregistrare cu email/parolă */
  signup(email: string, password: string): Observable<any> {
    return from(createUserWithEmailAndPassword(this.auth, email, password));
  }

  /** Autentificare cu email/parolă */
  login(email: string, password: string): Observable<any> {
    return from(signInWithEmailAndPassword(this.auth, email, password));
  }

  /** Deconectare */
  logout(): Observable<void> {
    return from(signOut(this.auth));
  }

  /** Token cu custom claims (roluri) */
  getIdTokenResult$(): Observable<any> {
    return this.user$.pipe(
      switchMap((user: User | null) =>
        user ? from(getIdTokenResult(user)) : of(null)
      )
    );
  }
}
