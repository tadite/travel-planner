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

    
    questions: any = [{"id":"title1.question","data":"Введите период поездки","type":"title"},{"id":"datepicker.1.dateIntervalInput-travelPeriod-action","data":" ","type":"date_picker"},{"id":"datepicker.2.dateIntervalInput-travelPeriod-action","data":" ","type":"date_picker"}];
    /* = [{"id":"title1.question","data":"Страна отправления","type":"title"},{"id":"dropdownlist1.dropdownlist-departure-country-action-dropdown-list","data":{"dropdownlist1.109":"Lebanon","dropdownlist1.228":"Jamaica","dropdownlist1.107":"Lesotho","dropdownlist1.229":"Japan","dropdownlist1.108":"Liberia","dropdownlist1.105":"Kuwait","dropdownlist1.226":"South Korea","dropdownlist1.106":"Laos","dropdownlist1.227":"South Africa","dropdownlist1.103":"Côte d'Ivoire","dropdownlist1.224":"Ethiopia","dropdownlist1.104":"Cuba","dropdownlist1.101":"Congo, Democratic Republic","dropdownlist1.222":"Equatorial Guinea","dropdownlist1.102":"Costa Rica","dropdownlist1.223":"Eritrea","dropdownlist1.220":"Sri Lanka","dropdownlist1.100":"Congo","dropdownlist1.221":"Ecuador","dropdownlist1.219":"Svalbard and Jan Mayen","dropdownlist1.217":"Switzerland","dropdownlist1.218":"Sweden","dropdownlist1.215":"Czech Republic","dropdownlist1.216":"Chile","dropdownlist1.213":"Central African Republic","dropdownlist1.214":"Chad","dropdownlist1.211":"French Polynesia","dropdownlist1.212":"Croatia","dropdownlist1.210":"French Guiana","dropdownlist1.208":"Falkland Islands","dropdownlist1.209":"France","dropdownlist1.206":"Philippines","dropdownlist1.207":"Finland","dropdownlist1.204":"Faroe Islands","dropdownlist1.205":"Fiji","dropdownlist1.202":"Wallis and Futuna","dropdownlist1.203":"Uruguay","dropdownlist1.200":"Turkey","dropdownlist1.201":"Uganda","dropdownlist1.31":"Bahamas","dropdownlist1.32":"Bangladesh","dropdownlist1.30":"Afghanistan","dropdownlist1.35":"Belize","dropdownlist1.36":"Belgium","dropdownlist1.33":"Barbados","dropdownlist1.34":"Bahrain","dropdownlist1.39":"Bulgaria","dropdownlist1.37":"Benin","dropdownlist1.38":"Bermuda","dropdownlist1.20":"Austria","dropdownlist1.21":"Albania","dropdownlist1.24":"Anguilla","dropdownlist1.25":"Angola","dropdownlist1.22":"Algeria","dropdownlist1.23":"American Samoa","dropdownlist1.4":"Kazakhstan","dropdownlist1.5":"Azerbaijan","dropdownlist1.6":"Armenia","dropdownlist1.7":"Georgia","dropdownlist1.8":"Israel","dropdownlist1.9":"USA","dropdownlist1.28":"Argentina","dropdownlist1.29":"Aruba","dropdownlist1.26":"Andorra","dropdownlist1.27":"Antigua and Barbuda","dropdownlist1.1":"Russia","dropdownlist1.2":"Ukraine","dropdownlist1.3":"Belarus","dropdownlist1.10":"Canada","dropdownlist1.13":"Lithuania","dropdownlist1.14":"Estonia","dropdownlist1.11":"Kyrgyzstan","dropdownlist1.12":"Latvia","dropdownlist1.17":"Turkmenistan","dropdownlist1.18":"Uzbekistan","dropdownlist1.15":"Moldova","dropdownlist1.16":"Tajikistan","dropdownlist1.19":"Australia","dropdownlist1.198":"Tuvalu","dropdownlist1.199":"Tunisia","dropdownlist1.196":"Tonga","dropdownlist1.197":"Trinidad and Tobago","dropdownlist1.194":"Togo","dropdownlist1.195":"Tokelau","dropdownlist1.75":"Dominican Republic","dropdownlist1.76":"Egypt","dropdownlist1.73":"Denmark","dropdownlist1.74":"Dominica","dropdownlist1.79":"Zimbabwe","dropdownlist1.77":"Zambia","dropdownlist1.78":"Western Sahara","dropdownlist1.189":"Suriname","dropdownlist1.187":"Somalia","dropdownlist1.188":"Sudan","dropdownlist1.71":"Greece","dropdownlist1.185":"Slovenia","dropdownlist1.72":"Guam","dropdownlist1.186":"Solomon Islands","dropdownlist1.183":"Syria","dropdownlist1.70":"Greenland","dropdownlist1.184":"Slovakia","dropdownlist1.192":"Taiwan","dropdownlist1.193":"Tanzania","dropdownlist1.190":"Sierra Leone","dropdownlist1.191":"Thailand","dropdownlist1.64":"Guinea-Bissau","dropdownlist1.65":"Germany","dropdownlist1.62":"Guatemala","dropdownlist1.63":"Guinea","dropdownlist1.68":"Hong Kong","dropdownlist1.69":"Grenada","dropdownlist1.66":"Gibraltar","dropdownlist1.67":"Honduras","dropdownlist1.178":"Saint Kitts and Nevis","dropdownlist1.179":"Saint Lucia","dropdownlist1.176":"Senegal","dropdownlist1.177":"Saint Vincent and the Grenadines","dropdownlist1.60":"Ghana","dropdownlist1.174":"Northern Mariana Islands","dropdownlist1.61":"Guadeloupe","dropdownlist1.175":"Seychelles","dropdownlist1.172":"Saint Helena","dropdownlist1.173":"North Korea","dropdownlist1.181":"Serbia","dropdownlist1.182":"Singapore","dropdownlist1.180":"Saint Pierre and Miquelon","dropdownlist1.53":"US Virgin Islands","dropdownlist1.54":"East Timor","dropdownlist1.51":"Venezuela","dropdownlist1.52":"British Virgin Islands","dropdownlist1.57":"Haiti","dropdownlist1.58":"Guyana","dropdownlist1.169":"São Tomé and Príncipe","dropdownlist1.55":"Vietnam","dropdownlist1.56":"Gabon","dropdownlist1.167":"Samoa","dropdownlist1.168":"San Marino","dropdownlist1.165":"Romania","dropdownlist1.166":"El Salvador","dropdownlist1.163":"Réunion","dropdownlist1.50":"Hungary","dropdownlist1.164":"Rwanda","dropdownlist1.161":"Portugal","dropdownlist1.162":"Puerto Rico","dropdownlist1.170":"Saudi Arabia","dropdownlist1.171":"Swaziland","dropdownlist1.59":"Gambia","dropdownlist1.42":"Botswana","dropdownlist1.43":"Brazil","dropdownlist1.40":"Bolivia","dropdownlist1.41":"Bosnia and Herzegovina","dropdownlist1.46":"Burundi","dropdownlist1.47":"Bhutan","dropdownlist1.44":"Brunei","dropdownlist1.158":"Peru","dropdownlist1.45":"Burkina Faso","dropdownlist1.159":"Pitcairn Islands","dropdownlist1.156":"Papua New Guinea","dropdownlist1.157":"Paraguay","dropdownlist1.154":"Palestine","dropdownlist1.155":"Panama","dropdownlist1.152":"Pakistan","dropdownlist1.153":"Palau","dropdownlist1.150":"Cook Islands","dropdownlist1.151":"Turks and Caicos Islands","dropdownlist1.160":"Poland","dropdownlist1.48":"Vanuatu","dropdownlist1.49":"United Kingdom","dropdownlist1.149":"Cayman Islands","dropdownlist1.147":"Isle of Man","dropdownlist1.148":"Norfolk Island","dropdownlist1.145":"United Arab Emirates","dropdownlist1.146":"Oman","dropdownlist1.143":"New Caledonia","dropdownlist1.144":"Norway","dropdownlist1.141":"Niue","dropdownlist1.142":"New Zealand","dropdownlist1.140":"Nicaragua","dropdownlist1.138":"Curaçao","dropdownlist1.139":"Netherlands","dropdownlist1.136":"Niger","dropdownlist1.137":"Nigeria","dropdownlist1.134":"Nauru","dropdownlist1.135":"Nepal","dropdownlist1.132":"Myanmar","dropdownlist1.133":"Namibia","dropdownlist1.130":"Mongolia","dropdownlist1.131":"Montserrat","dropdownlist1.97":"China","dropdownlist1.98":"Colombia","dropdownlist1.95":"Cyprus","dropdownlist1.129":"Monaco","dropdownlist1.96":"Kiribati","dropdownlist1.127":"Micronesia","dropdownlist1.128":"Mozambique","dropdownlist1.99":"Comoros","dropdownlist1.125":"Marshall Islands","dropdownlist1.126":"Mexico","dropdownlist1.123":"Morocco","dropdownlist1.90":"Cape Verde","dropdownlist1.124":"Martinique","dropdownlist1.121":"Maldives","dropdownlist1.122":"Malta","dropdownlist1.93":"Qatar","dropdownlist1.94":"Kenya","dropdownlist1.120":"Mali","dropdownlist1.91":"Cambodia","dropdownlist1.92":"Cameroon","dropdownlist1.86":"Iceland","dropdownlist1.87":"Spain","dropdownlist1.84":"Iran","dropdownlist1.118":"Malawi","dropdownlist1.85":"Ireland","dropdownlist1.119":"Malaysia","dropdownlist1.237":"Jersey","dropdownlist1.116":"Macau","dropdownlist1.117":"Macedonia","dropdownlist1.235":"Bonaire, Sint Eustatius and Saba","dropdownlist1.88":"Italy","dropdownlist1.114":"Mauritania","dropdownlist1.236":"Guernsey","dropdownlist1.115":"Madagascar","dropdownlist1.89":"Yemen","dropdownlist1.112":"Luxembourg","dropdownlist1.233":"Vatican City","dropdownlist1.113":"Mauritius","dropdownlist1.234":"Sint Maarten","dropdownlist1.231":"Djibouti","dropdownlist1.110":"Libya","dropdownlist1.111":"Liechtenstein","dropdownlist1.232":"South Sudan","dropdownlist1.82":"Jordan","dropdownlist1.83":"Iraq","dropdownlist1.230":"Montenegro","dropdownlist1.80":"India","dropdownlist1.81":"Indonesia"},"type":"dropdown_list"}];*/
     /*= 
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

    numValue = '2';
    textValue = 'Paris_24/04/18-28/04/18';

    defaultStartDate = '2018-04-24';
    defaultEndDate = '2018-04-28'; 

    departureCountryId = 'dropdownlist1.1';
    departureCityId = 'dropdownlist1.42';
    destinationCountryId = 'dropdownlist1.209';
    destinationCityId = 'dropdownlist1.1937764';
    defaultId = 'dropdownlist1.1';

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