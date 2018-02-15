import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {User} from './user';
import { Observable } from 'rxjs/Observable';
import{Question} from './question';
import 'rxjs/add/operator/map';
  
@Injectable()
export class HttpService{
  
    constructor(private httpClient: HttpClient){ }
      
    /*postData(user: User){         
      
       const myHeaders = new HttpHeaders().set('Authorization', 'my-auth-token');
          
        //return this.http.post('http://localhost:60820/api/values', user, {headers:myHeaders}); 
     //   return this.httpClient.post('http://localhost:8090/api/auth/signup', JSON.stringify({login: user.login, password: user.password, email: user.eMail}));        
      return this.httpClient.post<any>('http://localhost:8090/api/auth/signup', user, {headers: myHeaders})
     .map(user => {
         if (user && user.token) {
             console.log('user.token: ' + user.token);
             localStorage.setItem('currentUser', JSON.stringify({login: user.login, password: user.password, email: user.eMail}));
         }
         return user;
     });        
    }*/

    public get(url: string): Observable<any>{
        return this.httpClient.get(url);
    }

   
}