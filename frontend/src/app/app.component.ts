import { Component } from '@angular/core';
import { LoginComponent } from "./components/auth/login/login.component";
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-root',
  // imports: [ RouterOutlet, LoginComponent],
  imports: [ RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
