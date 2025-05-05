import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-sso-callback',
  templateUrl: './sso-callback.component.html',
  standalone: true,
  imports: [HttpClientModule], 
})
export class SsoCallbackComponent implements OnInit {
  constructor(private route: ActivatedRoute, private http: HttpClient) {}

  ngOnInit() {
    const code = this.route.snapshot.queryParamMap.get('code');
    if (code) {
      this.http.post('http://localhost:8080/api/auth/sso/callback', { code })
        .subscribe({
          next: (res) => {
            console.log('Autenticado por SSO:', res);
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