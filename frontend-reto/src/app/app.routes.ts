import { Routes } from '@angular/router';
import { GestTareaComponent } from './features/components/gest-tarea/gest-tarea.component';
import { AuthComponent } from './features/components/auth/auth.component';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '' , component: AuthComponent},
  { path: 'login' , component: AuthComponent},
  { path: 'tarea' , component: GestTareaComponent, canActivate:[AuthGuard]},
  { path: '**' , pathMatch: 'full' , redirectTo: 'login' }
];
