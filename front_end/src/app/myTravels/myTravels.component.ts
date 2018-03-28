import { Component } from '@angular/core';
import {NgForm} from '@angular/forms';
import { User } from '../user/user';

import { HttpService } from '../http/http.service';
import { AuthService } from '../auth/auth.service';
import { HttpClient } from '@angular/common/http';

  
@Component({
    selector: 'myTravels-app',     
    templateUrl: './myTravels.component.html',
    styleUrls: ['./myTravels.component.css'],
    providers: [AuthService]   
    
})
export class MyTravelsComponent { 
   
    login: string;
    
    constructor(private authService: AuthService){      
     
    }

    

    ngOnInit(){        
        this.login = this.getLogin(); 
      
    }

    getLogin(): string {
        return this.authService.getLogin();
    }

  
 
}