import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestTareaComponent } from './gest-tarea.component';

describe('GestTareaComponent', () => {
  let component: GestTareaComponent;
  let fixture: ComponentFixture<GestTareaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestTareaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestTareaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
