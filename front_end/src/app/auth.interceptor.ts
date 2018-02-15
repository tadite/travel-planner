import {Injectable} from '@angular/core';
import {HttpClient, HttpRequest, HttpInterceptor, HttpHandler, HttpEvent} from '@angular/common/http';
import {User} from './user';
import { Observable } from 'rxjs/Observable';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    constructor(private cookieService: CookieService) {
    }

    intercept(req: HttpRequest<any>,
              next: HttpHandler): Observable<HttpEvent<any>> {

        const idToken = this.cookieService.get("token");
        
        if (idToken) {
            const cloned = req.clone({
                headers: req.headers.set("Authorization", idToken)
            });

            return next.handle(cloned);
        }
        else {
            
            return next.handle(req);
        }
    }
}