import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {User} from '../user/user';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Injectable()
export class AuthService {
     
    constructor(private http: HttpClient, private cookieService: CookieService, private router: Router) {
    }
    
    
      
    signup(user: User) {
       // const myHeaders = new HttpHeaders().set('Authorization', 'my-auth-token');          
        
     //   return this.httpClient.post('http://localhost:8090/api/auth/signup', JSON.stringify({login: user.login, password: user.password, email: user.eMail}));        
     this.cookieService.set('login', user.login);
      return this.http.post<any>('/api/auth/signup', user)
     .map(user => {
         if (user && user.token) {             
             //localStorage.setItem('currentUser', JSON.stringify({login: user.login, password: user.password, email: user.eMail}));
             this.cookieService.set( 'token', user.token );             
         } 
         return user;
     });        
    }

    signin(user: User) { 
        this.cookieService.set('login', user.login);
       return this.http.post<any>('/api/auth/signin', user)
      .map(user => {
          if (user && user.token) { 
              this.cookieService.set( 'token', user.token );              
          } 
          return user;
      });        
     }

    logout(){
        this.cookieService.delete("token");
        this.router.navigate(['/login']);        
    }

    getLogin(){        
        return this.cookieService.get("login");
    }
}