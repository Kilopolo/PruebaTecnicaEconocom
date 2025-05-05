import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from "./components/shared/header/header.component";
import { DashbardComponent } from "./components/pages/dashbard/dashbard.component";
import { FooterComponent } from "./components/shared/footer/footer.component";
import { LoginComponent } from "./components/auth/login/login.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,  DashbardComponent, FooterComponent, LoginComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
}
