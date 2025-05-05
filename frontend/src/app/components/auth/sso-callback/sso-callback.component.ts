import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-sso-callback',
  templateUrl: './sso-callback.component.html',
})
export class SsoCallbackComponent implements OnInit {

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const code = params['code'];
      if (code) {
        fetch('http://localhost:8080/api/auth/sso/callback?code=' + code)
          .then(response => response.json())
          .then(data => {
            alert('Autenticado con éxito vía SSO');
            // Aquí puedes guardar token, redirigir, etc.
          })
          .catch(error => {
            console.error('Error en callback SSO', error);
            alert('Error en autenticación SSO');
          });
      } else {
        alert('No se encontró el código de autenticación');
      }
    });
  }
}
