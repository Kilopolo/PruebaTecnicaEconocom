import { Routes } from '@angular/router';

import { SsoCallbackComponent } from './components/auth/sso-callback/sso-callback.component';

export const routes: Routes = [
  { path: 'sso/callback', component: SsoCallbackComponent }
];