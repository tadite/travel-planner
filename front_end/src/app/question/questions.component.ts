import {Component, OnInit} from '@angular/core';
import {trigger, state, style, transition, animate, keyframes, query, stagger} from '@angular/animations';
import {HttpService} from '../http/http.service';
import {Question} from './question';
import {HttpClientModule, HttpClient} from '@angular/common/http';
import {Console} from '@angular/core/src/console';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';
import {AuthService} from '../auth/auth.service';
import {NgForm} from '@angular/forms';
import {} from '@types/googlemaps';
import {GoogleMapsAPIWrapper, MapsAPILoader} from '@agm/core';
import {Observable} from 'rxjs';


@Component({
    selector: 'questions-app',
    templateUrl: './questions.component.html',
    styleUrls: ['./questions.component.css'],
    animations: [
        trigger('visibilityChanged', [
            state('shown', style({opacity: 1, display: 'inline'})),
            state('hidden', style({opacity: 0, display: 'none'})),
            transition('shown => hidden', animate('15ms')),
            transition('hidden => shown', animate('15ms')),
        ])
    ],
    //  providers: [HttpClient]
    providers: [HttpService, AuthService, GoogleMapsAPIWrapper]
})

export class QuestionsComponent implements OnInit {

    states: string[] = ['shown', 'hidden', 'hidden', 'hidden'];
    current_question: number = 0;

    nextQuestion() {
        this.states[this.current_question] = 'hidden';
        this.current_question++;
        this.states[this.current_question] = 'shown';
    }


    questions: any;
    /* = [{"id":"title1.question","data":"Введите период поездки","type":"title"},{"id":"datepicker.1.dateIntervalInput-travelPeriod-action","data":" ","type":"date_picker"},{"id":"datepicker.2.dateIntervalInput-travelPeriod-action","data":" ","type":"date_picker"}];*/
    /* = [{"id":"title1.question","data":"Страна отправления","type":"title"},{"id":"dropdownlist1.dropdownlist-departure-country-action-dropdown-list","data":{"dropdownlist1.109":"Lebanon","dropdownlist1.228":"Jamaica","dropdownlist1.107":"Lesotho","dropdownlist1.229":"Japan","dropdownlist1.108":"Liberia","dropdownlist1.105":"Kuwait","dropdownlist1.226":"South Korea","dropdownlist1.106":"Laos","dropdownlist1.227":"South Africa","dropdownlist1.103":"Côte d'Ivoire","dropdownlist1.224":"Ethiopia","dropdownlist1.104":"Cuba","dropdownlist1.101":"Congo, Democratic Republic","dropdownlist1.222":"Equatorial Guinea","dropdownlist1.102":"Costa Rica","dropdownlist1.223":"Eritrea","dropdownlist1.220":"Sri Lanka","dropdownlist1.100":"Congo","dropdownlist1.221":"Ecuador","dropdownlist1.219":"Svalbard and Jan Mayen","dropdownlist1.217":"Switzerland","dropdownlist1.218":"Sweden","dropdownlist1.215":"Czech Republic","dropdownlist1.216":"Chile","dropdownlist1.213":"Central African Republic","dropdownlist1.214":"Chad","dropdownlist1.211":"French Polynesia","dropdownlist1.212":"Croatia","dropdownlist1.210":"French Guiana","dropdownlist1.208":"Falkland Islands","dropdownlist1.209":"France","dropdownlist1.206":"Philippines","dropdownlist1.207":"Finland","dropdownlist1.204":"Faroe Islands","dropdownlist1.205":"Fiji","dropdownlist1.202":"Wallis and Futuna","dropdownlist1.203":"Uruguay","dropdownlist1.200":"Turkey","dropdownlist1.201":"Uganda","dropdownlist1.31":"Bahamas","dropdownlist1.32":"Bangladesh","dropdownlist1.30":"Afghanistan","dropdownlist1.35":"Belize","dropdownlist1.36":"Belgium","dropdownlist1.33":"Barbados","dropdownlist1.34":"Bahrain","dropdownlist1.39":"Bulgaria","dropdownlist1.37":"Benin","dropdownlist1.38":"Bermuda","dropdownlist1.20":"Austria","dropdownlist1.21":"Albania","dropdownlist1.24":"Anguilla","dropdownlist1.25":"Angola","dropdownlist1.22":"Algeria","dropdownlist1.23":"American Samoa","dropdownlist1.4":"Kazakhstan","dropdownlist1.5":"Azerbaijan","dropdownlist1.6":"Armenia","dropdownlist1.7":"Georgia","dropdownlist1.8":"Israel","dropdownlist1.9":"USA","dropdownlist1.28":"Argentina","dropdownlist1.29":"Aruba","dropdownlist1.26":"Andorra","dropdownlist1.27":"Antigua and Barbuda","dropdownlist1.1":"Russia","dropdownlist1.2":"Ukraine","dropdownlist1.3":"Belarus","dropdownlist1.10":"Canada","dropdownlist1.13":"Lithuania","dropdownlist1.14":"Estonia","dropdownlist1.11":"Kyrgyzstan","dropdownlist1.12":"Latvia","dropdownlist1.17":"Turkmenistan","dropdownlist1.18":"Uzbekistan","dropdownlist1.15":"Moldova","dropdownlist1.16":"Tajikistan","dropdownlist1.19":"Australia","dropdownlist1.198":"Tuvalu","dropdownlist1.199":"Tunisia","dropdownlist1.196":"Tonga","dropdownlist1.197":"Trinidad and Tobago","dropdownlist1.194":"Togo","dropdownlist1.195":"Tokelau","dropdownlist1.75":"Dominican Republic","dropdownlist1.76":"Egypt","dropdownlist1.73":"Denmark","dropdownlist1.74":"Dominica","dropdownlist1.79":"Zimbabwe","dropdownlist1.77":"Zambia","dropdownlist1.78":"Western Sahara","dropdownlist1.189":"Suriname","dropdownlist1.187":"Somalia","dropdownlist1.188":"Sudan","dropdownlist1.71":"Greece","dropdownlist1.185":"Slovenia","dropdownlist1.72":"Guam","dropdownlist1.186":"Solomon Islands","dropdownlist1.183":"Syria","dropdownlist1.70":"Greenland","dropdownlist1.184":"Slovakia","dropdownlist1.192":"Taiwan","dropdownlist1.193":"Tanzania","dropdownlist1.190":"Sierra Leone","dropdownlist1.191":"Thailand","dropdownlist1.64":"Guinea-Bissau","dropdownlist1.65":"Germany","dropdownlist1.62":"Guatemala","dropdownlist1.63":"Guinea","dropdownlist1.68":"Hong Kong","dropdownlist1.69":"Grenada","dropdownlist1.66":"Gibraltar","dropdownlist1.67":"Honduras","dropdownlist1.178":"Saint Kitts and Nevis","dropdownlist1.179":"Saint Lucia","dropdownlist1.176":"Senegal","dropdownlist1.177":"Saint Vincent and the Grenadines","dropdownlist1.60":"Ghana","dropdownlist1.174":"Northern Mariana Islands","dropdownlist1.61":"Guadeloupe","dropdownlist1.175":"Seychelles","dropdownlist1.172":"Saint Helena","dropdownlist1.173":"North Korea","dropdownlist1.181":"Serbia","dropdownlist1.182":"Singapore","dropdownlist1.180":"Saint Pierre and Miquelon","dropdownlist1.53":"US Virgin Islands","dropdownlist1.54":"East Timor","dropdownlist1.51":"Venezuela","dropdownlist1.52":"British Virgin Islands","dropdownlist1.57":"Haiti","dropdownlist1.58":"Guyana","dropdownlist1.169":"São Tomé and Príncipe","dropdownlist1.55":"Vietnam","dropdownlist1.56":"Gabon","dropdownlist1.167":"Samoa","dropdownlist1.168":"San Marino","dropdownlist1.165":"Romania","dropdownlist1.166":"El Salvador","dropdownlist1.163":"Réunion","dropdownlist1.50":"Hungary","dropdownlist1.164":"Rwanda","dropdownlist1.161":"Portugal","dropdownlist1.162":"Puerto Rico","dropdownlist1.170":"Saudi Arabia","dropdownlist1.171":"Swaziland","dropdownlist1.59":"Gambia","dropdownlist1.42":"Botswana","dropdownlist1.43":"Brazil","dropdownlist1.40":"Bolivia","dropdownlist1.41":"Bosnia and Herzegovina","dropdownlist1.46":"Burundi","dropdownlist1.47":"Bhutan","dropdownlist1.44":"Brunei","dropdownlist1.158":"Peru","dropdownlist1.45":"Burkina Faso","dropdownlist1.159":"Pitcairn Islands","dropdownlist1.156":"Papua New Guinea","dropdownlist1.157":"Paraguay","dropdownlist1.154":"Palestine","dropdownlist1.155":"Panama","dropdownlist1.152":"Pakistan","dropdownlist1.153":"Palau","dropdownlist1.150":"Cook Islands","dropdownlist1.151":"Turks and Caicos Islands","dropdownlist1.160":"Poland","dropdownlist1.48":"Vanuatu","dropdownlist1.49":"United Kingdom","dropdownlist1.149":"Cayman Islands","dropdownlist1.147":"Isle of Man","dropdownlist1.148":"Norfolk Island","dropdownlist1.145":"United Arab Emirates","dropdownlist1.146":"Oman","dropdownlist1.143":"New Caledonia","dropdownlist1.144":"Norway","dropdownlist1.141":"Niue","dropdownlist1.142":"New Zealand","dropdownlist1.140":"Nicaragua","dropdownlist1.138":"Curaçao","dropdownlist1.139":"Netherlands","dropdownlist1.136":"Niger","dropdownlist1.137":"Nigeria","dropdownlist1.134":"Nauru","dropdownlist1.135":"Nepal","dropdownlist1.132":"Myanmar","dropdownlist1.133":"Namibia","dropdownlist1.130":"Mongolia","dropdownlist1.131":"Montserrat","dropdownlist1.97":"China","dropdownlist1.98":"Colombia","dropdownlist1.95":"Cyprus","dropdownlist1.129":"Monaco","dropdownlist1.96":"Kiribati","dropdownlist1.127":"Micronesia","dropdownlist1.128":"Mozambique","dropdownlist1.99":"Comoros","dropdownlist1.125":"Marshall Islands","dropdownlist1.126":"Mexico","dropdownlist1.123":"Morocco","dropdownlist1.90":"Cape Verde","dropdownlist1.124":"Martinique","dropdownlist1.121":"Maldives","dropdownlist1.122":"Malta","dropdownlist1.93":"Qatar","dropdownlist1.94":"Kenya","dropdownlist1.120":"Mali","dropdownlist1.91":"Cambodia","dropdownlist1.92":"Cameroon","dropdownlist1.86":"Iceland","dropdownlist1.87":"Spain","dropdownlist1.84":"Iran","dropdownlist1.118":"Malawi","dropdownlist1.85":"Ireland","dropdownlist1.119":"Malaysia","dropdownlist1.237":"Jersey","dropdownlist1.116":"Macau","dropdownlist1.117":"Macedonia","dropdownlist1.235":"Bonaire, Sint Eustatius and Saba","dropdownlist1.88":"Italy","dropdownlist1.114":"Mauritania","dropdownlist1.236":"Guernsey","dropdownlist1.115":"Madagascar","dropdownlist1.89":"Yemen","dropdownlist1.112":"Luxembourg","dropdownlist1.233":"Vatican City","dropdownlist1.113":"Mauritius","dropdownlist1.234":"Sint Maarten","dropdownlist1.231":"Djibouti","dropdownlist1.110":"Libya","dropdownlist1.111":"Liechtenstein","dropdownlist1.232":"South Sudan","dropdownlist1.82":"Jordan","dropdownlist1.83":"Iraq","dropdownlist1.230":"Montenegro","dropdownlist1.80":"India","dropdownlist1.81":"Indonesia"},"type":"dropdown_list"}];*/
    /*= {"clientId":1,
       "fromCheckpoint":{
           "countryName":"Russia",
           "cityName":"Voronezh"
        },
        "toCheckpoint":{
            "countryName":"France",
            "cityName":"Paris"
        },
        "name":"Masha",
        "dateStart":"2018-04-24",
        "dateEnd":"2018-04-28",
        "numberOfPersons":"2",
        "budgetType":"Премиум",
        "hotel":{
            "name":"Paris Marriott Opera Ambassador",
            "address":"16 Boulevard Haussmann, Париж, Paris, 75009 Франция",
            "price":"113,647 RUB",
            "pricePeriod":"за 4 ночи",
            "priceInfo":"включая налоги и сборы.",
            "booking":"https://ru.hotels.com/travelads/trackredirect.html?trackingUrl=H4sIAAAAAAAAAD1SR4-zWAD7N3NhM7RH-6TRijAESOih5kZ59JZHCeHXb3YPK1myZMsHW66WZZr_4PiCkg12ST5_V90M0Qa_s7HHvf9UMZ_vH6nOIL7RuDousJv_t6Suztq_P_GsrYfyN1mSH6mfTueX0LiT2djgdsS5HIXHJGG8KNv6DUdp9TLiBw69PHV7bMlE8kr6HhcZi3WtKgkqdNG_wzu_21a6sCKJhoNo3TV1CEKbM0eqh8KvXtMNsr9G-66sYDx7j8sc74eeCZbpmastI1-_LrIQWPRo9VgF1QfHcGrsWQez0y0wq2gQXEDwiinEcimGmjL-EuYa-s5LsxG4ja38GKw77_Gxc-fVdxArjzl-cFaWcqNzyHPIHdjDnRP3d1BmzryUIn02hGSyfG-tjseRqmzjqSV7MwhA6dbz2Ads0N89KA3IX2XcOAvv-oG_1PfzErDa4U5JKFJ3X3rpphQ9A8vWF99h9wV3WokyBp0ML6UgH1wQsOcrjiLbIpXmXflzeM6bEH-dd8xO26hHYPEDlmWS6ZpbJXpqi3Bf3vjMh8ThhBnPyZN77TQKmTK4UPulZHTw7N8UkirRiTHbIQRvyKkb8U5EIcPveBNcBmath1R8CHAoWUzN8koAx_16uaZaVw-UZshZI3NKWOzYAescXRJmSNPKrLoS5aK-bK_bSuxP2lG6AokZ7TmtFbk3rREqR54ercd6e0Tgr9C9HuTnL7JWM2u0sQkVRDNZkuayJ59OXBk0YORhyGOhoKRCaNdxLJWYm2zCTS_d9pooK7I4tgO7T_YbzXk90wSNMPSombEGfKbPxjqxbns8H9oSv194HO2lJG1Pw10DIxABQK9IJhzVF20GXaJagxsyYn1YC9HduEUNlPN2ewdG1B2_ueFZvqYLlp3NF2kaYmsfHdn8DGDvV_3FZvI5NBanw4cwSPo33LKOqEi0zhPpz5jvdvOv99jE1-UAjT30UgSOYmYm_7igURkTAOehYJ_U08TT5yYgF3ObpMdeFX5DSYDSZNdheADTcfRiIljzLltUdW6C2Kya6tz79Zo8QVgMnkfY6xNKZZ1SneFeGsmlGBM62fiYKG5cqLktf4WdDQVXLTAyY9lCsagWIC3iWa0h9MrptU6MHkegK-eiKvngnek51zbjuhabqzUqpu48GhsaZzF9ZDXsCyVD-0N-LXBeAojmehysDSJU5_CHEgjhm_iL5mjuQwAw7L8ksOQ38ZUlQ17nyQLVXlnr_IcUGAbQBXkiuTQ_AapgTylkyBNHFgzNAA5SPPuVwiQbB22eV5j_UATJnwj6RAGPZP8A8oMvdUZZpf3-sCSRMLTAnUjApycAE-6UsCR1IoQiIyDBCDRP_QMRuw8HEgUAAA..&targetUrl=H4sIAAAAAAAAAEWMsQ7CIBQA_4btpe8hmi4vLtZOxqEmWjcEEogVKqX_L5Ndbrq7xieiPZJsjrNmEl8w3pk3pLWwRGoBFchWFP1i6xaTw1xCilXLKX0AQdt1KgtLMfZnJvXvQ9xyJS79jZW4Xzs-VJ6q_hwejGIYO95tM-PDZLOLjD-cNpwjlwAAAA.."},
            "twoWayFlight":{
                "flightTo":{
                    "aircraft":"Sukhoi Superjet SU-100-95",
                    "companyName":"Aeroflot",
                    "classType":"Бизнес-класс",
                    "departureDate":"24 апреля 2018, Вторник",
                    "departureTime":"06:35",
                    "timeInPath":"9 ч 05 мин"},
                "flightFrom":{
                    "aircraft":"Airbus A321",
                    "companyName":"Aeroflot",
                    "classType":"Бизнес-класс",
                    "departureDate":"28 апреля 2018, Суббота",
                    "departureTime":"14:05",
                    "timeInPath":"10 ч 00 мин"},
                "price":"185 986 RUR",
                "booking":"https://avia.tickets.ru/m/search/pre_booking?session_id=2227755f2338f39283c90408ff74281f&recommendation_id=b3c12ff150b089b2b2c2efac5a5b2828_157^^0&vs=SU"},
            "excursions":[{
                "name":"Мир парижской фотографии",
                "description":"Прогулка по неизвестным фотогалереям с погружением в мир парижской фотографии",
                "price":"€ 36 за 2 человек",
                "time":"2 часа",
                "booking":"https://experience.tripster.ru/experience/2360/"}],
            "carRent":{
                "name":"Fiat Panda",
                "pricePeriod":"Аренда на 5 суток",
                "price":"6 774.04 RUR",
                "seats":"Пассажиры 4",
                "doors":"Двери 2",
                "climate":"Кондиционер",
                "transmission":"Механическая",
                "classType":"Мини",
                "mileage":"Безлимитный пробег",
                "booking":"https://car.tickets.ru/search/booking_v2?session_id=5ab6801158e84b592900000c&car_id=5ab6801958e84b5929000010"
            }
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

        if (month < 10) {
            month = '0' + month;
        }
        if (day < 10) {
            day = '0' + day;
        }

        this.today = year + "-" + month + "-" + day;
        this.mapsApiLoader.load();
    }

    isLink(questionData: any, defName: String) {
        if (!(questionData.links instanceof Array))
            return false;
        return (<Array<String>>questionData.links).indexOf(defName) > -1;
    }

    isEndOfTree(obj: any) {
        return typeof(obj.countryId) !== 'undefined';
    }

    ngOnInit() {
        this.login = this.getLogin();
        this.getNextActionView();
    }

    onRollback() {
        this.loading = true;
        this.http.deleteObs(this.actionUrl).subscribe(result => {
                this.checks = {};
                this.getNextActionView();
            },
            error => {
                console.log(error);
                this.loading = false;
            }
        );
    }

    onReset() {
        this.loading = true;
        this.result = false;
        this.questions = null;
        this.http.deleteObs(this.actionUrl + '/reset').subscribe(result => {
                console.log('questions onReset' + this.questions);
                this.checks = {};
                this.getNextActionView();
                this.loading = false;
            },
            error => {
                console.log('error in onReset' + error);
                this.loading = false;
            }
        );
    }

    onSave() {
        this.http.postObs(this.actionUrl + '/save', {}).subscribe(result => {
                this.questions = result;
                console.log('questions onSave' + this.questions);
            },
            error => {
                console.log('error in onSave' + error);
                this.loading = false;
            }
        );
    }

    onSubmit(f: NgForm) {
        this.loading = true;
        var map = {};

        var tempForm = f.value;
        for (var key in tempForm) {
            map[tempForm[key]] = key;
        }

        for (var key in this.checks) {
            if (key != null)
                map[key] = this.checks[key];
        }

        console.log(map);
        this.http.postObs(this.actionUrl, map).subscribe(result => {
                this.checks = {};
                this.getNextActionView();
            },
            error => {
                console.log('error in onSubmit' + error);
            }
        );

    }

    isSaveButtonEnabled() {
        return this.result && this.questions.hasOwnProperty('clientId') && this.questions.clientId == null;
    }

    getNextActionView() {
        this.loading = true;
        this.http.get(this.actionUrl).subscribe(value => {
                this.loading = false;
                this.questions = value;
                console.log('.from: ' + typeof this.questions.fromCheckpoint);
                console.log('[from]: ' + typeof this.questions['from']);

                if (typeof this.questions.fromCheckpoint !== 'undefined') {
                    this.mapsApiLoader.load();
                    this.buildRoute();
                    this.result = true;
                }
                console.log('result: ' + this.result);
                console.log(this.questions);
            },
            error => {
                console.log('error in nextView' + error);
                this.loading = false;
            }
        );

    }

    putCheckId(id: any) {
        console.log('id: ' + id);
        console.log('checks: ' + this.checks);
        if (this.checks[id] === 'checked')
            this.checks[id] = null;
        else
            this.checks[id] = 'checked';
        console.log('checks: ' + this.checks);
    }

    getCheckId(id: any) {
        this.checks[id] = 'checked';
    }

    logout() {
        this.authService.logout();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }

    buildRoute() {
        this.mapsApiLoader.load();
        if (this.questions.fromCheckpoint.countryName != null && this.questions.fromCheckpoint.cityName != null) {
            this.setLocationCoords(this.questions.fromCheckpoint.cityName + ' ' + this.questions.fromCheckpoint.countryName);
        }
        if (this.questions.toCheckpoint.countryName != null && this.questions.toCheckpoint.cityName != null) {
            this.setLocationCoords(this.questions.toCheckpoint.cityName + ' ' + this.questions.toCheckpoint.countryName);
        }
    }

    setLocationCoords(address: string) {
        this.getCoordinates(address)
            .subscribe(result => {
                this.locations.push(new Location(result.lat(), result.lng()))
            });
    }

    getCoordinates(address: string): Observable<any> {
        let geocoder = new google.maps.Geocoder();
        return Observable.create((observer: any) => {
            geocoder.geocode({'address': address}, (results, status) => {
                if (status == google.maps.GeocoderStatus.OK) {
                    observer.next(results[0].geometry.location);
                    observer.complete();
                } else {
                    observer.error();
                }
            });
        });
    }
}

export class Location {
    lat: any;
    lng: any;

    constructor(lat: number, lng: number) {
        this.lat = lat;
        this.lng = lng;
    }
}