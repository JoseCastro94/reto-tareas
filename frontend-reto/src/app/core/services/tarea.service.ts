import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tarea } from '../models/tarea.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TareaService {
  private http = inject(HttpClient);
  private url: string = environment.baseUrl + 'tareas';

  listar() {
    return this.http.get<Tarea[]>( this.url);
  }

  buscarPorId(id: number) {
    return this.http.get( this.url + '/'+ id);
  }

  guardar(obj: any): Observable<any> {
    if(obj.id == 0){
      return this.http.post( this.url , obj);
    }else{
      return this.http.put( this.url + '/'+ obj.id, obj);
    }
  }

  eliminar(id: number): Observable<any> {
    return this.http.delete( this.url + '/' + id);
  }
}
