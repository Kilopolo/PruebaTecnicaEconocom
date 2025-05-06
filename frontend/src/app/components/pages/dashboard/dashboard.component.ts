import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  imports: [MatCardModule, MatButtonModule, MatFormFieldModule, MatInputModule, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  token: string | null = null;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('accessToken');
  }

  goHome(): void {
    this.router.navigate(['/']);
  }

  logOut(): void {
    localStorage.removeItem('accessToken');
    this.token = null;
    this.router.navigate(['/']);
}}
