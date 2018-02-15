import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {User} from './user';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AuthService {
     
    constructor(private http: HttpClient, private cookieService: CookieService) {
    }
      
    login(user: User) {
       // const myHeaders = new HttpHeaders().set('Authorization', 'my-auth-token');          
        
     //   return this.httpClient.post('http://localhost:8090/api/auth/signup', JSON.stringify({login: user.login, password: user.password, email: user.eMail}));        
      return this.http.post<any>('http://localhost:8090/api/auth/signup', user)
     .map(user => {
         if (user && user.token) {             
             //localStorage.setItem('currentUser', JSON.stringify({login: user.login, password: user.password, email: user.eMail}));
             this.cookieService.set( 'token', user.token );
         } 
         
         console.log('user.status ' + user.status + ' user.message ' + user.message);

        /* data => { 
            console.log('data.status: ' + data['status']);
            if (data.status == 200) {
                this.router.navigate(['/questions']);
                }                     
           },
           error => { console.log('error.status: ' + error.status);
           if (error.status == 409){
                 this.userExists = true;
            }                  
        }*/
         
         return user;
     });        
    }
}