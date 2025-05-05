import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { importProvidersFrom } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [
    // Angular
    FormsModule,
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

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  onSubmit() {
    console.log('Email:', this.email);
    console.log('Password:', this.password);
  }

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
