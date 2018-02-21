import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/Rx";
import { QuestionsComponent } from '../question/questions.component';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AuthGuardService implements CanActivate{  

  constructor(private router: Router, private cookieService: CookieService){} 
    
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : Observable<boolean> | boolean{
      const token = this.cookieService.get("token"); 
        if (token) {  
            return true;     
        }
    this.router.navigate(['/login']); 
    return false; 
    } 
}