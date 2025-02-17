import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink,
    CommonModule
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  private authService = inject(AuthService);
  private router = inject(Router);
  private authStatusSub: Subscription | undefined;

  estaLogeado: boolean = false;

  ngOnInit(): void {
    this.authStatusSub = this.authService.getAuthStatus().subscribe(status => {
      this.estaLogeado = status;
    });
  }

  fnLogout(){
    this.authService.removerAuth();
    this.router.navigate(['login']);
  }
}
