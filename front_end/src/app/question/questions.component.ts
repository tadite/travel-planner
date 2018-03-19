import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate, keyframes, query, stagger } from '@angular/animations';
import { HttpService } from '../http/http.service';
import { Question } from './question';
import {HttpClientModule, HttpClient} from '@angular/common/http';
import { Console } from '@angular/core/src/console';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import {NgForm} from '@angular/forms';



@Component({
    selector: 'questions-app',
    templateUrl: './questions.component.html',
    styleUrls: ['./questions.component.css'],
    animations: [
        trigger('visibilityChanged', [
            state('shown' , style({ opacity: 1, display: 'inline' })),
            state('hidden', style({ opacity: 0, display: 'none' })),
            transition('shown => hidden', animate('15ms')),
            transition('hidden => shown', animate('15ms')),
          ])     
    ],
  //  providers: [HttpClient]
   providers: [HttpService, AuthService]
})

export class QuestionsComponent implements OnInit
{      
    
states: string[] = ['shown', 'hidden', 'hidden', 'hidden'];
    current_question: number = 0;

    nextQuestion() {
        this.states[this.current_question] = 'hidden';
        this.current_question++;
        this.states[this.current_question] = 'shown';
    }

    //questions: any[] = [{"id":"title1.question","data":"Выберите город","type":"title"},{"id":"block1.checkbox1.1959511","data":"Liepāja","type":"checkbox"},{"id":"block1.checkbox2.1921996","data":"Rēzekne","type":"checkbox"},{"id":"block1.checkbox3.1713629","data":"Daugavpils","type":"checkbox"},{"id":"block1.checkbox4.1715161","data":"Ogre","type":"checkbox"},{"id":"block1.checkbox5.1909043","data":"Ventspils","type":"checkbox"},{"id":"block1.checkbox6.1925340","data":"Rīga","type":"checkbox"},{"id":"block1.checkbox7.1801712","data":"Jelgava","type":"checkbox"},{"id":"block1.checkbox8.1953391","data":"Jūrmala","type":"checkbox"},{"id":"block1.checkbox9.1905282","data":"Valmiera","type":"checkbox"},{"id":"block1.checkbox10.1907193","data":"Cēsis","type":"checkbox"}]
    questions: any;/* = [{"id":"title1.question","data":"Выберите место проживания","type":"title"},
    {"id":"table.1.radioButton-placeOfResidence-action-NEW-TABLE-table",
     "data":{
       "id":"table.1.radioButton-placeOfResidence-action-NEW-TABLE-table",
         "columnDefs":{
           "location__country":"Страна",
           "location__name":"Город",
           "priceAvg":"Сред. стоимость",
           "hotelName":"Название отеля",
           "stars":"кол-во звёзд"
           },
         "rows":[{
           "columns":[
               {"name":"id","value":"269104"},
               {"name":"location.country","value":"Германия"},
               {"name":"location.name","value":"Берлин"},
               {"name":"priceAvg","value":"8539.66"},
               {"name":"hotelName","value":"Pullman Berlin Schweizerhof"},
               {"name":"stars","value":"5"}
           ]},
           {"columns":[
               {"name":"id","value":"269507"},
               {"name":"location.country","value":"Германия"},
               {"name":"location.name","value":"Берлин"},
               {"name":"priceAvg","value":"7015.74"},
               {"name":"hotelName","value":"Scandic Berlin Potsdamer Platz"},
               {"name":"stars","value":"4"}
           ]},
           {"columns":[
               {"name":"id","value":"46709667"},
               {"name":"location.country","value":"Германия"},
               {"name":"location.name","value":"Берлин"},
               {"name":"priceAvg","value":"6498.19"},
               {"name":"hotelName","value":"Ibis Berlin Hauptbahnhof"},
               {"name":"stars","value":"3"}
           ]},
           {"columns":[
               {"name":"id","value":"269588"},
               {"name":"location.country","value":"Германия"},
               {"name":"location.name","value":"Берлин"},
               {"name":"priceAvg","value":"6210.66"},
               {"name":"hotelName","value":"Motel One Berlin-Hauptbahnhof"},
               {"name":"stars","value":"2"}
           ]}
       ]},
   "type":"table_picker"}];*/

    objectKeys = Object.keys;
    checks: {};
    mapDates = {};
    actionUrl: string = '/action';

    public today: any;
    public currentDate: Date;
    
    login: string;

    public loading = false;

    result: boolean = false;

    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService) {
        this.currentDate = new Date();
        let year: any = this.currentDate.getFullYear();
        let month: any = this.currentDate.getMonth() + 1;
        let day: any = this.currentDate.getDate();

        if (month < 10){
            month = '0' + month;
        }
        if (day < 10){
            day = '0' + day;
        }

        this.today = year + "-" + month + "-" + day;
    }

    isEndOfTree(obj : any) {      
        return typeof(obj.countryId) !== 'undefined';
    }
    
    ngOnInit(){        
        this.login = this.getLogin();        
        this.getNextActionView();        
    }

    onSubmit(f: NgForm) {
        var map = {};
       
        var tempForm = f.value;
        for(var key in tempForm){
             map[tempForm[key]]= key;
        }        

        for(var key in this.checks){
            if (key!=null)
                map[key]= this.checks[key];
        }
              
        console.log(map);   
        var self = this;
        this.http.postObs(this.actionUrl, map).subscribe(result => {
            this.checks={};
            self.getNextActionView();
        },
        error => {console.log(error);}
        );
    }
    

    getNextActionView(){
        this.loading = true;
        this.http.get(this.actionUrl).subscribe(value => {
            this.loading = false;
            this.questions = value;
            console.log('.from: ' + typeof this.questions.from);
            console.log('[from]: '  + typeof this.questions['from']);
            if (typeof this.questions.from !== 'undefined') {                
                this.result = true;
            }
            console.log('result: ' + this.result);  
            console.log(this.questions);        
        },
        error => { }
        );
    }

    putCheckId(id: any) {
        console.log('id: ' + id); 
        console.log('checks: ' + this.checks); 
        if (this.checks[id]==='checked')
            this.checks[id]=null;
        else
            this.checks[id]='checked';
        console.log('checks: ' + this.checks); 
    }

    getCheckId(id: any){
        this.checks[id]='checked';
    }

    logout(){
        this.authService.logout();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }

}