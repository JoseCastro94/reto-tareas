import { AuthService } from './../../../core/services/auth.service';
import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AlertService } from '../../../core/services/alert.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth',
  imports: [
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  private authService = inject(AuthService);
  private alertaService = inject(AlertService);
  private router = inject(Router);
  loginForm!: FormGroup;

  constructor(private fb: FormBuilder) {
  this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  fnLogin() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;

      const params = {
        username: username,
        password:  password
      }

      this.authService.login(params).subscribe({
        next: (response) => {
          this.authService.setAuth(response);
          this.router.navigate(['/tarea']);
        },
        error: (e) => {
          e = e.error;
          this.alertaService.error(e.message);
          console.error(e);
        }
      });
    }
  }
}
