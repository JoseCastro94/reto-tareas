import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GestTareaComponent } from './gest-tarea.component';
import { TareaService } from '../../../core/services/tarea.service';
import { AlertService } from '../../../core/services/alert.service';
import { ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';

describe('GestTareaComponent', () => {
  let component: GestTareaComponent;
  let fixture: ComponentFixture<GestTareaComponent>;
  let tareaService: TareaService;
  let alertaService: AlertService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule,
        ReactiveFormsModule,
        GestTareaComponent
      ],
      providers: [TareaService, AlertService]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestTareaComponent);
    component = fixture.componentInstance;
    tareaService = TestBed.inject(TareaService);
    alertaService = TestBed.inject(AlertService);
    fixture.detectChanges();
  });

  it('Creacion', () => {
    expect(component).toBeTruthy();
  });

  it('Cargar las tareas correctamente', () => {
    const tareaMock = [
      { id: 1, titulo: 'Tarea 1', descripcion: 'Descripción 1', estado: 'POR HACER' },
      { id: 2, titulo: 'Tarea 2', descripcion: 'Descripción 2', estado: 'EN PROGRESO' },
      { id: 3, titulo: 'Tarea 3', descripcion: 'Descripción 3', estado: 'COMPLETADA' }
    ];

    spyOn(tareaService, 'listar').and.returnValue(of(tareaMock));

    component.fnListar();

    expect(component.tareasPorHacer.length).toBe(1);
    expect(component.tareasEnProgreso.length).toBe(1);
    expect(component.tareasCompletadas.length).toBe(1);
  });
});
