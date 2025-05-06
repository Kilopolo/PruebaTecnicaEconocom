import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sso-callback',
  templateUrl: './sso-callback.component.html',
  standalone: true,
  imports: [HttpClientModule], 
})
export class SsoCallbackComponent implements OnInit {
  constructor(
    private route: ActivatedRoute, 
    private http: HttpClient,
    private router: Router) {}

  ngOnInit() {
    const code = this.route.snapshot.queryParamMap.get('code');
    if (code) {
      this.http.post('http://localhost:8080/api/auth/sso/callback', { code })
        .subscribe({
          next: (res) => {
            console.log('Autenticado por SSO:', res);
            if (res) {
              // localStorage.setItem('accessToken', res);  
              // Guarda el token en el almacenamiento local
              console.log('Token guardado:', res);
              // Redirigir a la página de inicio o dashboard
              this.router.navigate(['/dashboard']);
            } else {
              console.error('No se recibió un token del backend');
            }
          },
          error: (err) => {
            console.error('Error al validar SSO:', err);
          }
        });
    } else {
      console.error('No se recibió código SSO');
    }
  }
}