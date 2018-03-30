import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs/Rx";
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { Injectable} from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import {HttpService} from "../http/http.service";



@Injectable()
export class AdminGuardService implements CanActivate{

    constructor(private router: Router, private cookieService: CookieService, private authService: AuthService){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) : Observable<boolean> | boolean{
        const token = this.cookieService.get("token");
        const login =this.authService.getLogin();

        if (token && login=="admin"){
            return true;
        }
        this.router.navigate(['/login']);
        return false;
    }

}