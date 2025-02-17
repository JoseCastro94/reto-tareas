import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenSubject = new BehaviorSubject<boolean>(this.getToken() != null);
  private http = inject(HttpClient);
  private url: string = environment.baseUrl + 'auth';

  login(obj: any): Observable<any> {
    return this.http.post(this.url + '/login', obj);
  }

  setAuth(response: any){
    localStorage.setItem('token',response.token);
    this.tokenSubject.next(true);
  }

  removerAuth(){
    localStorage.removeItem('token');
    this.tokenSubject.next(false);
  }

  getToken(){
    if (this.isBrowser()) {
      let token = localStorage.getItem('token');
      if(token != null){
        return token;
      }
    }

    return null;
  }

  isBrowser(): boolean {
    return typeof window !== 'undefined' &&
    typeof window.localStorage !== 'undefined';
  }

  getAuthStatus() {
    return this.tokenSubject.asObservable();
  }
}
