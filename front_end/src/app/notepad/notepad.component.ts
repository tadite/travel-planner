import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
  
@Component({
    selector: 'notepad-app',
    templateUrl: './notepad.component.html',
    styleUrls: ['./notepad.component.css'],
    providers: [AuthService]
})
export class NotepadComponent { 
    constructor(private authService: AuthService){       
     
    }
    login: string;
    ngOnInit(){        
        this.login = this.getLogin();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }
}