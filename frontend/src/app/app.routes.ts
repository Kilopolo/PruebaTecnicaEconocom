import { Routes } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { SsoCallbackComponent } from './components/auth/sso-callback/sso-callback.component';

export const routes: Routes = [
  { path: 'sso/callback', component: SsoCallbackComponent },
  { path: '', component: LoginComponent },
  { path: '**', redirectTo: '' }
];