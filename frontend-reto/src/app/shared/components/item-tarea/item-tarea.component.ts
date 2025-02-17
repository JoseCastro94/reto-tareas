import { Component, Input, Output, EventEmitter} from '@angular/core';
import { Tarea } from '../../../core/models/tarea.model';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { AccordionModule } from 'primeng/accordion';
import { BadgeModule } from 'primeng/badge';
import { AvatarModule } from 'primeng/avatar';

@Component({
  selector: 'app-item-tarea',
  imports: [
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    AccordionModule,
    AvatarModule,
    BadgeModule
  ],
  templateUrl: './item-tarea.component.html',
  styleUrl: './item-tarea.component.css'
})
export class ItemTareaComponent {
  @Input() tarea!: Tarea;  // El tipo Tarea está correctamente tipado
  @Output() editar = new EventEmitter<Tarea>(); // Emite un objeto de tipo Tarea
  @Output() eliminar = new EventEmitter<Tarea>(); // Emite un objeto de tipo Tarea

  onEditar() {
    this.editar.emit(this.tarea); // Emite el objeto tarea para editar
  }

  onEliminar() {
    this.eliminar.emit(this.tarea); // Emite el objeto tarea para eliminar
  }
}
