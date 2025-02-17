import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TareaService } from '../../../core/services/tarea.service';
import { Tarea } from '../../../core/models/tarea.model';
import { AlertService } from '../../../core/services/alert.service';
import { AccordionModule } from 'primeng/accordion';
import { AvatarModule } from 'primeng/avatar';
import { BadgeModule } from 'primeng/badge';
import { ButtonModule } from 'primeng/button';
import { ItemTareaComponent } from "../../../shared/components/item-tarea/item-tarea.component";

declare var $: any;

@Component({
  selector: 'app-gest-tarea',
  imports: [
    FormsModule,
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    AccordionModule,
    AvatarModule,
    BadgeModule,
    ItemTareaComponent
],
  templateUrl: './gest-tarea.component.html',
  styleUrls: ['./gest-tarea.component.css']
})

export class GestTareaComponent implements OnInit {
  private tareaService = inject(TareaService);
  private alertaService = inject(AlertService);
  tareasPorHacer: Tarea[] = [];
  tareasEnProgreso: Tarea[] = [];
  tareasCompletadas: Tarea[] = [];
  tareaForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.tareaForm = this.fb.group({
      id: [0],  
      titulo: ['', [Validators.required]],  
      descripcion: ['', [Validators.required]],  
      estado: ['', [Validators.required]]  
    });
  }

  ngOnInit(): void {
    this.fnListar();
  }

  fnListar(): void {
    this.tareaService.listar().subscribe({
      next: (response) => {
        this.tareasPorHacer = response.filter(tarea => tarea.estado.toUpperCase().trim() === 'POR HACER');
        this.tareasEnProgreso = response.filter(tarea => tarea.estado.toUpperCase().trim() === 'EN PROGRESO');
        this.tareasCompletadas = response.filter(tarea => tarea.estado.toUpperCase().trim() === 'COMPLETADA');
      },
      error: (e) => {
        this.alertaService.error('Error al cargar tareas');
        console.error(e);
      },
    });
  }

  fnNuevo() {
    this.tareaForm.reset();
    $("#modalForm").modal('show');
  }

  fnEditar(tarea: Tarea) {
    this.tareaForm.patchValue({
      id: tarea.id,
      titulo: tarea.titulo,
      descripcion: tarea.descripcion,
      estado: tarea.estado
    });
    $("#modalForm").modal('show');
  }

  fnConfEliminar(tarea: Tarea) {
    this.alertaService.confirm('¿Estás seguro de eliminar esta tarea?').then((result) => {
      if (result) {
        this.fnEliminar(tarea.id);
      }
    });
  }

  fnEliminar(id: number) {
    this.tareaService.eliminar(id).subscribe({
      next: (response) => {
        if (response.status == 200) {
          this.alertaService.success(response.message);
          this.fnListar();  
        } else {
          this.alertaService.info('No se pudo eliminar la tarea');
        }
      },
      error: (e) => {
        e = e.error;
        this.alertaService.error('Error: '+ e.message);
        console.error(e);
      }
    });
  }

  fnGuardar() {
    if (this.tareaForm.valid) {
      const params = {
        id:  this.tareaForm.value.id == null? 0 : this.tareaForm.value.id,
        titulo:  this.tareaForm.value.titulo,
        descripcion:  this.tareaForm.value.descripcion,
        estado:  this.tareaForm.value.estado
      }

      this.tareaService.guardar(params).subscribe({
        next: (response) => {
          if(response.status == 200 || response.status == 201){
            $("#modalForm").modal('hide');
            this.alertaService.success(response.message);
            this.fnListar();  
          }else{
            this.alertaService.info(response.message);
          }
        },
        error: (e) => {
          e = e.error;
          this.alertaService.error('Error: '+ e.message);
          console.error(e);
        }
      });
    }else{
      this.alertaService.warning('Formulario invalido!');
    }
  }

  fnCerrarModal() {
    $("#modalForm").modal('hide');
  }
}
