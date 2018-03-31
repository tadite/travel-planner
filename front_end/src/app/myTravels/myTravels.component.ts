import { Component } from '@angular/core';
import {NgForm} from '@angular/forms';
import { User } from '../user/user';

import { HttpService } from '../http/http.service';
import { AuthService } from '../auth/auth.service';
import { HttpClient } from '@angular/common/http';

import 'rxjs/add/operator/map';
import * as _ from 'underscore';
import { TravelPagerService } from '../service/travel.pager.service'
  
@Component({
    selector: 'myTravels-app',     
    templateUrl: './myTravels.component.html',
    styleUrls: ['./myTravels.component.css'],
    providers: [HttpService, AuthService, TravelPagerService]   
    
})
export class MyTravelsComponent { 
   
    login: string;

    travels: any;

     // array of all items to be paged
     private allItems: any[];

     // pager object
     pager: any = {};
 
     // paged items
     pagedItems: any[];

    public loading = false;
    travelUrl: string = '/api/client/travel';
    deleteTravelUrl: string = '/api/travel';
    constructor(private authService: AuthService, private http: HttpService, private pagerService: TravelPagerService){      
     
    }

    

    ngOnInit(){        
        this.login = this.getLogin(); 
        this.getTravels();
      
    }

    getLogin(): string {
        return this.authService.getLogin();
    }

    getTravels(){
        this.loading = true;
        this.http.get(this.travelUrl).subscribe(value => {
            this.loading = false;
            this.travels = value; 

            console.log(this.travels);
             //for pagination
            
                this.allItems = this.travels;
                console.log('allItems: ' + this.allItems);
                this.setPage(1);
            
        },
        error => {
            console.log('error in getTravels ' + error);
            this.loading = false;
        }
    );
    }

    onDelete(id: number){
        this.loading = true;
        console.log('id ' + id);
        this.http.deleteObs(this.deleteTravelUrl + id).subscribe(result => {
            this.loading = false;
        },
        error => {
            console.log('error in onReset' + error);
            this.loading = false;
        }
    );
    }

    setPage(page: number) {
        if (page < 1 || page > this.pager.totalPages) {
            return;
        }

        // get pager object from service
        this.pager = this.pagerService.getPager(this.allItems.length, page);

        // get current page of items
        this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex + 1);
    }
 
}