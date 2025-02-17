import { ComponentFixture, TestBed } from '@angular/core/testing';
import { AuthComponent } from './auth.component';  // Importa AuthComponent correctamente
import { AuthService } from '../../../core/services/auth.service';
import { AlertService } from '../../../core/services/alert.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';

describe('AuthComponent', () => {
  let component: AuthComponent;
  let fixture: ComponentFixture<AuthComponent>;
  let authServiceMock: jasmine.SpyObj<AuthService>;
  let alertaServiceMock: jasmine.SpyObj<AlertService>;
  let routerMock: jasmine.SpyObj<Router>;

  beforeEach(async () => {
    authServiceMock = jasmine.createSpyObj('AuthService', ['login', 'setAuth']);
    alertaServiceMock = jasmine.createSpyObj('AlertService', ['error']);
    routerMock = jasmine.createSpyObj('Router', ['navigate']);

    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        RouterTestingModule,
        AuthComponent // Aquí importa AuthComponent en lugar de declararlo
      ],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: AlertService, useValue: alertaServiceMock },
        { provide: Router, useValue: routerMock }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AuthComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('Creacion', () => {
    expect(component).toBeTruthy();
  });

  it('debe llamar a authService.login y navegar a /tarea en caso de éxito', () => {
    const mockResponse = { token: 'dummyToken' };
    authServiceMock.login.and.returnValue(of(mockResponse));

    component.loginForm.setValue({ username: 'admin', password: '123456' });

    component.fnLogin();

    expect(authServiceMock.login).toHaveBeenCalledWith({
      username: 'admin',
      password: '123456'
    });
    expect(authServiceMock.setAuth).toHaveBeenCalledWith(mockResponse);
    expect(routerMock.navigate).toHaveBeenCalledWith(['/tarea']);
  });

  it('debería mostrar una alerta de error cuando falla el inicio de sesión', () => {
    const mockErrorResponse = { error: { message: 'Invalid credentials' } };
    authServiceMock.login.and.returnValue(throwError(mockErrorResponse));

    component.loginForm.setValue({ username: 'adminv1', password: '123456' });

    component.fnLogin();

    expect(authServiceMock.login).toHaveBeenCalledWith({
      username: 'adminv1',
      password: '123456'
    });
    expect(alertaServiceMock.error).toHaveBeenCalledWith('Invalid credentials');
  });

  it('no debería llamar a authService.login si el formulario no es válido', () => {
    component.loginForm.setValue({ username: '', password: '' });

    component.fnLogin();

    expect(authServiceMock.login).not.toHaveBeenCalled();
  });
});
