import { Component } from '@angular/core';
import { trigger, state, style, transition, animate, keyframes, query, stagger } from '@angular/animations';
import { HttpService } from './http.service';
import { User } from './user';


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
    providers: [HttpService]
})
export class LoginComponent { 
    reg_state: string = 'hidden';
    login_state: string = 'shown';

    animateMe() {
        this.reg_state = (this.reg_state === 'shown' ? 'hidden' : 'shown');
        this.login_state = (this.login_state === 'shown' ? 'hidden' : 'shown')
    }

    userName: string;
    password: string;
    firstName: string;
    lastName: string;
    eMail: string;

    user: User = new User(this.firstName, this.lastName, this.eMail, this.password);
    receivedUser: User; 
    done: boolean = false;

    constructor(private httpService: HttpService){}
    
    submit(user: User){
        this.httpService.postData(user)
                .subscribe(
                    (data: User) => {this.receivedUser=data; this.done=true;},
                    error => console.log(error)
                );
    }
}