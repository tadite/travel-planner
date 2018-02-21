import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {User} from '../user/user';
import { Observable } from 'rxjs/Observable';
import{Question} from '../question/question';
import 'rxjs/add/operator/map';
  
@Injectable()
export class HttpService{
  
    constructor(private httpClient: HttpClient){ } 

    public get(url: string): Observable<any>{
        return this.httpClient.get(url)
        /*.catch((error, caught) => {
                if (error.status === 401) {  
                    console.log('error.status');                  
                    this.cookieService.deleteAll();
                    this.router.navigate(['/login']);
                    return Observable.throw(error);
                }                
                return Observable.throw(error);

            }) as any;*/;
    }
}