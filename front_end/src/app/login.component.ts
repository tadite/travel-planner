import { Component } from '@angular/core';
import { trigger, state, style, transition, animate, keyframes, query, stagger } from '@angular/animations';
import { HttpService } from './http.service';
import { AuthService } from './auth.service';
import { User } from './user';
import { Router } from '@angular/router';

@Component({
    selector: 'login-app',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css'],
    animations: [
        trigger('visibilityChanged', [
            state('shown' , style({ opacity: 1, display: 'inline' })),
            state('hidden', style({ opacity: 0, display: 'none' })),
            transition('shown => hidden', animate('300ms')),
            transition('hidden => shown', animate('300ms')),
          ])     
    ],
    providers: [AuthService]
})
export class LoginComponent { 
    reg_state: string = 'hidden';
    login_state: string = 'shown';

    animateMe() {
        this.reg_state = (this.reg_state === 'shown' ? 'hidden' : 'shown');
        this.login_state = (this.login_state === 'shown' ? 'hidden' : 'shown')
    }
    
    password: string;    
    login: string;
    eMail: string;

    user: User = new User(this.login, this.eMail, this.password);
   
    userExists: boolean = false;
    constructor(private authService: AuthService, private router: Router){}
    
    submit(user: User){
        this.authService.login(user)
                .subscribe(                   
                   data => {                     
                        this.router.navigate(['/questions']);                                           
                   },
                   error => {
                   if (error.status === 409) {
                         this.userExists = true;
                    }
                });
    }
}