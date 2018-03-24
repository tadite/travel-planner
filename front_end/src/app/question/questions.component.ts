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

    
    questions: any; /*= 
    {"clientId":1,
     "fromCheckpoint":{
         "countryName":"Russia",
         "cityName":"Moscow"
        },
     "toCheckpoint":{
         "countryName":"Germany",
         "cityName":"Berlin"
        },
     "name":null,
     "dateStart":"2018-04-21",
     "dateEnd":"2018-04-27",
     "numberOfPersons":"2",
     "budgetType":"Премиум",
     "hotel":{
         "name":"The Ritz-Carlton, Berlin",
         "address":"Potsdamer Platz 3, Берлин, BE, 10785 Германия",
         "price":"133,786 RUB",
         "pricePeriod":"за 6 ночей",
         "priceInfo":"включая налоги и сборы.",
         "booking":"https://ru.hotels.com/travelads/trackredirect.html?trackingUrl=H4sIAAAAAAAAAD1Tx66rWBD8m7thfDlkGMkakWwDxmAy3hEOJuforx-_WYzUUklV6m6pqjuf5376G0XnMVphHaXTb15PcFzhb9I1qPMfy6eT_aWKBKIrgd66GdbT_5JYF0n1z7c9qYr2LUVzdBab_hSWH7bhHVeLzbYh5ivn2xJmNkYC6NDXMCGkVKlo1DoV4oAx94p2fFuwi1VyK-lYdfnzitRlvaIdBpDFNKWV4dz6VimFvL9t0noq73kAOOoFIoDlentwRNbahV-3e9dnfQjNI9T8S7tFpO2iSO9j8k7afBaxDufJQvMYrwfvLihuxJUbVV6JvRAFGFm-mlgUFK9rfEvpqgU-vxGBmVwIdk6oWIE4FQZHu6byfNVT1XPuiG62IYcQCvE5LBGYl3hFrXtZOOVd1zmDHP2hygQTKQlfWG1C5PJ4YwR5hu0TDJNkl82HsB46TXEvcm7MR18UvkOJ9GfCVzGw4eD2qqabGh0W_jbdLRFCXwyGgLaP1gmqpDfQiLpOmV_mmohaTpiOBmHQW8Hirns7VuOya0ZSScH95nnEvZdNfVdYvc_yuATMQCGiXIi6MsDdGLwkcpyJvUkfkPOpy6zF9fnpbpI0CaA0hFQNeCat1yXSgAQOTNbLLSdal050LPOxnGJ35eCxJ25oKqdT9djRbRWuj4GTM7AK42ZVonJd8sf6UR3bw0Ydkkhf0vT4NcaYFyWyNv4JbnI1xJdX0t0_spzWZFjK0rKg3hNZ853XH11-YZMeaNqWcpceuXAWXk7qvCv0q6NAXKmO2llOjUXMQHZvNpmTMHcwUmbz5Yk0jvQqD4sTHoithdMmUNj0ZKnEPKriRXthRSOm1c2afJPrA87idh_E0qbT0Y_aTFwXU64-suS_pMthCtsrsZqriTD9JVoF63rdIkbpZA2fH_ulXRhg5QRgNNt03I4Knw5rPuE3QdaaCle4b_XMtNQ09P4FQ_8sjJA7JsWB1Od05MSxx6CfbP96_o3T9axH2rr3Fwxoc3g4qB5A6LKSTXYiHpjgiZDbW6xVjTGDDiFD0cZF2KiStPBzq822fzuqdr_1T75xymNN2s_eWa_tYva6yGyu1tXfb9xWwD_fV0wl9c365DRfqcj3oJp1WmqeJ3yR85ZKeSQbV-od_WbsYrJzf5h6Pp6OIGzJjC_sVQ4Dn6M0H-ia7xOK2IbnnzFqqzP2M8Np9uA4FV1rrHAcixSecQ5wv-AvgiGYL5AkRf8BjsZ-wU8StWmRRjO8NdelSM8xjEkyy7gTTkfEiWSi-BQnNH6iCQxL2JiDkIY_MYySrlWmaYHpGQcYewLECScdwP39p8if2zQmuSKdv4OSjMOoU0qm8YmMU3CKcMidCDxhyDSh4zSm_wXEIjoCHgUAAA..&targetUrl=H4sIAAAAAAAAAEWMsQ7CIBRF_-ZtLwVE6vLiYu1kHGqidUMggVihUvr_Mtnl5gzn3MYnwZVsVXOcNXH4ovHOvDGthQTjB2QSRQtFv8i6xeQwl5Bi1XJKH2So7TqVhQSM_Zm4_PchbjmHS38jBfdrR_u6p8rP4UEMhrGj3XZmfJhsdpHYD0bE5ziXAAAA"},
    "twoWayFlight":{
             "flightTo":{
                 "aircraft":"Airbus A320-100/200",
                 "companyName":"Swiss",
                 "classType":"Бизнес-класс",
                 "departureDate":"21 апреля 2018, Суббота",
                 "departureTime":"05:35",
                 "timeInPath":"5 ч 35 мин"
                },
             "flightFrom":{
                 "aircraft":"",
                 "companyName":"Lufthansa",
                 "classType":"Бизнес-класс",
                 "departureDate":"27 апреля 2018, Пятница",
                 "departureTime":"17:00",
                 "timeInPath":"5 ч 55 мин"
                },
              "price":"60 574 RUR",
              "booking":"https://avia.tickets.ru/m/search/pre_booking?session_id=52ad2b9bd2a88b2ab8de656565f6c207&recommendation_id=5bed1007f2e56ca8e5ea8331fc7aa27e_157^^0&vs=LX"
            },
        "excursions":[],
        "carRent":null
    };*/
   
            

    objectKeys = Object.keys;
    checks: {};
    
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
            console.log('.from: ' + typeof this.questions.fromCheckpoint);
            console.log('[from]: '  + typeof this.questions['from']);
            if (typeof this.questions.fromCheckpoint !== 'undefined') {
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
        if (this.questions.fromCheckpoint.countryName != null && this.questions.fromCheckpoint.cityName != null){
            this.setLocationCoords(this.questions.fromCheckpoint.cityName + ' ' + this.questions.fromCheckpoint.countryName);
        }
        if (this.questions.toCheckpoint.countryName != null && this.questions.toCheckpoint.cityName != null){
            this.setLocationCoords(this.questions.toCheckpoint.cityName + ' ' + this.questions.toCheckpoint.countryName);
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