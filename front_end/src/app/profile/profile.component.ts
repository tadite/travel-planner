import { Component } from '@angular/core';
import {NgForm} from '@angular/forms';
import { User } from '../user/user';

import { HttpService } from '../http/http.service';
import { AuthService } from '../auth/auth.service';
import { HttpClient } from '@angular/common/http';

  
@Component({
    selector: 'profile-app',     
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.css'],
    providers: [HttpService, AuthService]   
    
})
export class ProfileComponent { 
    password: string;    
    login: string;
    eMail: string;
    //user: any;
    user: User = new User(this.login, this.eMail, this.password);
    countryList: any;
    countryArray: any;
    actionUrl: string = '/api/client/profile';
    countryUrl: string = '/api/client/geo/country';
    cityUrl: string = '/api/client/geo/city';
    cityList: any;
    cityArray: any;
    result: boolean;
    constructor(private authService: AuthService, private httpService: HttpService){       
     
    }

    

    ngOnInit(){        
        this.login = this.getLogin(); 
        this.getUser();
        this.getCountryArray();   
                 
    }

    getLogin(): string {
        return this.authService.getLogin();
    }

    getUser(){
        this.httpService.get(this.actionUrl).subscribe(value => {
            this.user = value;  
            if (this.user.cityId === null || this.user.countryId === null || this.user.age === null
            || this.user.firstName === null || this.user.firstName === null) {
                this.result = false;
            } else this.result = true;
            console.log('user in subs ' + this.user);                       
        },
        error => { }
        );
        
    }

    edit(){
        this.result = false;
       // this.getUser();
    }

    onSubmit(user: User) { 
        this.httpService.postData( this.actionUrl, user)
            .subscribe(
                (data: User) => {}
               
            );
        this.result = true;
    }

    getCountryArray(){
        this.httpService.get(this.countryUrl).subscribe(value => {           
            this.countryList = value; 
            this.countryArray = Object.keys(this.countryList); 
            console.log('array: ' + this.countryArray);                    
        },
        error => { }
        );
    }

    

    getCityArray(id: string){
        console.log('id country: ' + id);   
        this.httpService.getCityList(id, this.cityUrl).subscribe(value => {           
            this.cityList = value; 
            this.cityArray = Object.keys(this.cityList); 
            console.log('city array: ' + this.cityArray);  
            console.log('city list: ' + this.cityList);                   
        },
        error => { }
        );
    }

   // public model: any;

    /*search = (text$: Observable<string>) =>
    text$
      .debounceTime(200)
      .distinctUntilChanged()
      .map(term => term.length < 2 ? []
        : this.cityList.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10));*/

}