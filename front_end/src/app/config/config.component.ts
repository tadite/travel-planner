import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { HttpService } from '../http/http.service';
import { AuthService} from "../auth/auth.service";
import { CookieService } from 'ngx-cookie-service';


@Component({
    selector: 'config-app',
    templateUrl: './config.component.html',
    styleUrls: ['./config.component.css'],
    providers: [HttpService, AuthService]

})

export class ConfigComponent {
    login: string;
    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService) {

    }

    ngOnInit(){
        this.login = this.getLogin();
    }

    logout(){
        this.authService.logout();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }


}
