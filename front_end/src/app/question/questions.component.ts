import { Component, OnInit, AfterViewInit } from '@angular/core';
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
import 'rxjs/add/operator/map';
import * as _ from 'underscore';
import {PagerService} from '../service/pager.service'


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

export class QuestionsComponent implements OnInit, AfterViewInit {

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

    //= [{"id":"title1.question","data":"Информация о ценах","type":"title"},{"id":"table.1.coast-of-living-action-Table","data":{"id":"table.1.coast-of-living-action-Table","columnDefs":{"columns":[{"name":"title","value":""},{"name":"price","value":"Средняя цена"},{"name":"price_range__from","value":"Минимальная цена"},{"name":"price_range__to","value":"Максимальная цена"}]},"rows":[{"columns":[{"name":"id","value":"99"},{"name":"title","value":"Еда, недорогой ресторан"},{"name":"price","value":"995.89"},{"name":"price_range__from","value":"711.35"},{"name":"price_range__to","value":"1,138.16"}]},{"columns":[{"name":"id","value":"100"},{"name":"title","value":"Питание для 2 человек, Ресторан среднего класса, Трехместный"},{"name":"price","value":"3,556.75"},{"name":"price_range__from","value":"2,489.73"},{"name":"price_range__to","value":"4,979.45"}]},{"columns":[{"name":"id","value":"101"},{"name":"title","value":"McMeal в McDonalds (или эквивалентная комбинированная еда)"},{"name":"price","value":"569.08"},{"name":"price_range__from","value":"533.51"},{"name":"price_range__to","value":"640.22"}]},{"columns":[{"name":"id","value":"102"},{"name":"title","value":"Домашнее пиво (0.5 литра, разливное)"},{"name":"price","value":"426.81"},{"name":"price_range__from","value":"355.68"},{"name":"price_range__to","value":"497.95"}]},{"columns":[{"name":"id","value":"103"},{"name":"title","value":"Импортное пиво (0.33 литра, бутылка)"},{"name":"price","value":"426.81"},{"name":"price_range__from","value":"284.54"},{"name":"price_range__to","value":"497.95"}]},{"columns":[{"name":"id","value":"104"},{"name":"title","value":"Капучино (стандартный)"},{"name":"price","value":"238.00"},{"name":"price_range__from","value":"142.27"},{"name":"price_range__to","value":"355.68"}]},{"columns":[{"name":"id","value":"105"},{"name":"title","value":"Coke/Pepsi (0.33 литра, бутылка)"},{"name":"price","value":"204.84"},{"name":"price_range__from","value":"106.70"},{"name":"price_range__to","value":"284.54"}]},{"columns":[{"name":"id","value":"106"},{"name":"title","value":"Вода (0.33 литра, бутылка)"},{"name":"price","value":"133.38"},{"name":"price_range__from","value":"71.14"},{"name":"price_range__to","value":"213.41"}]},{"columns":[{"name":"id","value":"107"},{"name":"title","value":"Молоко (обычное), (1 литр)"},{"name":"price","value":"86.73"},{"name":"price_range__from","value":"61.89"},{"name":"price_range__to","value":"142.27"}]},{"columns":[{"name":"id","value":"108"},{"name":"title","value":"Буханка свежего белого хлеба (500г)"},{"name":"price","value":"106.29"},{"name":"price_range__from","value":"71.14"},{"name":"price_range__to","value":"142.27"}]},{"columns":[{"name":"id","value":"109"},{"name":"title","value":"Рис (белый), (1кг)"},{"name":"price","value":"145.49"},{"name":"price_range__from","value":"78.25"},{"name":"price_range__to","value":"248.97"}]},{"columns":[{"name":"id","value":"110"},{"name":"title","value":"Яйца (обычные) (12)"},{"name":"price","value":"211.04"},{"name":"price_range__from","value":"142.27"},{"name":"price_range__to","value":"384.13"}]},{"columns":[{"name":"id","value":"111"},{"name":"title","value":"Местный сыр (1кг)"},{"name":"price","value":"1,176.10"},{"name":"price_range__from","value":"711.35"},{"name":"price_range__to","value":"2,134.05"}]},{"columns":[{"name":"id","value":"112"},{"name":"title","value":"Куриные грудки (без костей, без кожи), (1 кг)"},{"name":"price","value":"770.04"},{"name":"price_range__from","value":"569.08"},{"name":"price_range__to","value":"1,102.59"}]},{"columns":[{"name":"id","value":"113"},{"name":"title","value":"Говядина (1кг) (или эквивалент)"},{"name":"price","value":"1,051.78"},{"name":"price_range__from","value":"569.08"},{"name":"price_range__to","value":"2,134.05"}]},{"columns":[{"name":"id","value":"114"},{"name":"title","value":"Яблоки (1кг)"},{"name":"price","value":"191.54"},{"name":"price_range__from","value":"142.27"},{"name":"price_range__to","value":"277.43"}]},{"columns":[{"name":"id","value":"115"},{"name":"title","value":"Бананы (1кг)"},{"name":"price","value":"138.61"},{"name":"price_range__from","value":"106.70"},{"name":"price_range__to","value":"177.84"}]},{"columns":[{"name":"id","value":"116"},{"name":"title","value":"Апельсины (1кг)"},{"name":"price","value":"163.61"},{"name":"price_range__from","value":"106.70"},{"name":"price_range__to","value":"241.86"}]},{"columns":[{"name":"id","value":"117"},{"name":"title","value":"Томаты (1кг)"},{"name":"price","value":"192.22"},{"name":"price_range__from","value":"142.27"},{"name":"price_range__to","value":"355.68"}]},{"columns":[{"name":"id","value":"118"},{"name":"title","value":"Картофель (1кг)"},{"name":"price","value":"112.04"},{"name":"price_range__from","value":"78.25"},{"name":"price_range__to","value":"142.27"}]},{"columns":[{"name":"id","value":"119"},{"name":"title","value":"Лук (1кг)"},{"name":"price","value":"136.68"},{"name":"price_range__from","value":"71.14"},{"name":"price_range__to","value":"213.41"}]},{"columns":[{"name":"id","value":"120"},{"name":"title","value":"Салат латук (1 головка)"},{"name":"price","value":"100.84"},{"name":"price_range__from","value":"71.14"},{"name":"price_range__to","value":"142.27"}]},{"columns":[{"name":"id","value":"121"},{"name":"title","value":"Вода (1.5 литра, бутылка)"},{"name":"price","value":"60.97"},{"name":"price_range__from","value":"35.57"},{"name":"price_range__to","value":"106.70"}]},{"columns":[{"name":"id","value":"122"},{"name":"title","value":"Бутылка вина (средняя цена)"},{"name":"price","value":"497.95"},{"name":"price_range__from","value":"391.24"},{"name":"price_range__to","value":"711.35"}]},{"columns":[{"name":"id","value":"123"},{"name":"title","value":"Домашнее пиво (0.5 литра, бутылка)"},{"name":"price","value":"141.38"},{"name":"price_range__from","value":"106.70"},{"name":"price_range__to","value":"177.84"}]},{"columns":[{"name":"id","value":"124"},{"name":"title","value":"Импортное пиво (0.33 литра, бутылка)"},{"name":"price","value":"167.36"},{"name":"price_range__from","value":"106.70"},{"name":"price_range__to","value":"213.41"}]},{"columns":[{"name":"id","value":"125"},{"name":"title","value":"Пачка сигарет (Marlboro)"},{"name":"price","value":"497.95"},{"name":"price_range__from","value":"497.95"},{"name":"price_range__to","value":"569.08"}]},{"columns":[{"name":"id","value":"126"},{"name":"title","value":"Билет в одну сторону (местный транспорт)"},{"name":"price","value":"135.16"},{"name":"price_range__from","value":"120.93"},{"name":"price_range__to","value":"142.27"}]},{"columns":[{"name":"id","value":"127"},{"name":"title","value":"Ежемесячный проезд (стандартная цена)"},{"name":"price","value":"5,335.13"},{"name":"price_range__from","value":"5,192.86"},{"name":"price_range__to","value":"5,349.35"}]},{"columns":[{"name":"id","value":"128"},{"name":"title","value":"Taxi Start (нормальный тариф)"},{"name":"price","value":"355.68"},{"name":"price_range__from","value":"213.41"},{"name":"price_range__to","value":"462.38"}]},{"columns":[{"name":"id","value":"129"},{"name":"title","value":"Такси 1км (нормальный тариф)"},{"name":"price","value":"92.48"},{"name":"price_range__from","value":"75.40"},{"name":"price_range__to","value":"142.27"}]},{"columns":[{"name":"id","value":"130"},{"name":"title","value":"Такси 1 час ожидания (нормальный тариф)"},{"name":"price","value":"2,703.13"},{"name":"price_range__from","value":"2,276.32"},{"name":"price_range__to","value":"4,268.10"}]},{"columns":[{"name":"id","value":"131"},{"name":"title","value":"Бензин (1 литр)"},{"name":"price","value":"101.14"},{"name":"price_range__from","value":"92.48"},{"name":"price_range__to","value":"110.26"}]},{"columns":[{"name":"id","value":"132"},{"name":"title","value":"Volkswagen Golf 1.4 90 KW Trendline (или эквивалентный новый автомобиль)"},{"name":"price","value":"1,422,700.79"},{"name":"price_range__from","value":"1,067,025.60"},{"name":"price_range__to","value":"1,671,673.43"}]},{"columns":[{"name":"id","value":"133"},{"name":"title","value":"Toyota Corolla 1.6l 97kW Comfort (или эквивалентный новый автомобиль)"},{"name":"price","value":"1,387,133.27"},{"name":"price_range__from","value":"1,138,160.64"},{"name":"price_range__to","value":"1,636,105.91"}]},{"columns":[{"name":"id","value":"134"},{"name":"title","value":"Основные (электричество,отопление, охлаждение, вода, мусор) для квартиры площадью 85 кв.м"},{"name":"price","value":"10,242.75"},{"name":"price_range__from","value":"6,043.50"},{"name":"price_range__to","value":"18,038.66"}]},{"columns":[{"name":"id","value":"135"},{"name":"title","value":"1 минута предоплаченного местного мобильного тарифа (без скидок или планов)"},{"name":"price","value":"16.85"},{"name":"price_range__from","value":"6.40"},{"name":"price_range__to","value":"35.57"}]},{"columns":[{"name":"id","value":"136"},{"name":"title","value":"Интернет (60 Мбит/с или больше, безлимит, кабель/ADSL)"},{"name":"price","value":"1,969.60"},{"name":"price_range__from","value":"1,422.70"},{"name":"price_range__to","value":"2,845.40"}]},{"columns":[{"name":"id","value":"137"},{"name":"title","value":"Фитнес клуб, ежемесячная плата за 1 взрослого"},{"name":"price","value":"3,419.75"},{"name":"price_range__from","value":"2,134.05"},{"name":"price_range__to","value":"5,690.80"}]},{"columns":[{"name":"id","value":"138"},{"name":"title","value":"Аренда теннисного корта (1 час в выходные)"},{"name":"price","value":"1,045.31"},{"name":"price_range__from","value":"497.95"},{"name":"price_range__to","value":"2,134.05"}]},{"columns":[{"name":"id","value":"139"},{"name":"title","value":"Кино, международная премьера, 1 билет"},{"name":"price","value":"778.93"},{"name":"price_range__from","value":"640.22"},{"name":"price_range__to","value":"853.62"}]},{"columns":[{"name":"id","value":"140"},{"name":"title","value":"Международная начальная школа, ежегодно для 1 ребенка"},{"name":"price","value":"994,645.69"},{"name":"price_range__from","value":"559,121.41"},{"name":"price_range__to","value":"1,707,240.95"}]},{"columns":[{"name":"id","value":"141"},{"name":"title","value":"1 пара джинсов (Levis 501 или аналогичные)"},{"name":"price","value":"6,185.72"},{"name":"price_range__from","value":"4,268.10"},{"name":"price_range__to","value":"7,753.72"}]},{"columns":[{"name":"id","value":"142"},{"name":"title","value":"1 летнее платье в сетевом магазине (Zara, H&M, ...)"},{"name":"price","value":"2,695.40"},{"name":"price_range__from","value":"1,778.38"},{"name":"price_range__to","value":"3,556.75"}]},{"columns":[{"name":"id","value":"143"},{"name":"title","value":"1 пара беговых кроссовок Nike (средняя цена)"},{"name":"price","value":"6,352.44"},{"name":"price_range__from","value":"4,979.45"},{"name":"price_range__to","value":"8,536.20"}]},{"columns":[{"name":"id","value":"144"},{"name":"title","value":"1 пара мужских классических кожаных туфлей"},{"name":"price","value":"9,134.78"},{"name":"price_range__from","value":"7,113.50"},{"name":"price_range__to","value":"14,227.01"}]},{"columns":[{"name":"id","value":"145"},{"name":"title","value":"Квартира (1 спальня) в центре города"},{"name":"price","value":"77,558.21"},{"name":"price_range__from","value":"56,908.03"},{"name":"price_range__to","value":"99,589.06"}]},{"columns":[{"name":"id","value":"146"},{"name":"title","value":"Квартира (1 спальня) на окраине"},{"name":"price","value":"59,108.84"},{"name":"price_range__from","value":"48,585.23"},{"name":"price_range__to","value":"78,248.54"}]},{"columns":[{"name":"id","value":"147"},{"name":"title","value":"Квартира (3 спальни) в центре города"},{"name":"price","value":"172,018.75"},{"name":"price_range__from","value":"128,043.07"},{"name":"price_range__to","value":"213,405.12"}]},{"columns":[{"name":"id","value":"148"},{"name":"title","value":"Квартира (3 спальни) на окраине"},{"name":"price","value":"118,476.20"},{"name":"price_range__from","value":"92,475.55"},{"name":"price_range__to","value":"142,270.08"}]},{"columns":[{"name":"id","value":"149"},{"name":"title","value":"Цена за квадратный метр, квартира в центре города"},{"name":"price","value":"702,517.80"},{"name":"price_range__from","value":"604,647.84"},{"name":"price_range__to","value":"853,620.48"}]},{"columns":[{"name":"id","value":"150"},{"name":"title","value":"Цена за квадратный метр, квартира на окраине"},{"name":"price","value":"490,831.77"},{"name":"price_range__from","value":"394,536.98"},{"name":"price_range__to","value":"569,080.32"}]},{"columns":[{"name":"id","value":"151"},{"name":"title","value":"Процентная ставка по ипотечным кредитам (%), ежегодно, за 20 лет фиксированной ставки"},{"name":"price","value":"1.8"},{"name":"price_range__from","value":"109.55"},{"name":"price_range__to","value":"177.84"}]}],"links":[]},"type":"table_picker"}];
    

    objectKeys = Object.keys;
    checks: {};

    actionUrl: string = '/action';

    public startDate: any;
    public endDate: any;
    public currentDate: Date = new Date();
    oneDay: number = 24*60*60*1000;

    login: string;

    public loading = false;

    result: boolean = false;
   

    locations: Location[] = new Array();

    numValue = '2';
    textValue = 'My best travel';

    defaultStartDate: any;
    defaultEndDate: any;

    defaultLat = 51.6754966;
    defaultLng = 39.2088823;

    departureCountryId = 'dropdownlist1.1';
    departureCityId = 'dropdownlist1.42';
    destinationCountryId = 'dropdownlist1.209';
    destinationCityId = 'dropdownlist1.1937764';
    defaultId = 'dropdownlist1.1';
	
	// array of all items to be paged
    private allItems: any[];

	// pager object
    pager: any = {};

    // paged items
    pagedItems: any[];

    markers: any[] = [
        'A',
        'B',
        'C',
        'D',
        'E',
        'F',
        'G',
        'H',
        'I',
        'J'
    ];

    constructor(private http: HttpService, private cookieService: CookieService, private router: Router,
                private authService: AuthService, private mapsApiLoader: MapsAPILoader,
                private pagerService: PagerService, private httpClient: HttpClient) {

        this.startDate = this.calculateDate(new Date(
            this.currentDate.getTime() + this.oneDay
        ));

        this.defaultStartDate = this.calculateDate(new Date(
            this.currentDate.getTime() + this.oneDay * 10
        ));

        this.endDate = this.calculateDate(new Date(
            this.currentDate.getTime() + this.oneDay * 2
		));
		
		this.defaultEndDate = this.calculateDate(new Date(
            this.currentDate.getTime() + this.oneDay * 20
        ));
	}

    calculateDate(date: Date): any{
        let year: any = date.getFullYear();
        let month: any = date.getMonth() + 1;
        let day: any = date.getDate();

        if (month < 10) {
            month = '0' + month;
        }
        if (day < 10) {
            day = '0' + day;
        }

        return year + "-" + month + "-" + day;
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

    ngAfterViewInit(): void {
        this.mapsApiLoader.load();
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
        this.locations = null;
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
        return (this.result && (typeof this.questions.travelId !== 'undefined') && this.questions.travelId == null);
    }

    getNextActionView() {
        this.loading = true;
        this.http.get(this.actionUrl).subscribe(value => {
                this.loading = false;
                this.questions = value;

                console.log('.from: ' + typeof this.questions.fromCheckpoint);
                console.log('[from]: ' + typeof this.questions['from']);

                if (typeof this.questions.fromCheckpoint !== 'undefined') {
                    this.buildRoute();
                    this.result = true;
                }
                console.log('result: ' + this.result);
                console.log(this.questions);

                //for pagination
                if (this.result == false && this.questions[1].data.rows != null) {
                    this.allItems = this.questions[1].data.rows;
                    console.log('allItems: ' + this.allItems);
                    this.setPage(1);
                }
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
        var i = 0;
        if (this.questions.twoWayFlight.flightFrom.departureCode != null){
            this.setLocationCoords(this.markers[i],
                this.questions.twoWayFlight.flightFrom.arrivalName,
                this.questions.twoWayFlight.flightFrom.arrivalName + ', ' +
                this.questions.twoWayFlight.flightFrom.arrivalCode);
            i++;
        }
        if (this.questions.twoWayFlight.flightFrom.transfers != null){
            for (var key in this.questions.twoWayFlight.flightFrom.transfers){
                let transfer = JSON.parse(key);
                this.setLocationCoords(this.markers[i],
                    transfer.placeName,
                    transfer.placeName + ', ' + transfer.placeCode);
                i++;
            }
        }
        if (this.questions.twoWayFlight.flightTo.departureCode != null){
            this.setLocationCoords(this.markers[i],
                this.questions.twoWayFlight.flightFrom.departureName,
                this.questions.twoWayFlight.flightFrom.departureName + ', ' +
                this.questions.twoWayFlight.flightFrom.departureCode);
            i++;
        }
        if (this.questions.hotel.address != null){
            this.setLocationCoords(this.markers[i],
                this.questions.hotel.name,
                this.questions.hotel.address);
            i++;
        }
    }

    setLocationCoords(marker: string, name: string, address: string){
        this.getCoordinates(address)
            .subscribe(result => {
                this.locations.push(new Location(marker, name, result.lat(), result.lng()))
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

    setPage(page: number) {
        if (page < 1 || page > this.pager.totalPages) {
            return;
        }

        // get pager object from service
        this.pager = this.pagerService.getPager(this.allItems.length, page);

        // get current page of items
        this.pagedItems = this.allItems.slice(this.pager.startIndex, this.pager.endIndex + 1);
    }

    downloadPDF(): any{
        let request = {
            travelName: this.questions.name
        }
        return this.httpClient.post(this.actionUrl + "/saveToPdf", this.questions.name, {
            headers:{'Content-Type': 'text/html; charset=utf-8'},
            responseType: 'arraybuffer'
        })
            .map((result) => {
                return new Blob([result], { type: 'application/pdf'});
            });
    }

    printPDF(){
        this.downloadPDF().subscribe((result: any) => {
            var fileUrl = URL.createObjectURL(result);
            window.open(fileUrl);
        });
    }
}

export class Location {
    marker: any;
    name: any;
    lat: any;
    lng: any;

    constructor(marker: string, name: string, lat: number, lng: number){
        this.marker = marker;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }
}