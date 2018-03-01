import {Injectable} from '@angular/core';
import {HttpClient, HttpRequest, HttpInterceptor, HttpHandler, HttpEvent} from '@angular/common/http';
import {User} from '../user/user';
import { Observable } from 'rxjs/Observable';
import { CookieService } from 'ngx-cookie-service';
import 'rxjs/add/operator/catch';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

   constructor(private cookieService: CookieService) { }
    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {

        const token = this.cookieService.get("token");        
        
        if (token) {
            const cloned = req.clone({
                headers: req.headers.set("Authorization", token)
            });

            return next.handle(cloned);            
        }
        else {            
            return next.handle(req);
        }
    }
}