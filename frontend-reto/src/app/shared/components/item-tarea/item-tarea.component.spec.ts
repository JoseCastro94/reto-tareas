import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ItemTareaComponent } from './item-tarea.component';

describe('ItemTareaComponent', () => {
  let component: ItemTareaComponent;
  let fixture: ComponentFixture<ItemTareaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ItemTareaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ItemTareaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
