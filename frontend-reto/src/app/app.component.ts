import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LoadingComponent } from "./shared/components/loading/loading.component";
import { NavbarComponent } from "./shared/components/navbar/navbar.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LoadingComponent, NavbarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend-reto';
}
