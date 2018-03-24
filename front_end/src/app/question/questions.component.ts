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
import { } from '@types/googlemaps';
import { GoogleMapsAPIWrapper, MapsAPILoader } from '@agm/core';
import { Observable } from 'rxjs';



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
   providers: [HttpService, AuthService, GoogleMapsAPIWrapper]
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

    //questions: any = [{"id":"title1.question","data":"Выберите город","type":"title"},{"id":"block1.checkbox1.1959511","data":"Liepāja","type":"checkbox"},{"id":"block1.checkbox2.1921996","data":"Rēzekne","type":"checkbox"},{"id":"block1.checkbox3.1713629","data":"Daugavpils","type":"checkbox"}];
    questions: any;/* = [{"id":"title1.question","data":"Выберите место проживания","type":"title"},
        {"id":"table.1.radioButton-placeOfResidence-action-NEW-TABLE-table",
        "data":{
            "id":"table.1.radioButton-placeOfResidence-action-NEW-TABLE-table",
            "columnDefs":{
                "columns":[
                    {"name":"location__country","value":"Страна"},
                    {"name":"location__name","value":"Город"},
                    {"name":"priceAvg","value":"Сред. стоимость"},
                    {"name":"hotelName","value":"Название отеля"},
                    {"name":"stars","value":"Количество звезд"}
                ]},
            "rows":[
                {"columns":[
                    {"name":"id","value":"269104"},
                    {"name":"location__country","value":"Германия"},
                    {"name":"location__name","value":"Kurfuerstendamm 27, Берлин, BE, 10719 Германия"},
                    {"name":"priceAvg","value":"8890.6"},
                    {"name":"hotelName","value":"Pullman Berlin Schweizerhof"},
                    {"name":"stars","value":"ааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааа"}
                ]},
                {"columns":[
                    {"name":"id","value":"269507"},
                    {"name":"location__country","value":"Германия"},
                    {"name":"location__name","value":"Берлин"},
                    {"name":"priceAvg","value":"6610.22"},
                    {"name":"hotelName","value":"Scandic Berlin Potsdamer Platz"},
                    {"name":"stars","value":"4"}
                ]},
                {"columns":[
                    {"name":"id","value":"29839620"},
                    {"name":"location__country","value":"Германия"},
                    {"name":"location__name","value":"Берлин"},
                    {"name":"priceAvg","value":"6927.74"},
                    {"name":"hotelName","value":"Motel One Berlin-Potsdamer Platz"},
                    {"name":"stars","value":"3"}
                ]},
                {"columns":[
                    {"name":"id","value":"269588"},
                    {"name":"location__country","value":"Германия"},
                    {"name":"location__name","value":"Берлин"},
                    {"name":"priceAvg","value":"6234.97"},
                    {"name":"hotelName","value":"Motel One Berlin-Hauptbahnhof"},
                    {"name":"stars","value":"2"}
                ]}               
            ]},
            "type":"table_picker"
        }]*/

    objectKeys = Object.keys;
    checks: {};
    mapDates = {};
    actionUrl: string = '/action';

    public today: any;
    public currentDate: Date;
    
    login: string;

    public loading = false;

    result: boolean = false;

    locations: Location[] = new Array();

    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService, private mapsApiLoader: MapsAPILoader) {
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
        this.mapsApiLoader.load();
    }

    isLink(questionData : any, defName : String) {     
        if (!(questionData.links instanceof Array)) 
            return false;
        return (<Array<String>>questionData.links).indexOf(defName)>-1;            
    }

    isEndOfTree(obj : any) {      
        return typeof(obj.countryId) !== 'undefined';
    }
    
    ngOnInit(){        
        this.login = this.getLogin();        
        this.getNextActionView();
    }

    onRollback() { 
        this.loading = true; 
        var self = this;
        this.http.deleteObs(this.actionUrl).subscribe(result => {
            this.checks={};
            self.getNextActionView();
        },
        error => {console.log(error);self.loading=false;}
        );
    }

    onReset() {  
        this.result=false;
        var self = this;
        this.http.deleteObs(this.actionUrl+'/reset').subscribe(result => {
            this.checks={};
            self.getNextActionView();
            self.loading=false;
        },
        error => {console.log(error);self.loading=false;}
        );
    }

    onSubmit(f: NgForm) {
        this.loading = true;
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

        var self = this;
        this.http.get(this.actionUrl).subscribe(value => {            
            self.loading = false;    
            this.questions = value;
            console.log('.from: ' + typeof this.questions.from);
            console.log('[from]: '  + typeof this.questions['from']);
            if (typeof this.questions.from !== 'undefined') {
                this.mapsApiLoader.load();
                this.buildRoute();
                this.result = true;
            }
            console.log('result: ' + this.result);  
            console.log(this.questions);    
        },
        error => { self.loading = false; }
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

    buildRoute(){
        this.mapsApiLoader.load();
        if (this.questions.from.countryName != null && this.questions.from.cityName != null){
            this.setLocationCoords(this.questions.from.cityName + ' ' + this.questions.from.countryName);
        }
        if (this.questions.to.countryName != null && this.questions.to.cityName != null){
            this.setLocationCoords(this.questions.to.cityName + ' ' + this.questions.to.countryName);
        }
    }

    setLocationCoords(address: string){
        this.getCoordinates(address)
            .subscribe(result => {
                this.locations.push(new Location(result.lat(), result.lng()))
            });
    }

    getCoordinates(address: string): Observable<any> {
        let geocoder = new google.maps.Geocoder();
        return Observable.create((observer: any) => {
            geocoder.geocode({'address': address}, (results, status) => {
                if (status == google.maps.GeocoderStatus.OK){
                    observer.next(results[0].geometry.location);
                    observer.complete();
                } else {
                    observer.error();
                }
            });
        });
    }
}

export class Location{
    lat: any;
    lng: any;

    constructor(lat: number, lng: number){
        this.lat = lat;
        this.lng = lng;
    }
}