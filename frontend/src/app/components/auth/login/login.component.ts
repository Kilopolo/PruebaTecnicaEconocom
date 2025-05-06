import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [
    // Angular
    FormsModule,
    CommonModule,
    // Material
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule
  ]
})
export class LoginComponent {
  email: string = '';
  password: string = '';
  showPassword = false;
  errorMessage: string = '';
  emailError: string = '';
  passwordError: string = '';
  generalError: string = '';

  constructor(private router: Router) {}  

  togglePassword() {
    this.showPassword = !this.showPassword;
  }


  validateEmail(value: string) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    this.emailError = !value
      ? 'El email es obligatorio'
      : !emailRegex.test(value)
      ? 'Formato de email inválido'
      : '';
  }

  validatePassword(value: string) {
    this.passwordError = !value ? 'La contraseña es obligatoria' : '';
  }

  isFormValid(): boolean {
    this.validateEmail(this.email);
    this.validatePassword(this.password);
    return !this.emailError && !this.passwordError;
  }

  onSubmit() {

    if (!this.isFormValid()) {
      return; // Detener el envío si la validación falla
    }

    fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ email: this.email, password: this.password })
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Error en la autenticación: ' + response.statusText);
        }
        return response.json();
      })
      .then(data => {
        console.log('Respuesta del backend:', data);
        if (data.accessToken) {
          localStorage.setItem('accessToken', data.accessToken);  
          // Guarda el token en el almacenamiento local
          console.log('Token guardado:', data.accessToken);
          // Redirigir a la página de inicio o dashboard
          this.router.navigate(['/dashboard']);

        } else {
          console.error('No se recibió un token del backend');
        }
      })
    .catch(error => {this.errorMessage = error.message || 'Error en el inicio de sesión.';});
  }

  /**
   * Cómo funciona el flujo SSO
      Llama al backend para iniciar el flujo SSO.
      El backend responde con una redirección (por ejemplo, a un proveedor simulado).
      Una vez autenticado, ese proveedor te redirige de vuelta al frontend (por ejemplo, a /sso-callback?code=algo).
      En ese componente (SsoCallbackComponent), tú haces la petición al backend (/sso/callback) y recibes el token.
      Pero ahí se detiene el flujo. Falta redirigir al dashboard desde SsoCallbackComponent cuando el login es exitoso.
   */
  loginSSO() {
    fetch('http://localhost:8080/api/auth/sso', {
      method: 'GET',
      redirect: 'follow'
    })
    .then(response => {
      if (response.redirected) {
        window.location.href = response.url;  // redirige al proveedor SSO simulado

      } else {
        console.error('No se recibió una redirección del backend');
      }
    })
    .catch(error => console.error('Error al iniciar SSO:', error));
  }
  

}
