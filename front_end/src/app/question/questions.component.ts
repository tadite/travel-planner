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

    //questions: any = [{"id":"title1.question","data":"Выберите город","type":"title"},{"id":"block1.checkbox1.1959511","data":"Liepāja","type":"checkbox"},{"id":"block1.checkbox2.1921996","data":"Rēzekne","type":"checkbox"},{"id":"block1.checkbox3.1713629","data":"Daugavpils","type":"checkbox"}];
    questions: any;/* = [{"id":"title1.question","data":"Выберите страну отправления","type":"title"},{"id":"dropdownlist1.dropdownlist-departure-country-action-dropdown-list","data":{"dropdownlist1.109":"Lebanon","dropdownlist1.228":"Jamaica","dropdownlist1.107":"Lesotho","dropdownlist1.229":"Japan","dropdownlist1.108":"Liberia","dropdownlist1.105":"Kuwait","dropdownlist1.226":"South Korea","dropdownlist1.106":"Laos","dropdownlist1.227":"South Africa","dropdownlist1.103":"Côte d'Ivoire","dropdownlist1.224":"Ethiopia","dropdownlist1.104":"Cuba","dropdownlist1.101":"Congo, Democratic Republic","dropdownlist1.222":"Equatorial Guinea","dropdownlist1.102":"Costa Rica","dropdownlist1.223":"Eritrea","dropdownlist1.220":"Sri Lanka","dropdownlist1.100":"Congo","dropdownlist1.221":"Ecuador","dropdownlist1.219":"Svalbard and Jan Mayen","dropdownlist1.217":"Switzerland","dropdownlist1.218":"Sweden","dropdownlist1.215":"Czech Republic","dropdownlist1.216":"Chile","dropdownlist1.213":"Central African Republic","dropdownlist1.214":"Chad","dropdownlist1.211":"French Polynesia","dropdownlist1.212":"Croatia","dropdownlist1.210":"French Guiana","dropdownlist1.208":"Falkland Islands","dropdownlist1.209":"France","dropdownlist1.206":"Philippines","dropdownlist1.207":"Finland","dropdownlist1.204":"Faroe Islands","dropdownlist1.205":"Fiji","dropdownlist1.202":"Wallis and Futuna","dropdownlist1.203":"Uruguay","dropdownlist1.200":"Turkey","dropdownlist1.201":"Uganda","dropdownlist1.31":"Bahamas","dropdownlist1.32":"Bangladesh","dropdownlist1.30":"Afghanistan","dropdownlist1.35":"Belize","dropdownlist1.36":"Belgium","dropdownlist1.33":"Barbados","dropdownlist1.34":"Bahrain","dropdownlist1.39":"Bulgaria","dropdownlist1.37":"Benin","dropdownlist1.38":"Bermuda","dropdownlist1.20":"Austria","dropdownlist1.21":"Albania","dropdownlist1.24":"Anguilla","dropdownlist1.25":"Angola","dropdownlist1.22":"Algeria","dropdownlist1.23":"American Samoa","dropdownlist1.4":"Kazakhstan","dropdownlist1.5":"Azerbaijan","dropdownlist1.6":"Armenia","dropdownlist1.7":"Georgia","dropdownlist1.8":"Israel","dropdownlist1.9":"USA","dropdownlist1.28":"Argentina","dropdownlist1.29":"Aruba","dropdownlist1.26":"Andorra","dropdownlist1.27":"Antigua and Barbuda","dropdownlist1.1":"Russia","dropdownlist1.2":"Ukraine","dropdownlist1.3":"Belarus","dropdownlist1.10":"Canada","dropdownlist1.13":"Lithuania","dropdownlist1.14":"Estonia","dropdownlist1.11":"Kyrgyzstan","dropdownlist1.12":"Latvia","dropdownlist1.17":"Turkmenistan","dropdownlist1.18":"Uzbekistan","dropdownlist1.15":"Moldova","dropdownlist1.16":"Tajikistan","dropdownlist1.19":"Australia","dropdownlist1.198":"Tuvalu","dropdownlist1.199":"Tunisia","dropdownlist1.196":"Tonga","dropdownlist1.197":"Trinidad and Tobago","dropdownlist1.194":"Togo","dropdownlist1.195":"Tokelau","dropdownlist1.75":"Dominican Republic","dropdownlist1.76":"Egypt","dropdownlist1.73":"Denmark","dropdownlist1.74":"Dominica","dropdownlist1.79":"Zimbabwe","dropdownlist1.77":"Zambia","dropdownlist1.78":"Western Sahara","dropdownlist1.189":"Suriname","dropdownlist1.187":"Somalia","dropdownlist1.188":"Sudan","dropdownlist1.71":"Greece","dropdownlist1.185":"Slovenia","dropdownlist1.72":"Guam","dropdownlist1.186":"Solomon Islands","dropdownlist1.183":"Syria","dropdownlist1.70":"Greenland","dropdownlist1.184":"Slovakia","dropdownlist1.192":"Taiwan","dropdownlist1.193":"Tanzania","dropdownlist1.190":"Sierra Leone","dropdownlist1.191":"Thailand","dropdownlist1.64":"Guinea-Bissau","dropdownlist1.65":"Germany","dropdownlist1.62":"Guatemala","dropdownlist1.63":"Guinea","dropdownlist1.68":"Hong Kong","dropdownlist1.69":"Grenada","dropdownlist1.66":"Gibraltar","dropdownlist1.67":"Honduras","dropdownlist1.178":"Saint Kitts and Nevis","dropdownlist1.179":"Saint Lucia","dropdownlist1.176":"Senegal","dropdownlist1.177":"Saint Vincent and the Grenadines","dropdownlist1.60":"Ghana","dropdownlist1.174":"Northern Mariana Islands","dropdownlist1.61":"Guadeloupe","dropdownlist1.175":"Seychelles","dropdownlist1.172":"Saint Helena","dropdownlist1.173":"North Korea","dropdownlist1.181":"Serbia","dropdownlist1.182":"Singapore","dropdownlist1.180":"Saint Pierre and Miquelon","dropdownlist1.53":"US Virgin Islands","dropdownlist1.54":"East Timor","dropdownlist1.51":"Venezuela","dropdownlist1.52":"British Virgin Islands","dropdownlist1.57":"Haiti","dropdownlist1.58":"Guyana","dropdownlist1.169":"São Tomé and Príncipe","dropdownlist1.55":"Vietnam","dropdownlist1.56":"Gabon","dropdownlist1.167":"Samoa","dropdownlist1.168":"San Marino","dropdownlist1.165":"Romania","dropdownlist1.166":"El Salvador","dropdownlist1.163":"Réunion","dropdownlist1.50":"Hungary","dropdownlist1.164":"Rwanda","dropdownlist1.161":"Portugal","dropdownlist1.162":"Puerto Rico","dropdownlist1.170":"Saudi Arabia","dropdownlist1.171":"Swaziland","dropdownlist1.59":"Gambia","dropdownlist1.42":"Botswana","dropdownlist1.43":"Brazil","dropdownlist1.40":"Bolivia","dropdownlist1.41":"Bosnia and Herzegovina","dropdownlist1.46":"Burundi","dropdownlist1.47":"Bhutan","dropdownlist1.44":"Brunei","dropdownlist1.158":"Peru","dropdownlist1.45":"Burkina Faso","dropdownlist1.159":"Pitcairn Islands","dropdownlist1.156":"Papua New Guinea","dropdownlist1.157":"Paraguay","dropdownlist1.154":"Palestine","dropdownlist1.155":"Panama","dropdownlist1.152":"Pakistan","dropdownlist1.153":"Palau","dropdownlist1.150":"Cook Islands","dropdownlist1.151":"Turks and Caicos Islands","dropdownlist1.160":"Poland","dropdownlist1.48":"Vanuatu","dropdownlist1.49":"United Kingdom","dropdownlist1.149":"Cayman Islands","dropdownlist1.147":"Isle of Man","dropdownlist1.148":"Norfolk Island","dropdownlist1.145":"United Arab Emirates","dropdownlist1.146":"Oman","dropdownlist1.143":"New Caledonia","dropdownlist1.144":"Norway","dropdownlist1.141":"Niue","dropdownlist1.142":"New Zealand","dropdownlist1.140":"Nicaragua","dropdownlist1.138":"Curaçao","dropdownlist1.139":"Netherlands","dropdownlist1.136":"Niger","dropdownlist1.137":"Nigeria","dropdownlist1.134":"Nauru","dropdownlist1.135":"Nepal","dropdownlist1.132":"Myanmar","dropdownlist1.133":"Namibia","dropdownlist1.130":"Mongolia","dropdownlist1.131":"Montserrat","dropdownlist1.97":"China","dropdownlist1.98":"Colombia","dropdownlist1.95":"Cyprus","dropdownlist1.129":"Monaco","dropdownlist1.96":"Kiribati","dropdownlist1.127":"Micronesia","dropdownlist1.128":"Mozambique","dropdownlist1.99":"Comoros","dropdownlist1.125":"Marshall Islands","dropdownlist1.126":"Mexico","dropdownlist1.123":"Morocco","dropdownlist1.90":"Cape Verde","dropdownlist1.124":"Martinique","dropdownlist1.121":"Maldives","dropdownlist1.122":"Malta","dropdownlist1.93":"Qatar","dropdownlist1.94":"Kenya","dropdownlist1.120":"Mali","dropdownlist1.91":"Cambodia","dropdownlist1.92":"Cameroon","dropdownlist1.86":"Iceland","dropdownlist1.87":"Spain","dropdownlist1.84":"Iran","dropdownlist1.118":"Malawi","dropdownlist1.85":"Ireland","dropdownlist1.119":"Malaysia","dropdownlist1.237":"Jersey","dropdownlist1.116":"Macau","dropdownlist1.117":"Macedonia","dropdownlist1.235":"Bonaire, Sint Eustatius and Saba","dropdownlist1.88":"Italy","dropdownlist1.114":"Mauritania","dropdownlist1.236":"Guernsey","dropdownlist1.115":"Madagascar","dropdownlist1.89":"Yemen","dropdownlist1.112":"Luxembourg","dropdownlist1.233":"Vatican City","dropdownlist1.113":"Mauritius","dropdownlist1.234":"Sint Maarten","dropdownlist1.231":"Djibouti","dropdownlist1.110":"Libya","dropdownlist1.111":"Liechtenstein","dropdownlist1.232":"South Sudan","dropdownlist1.82":"Jordan","dropdownlist1.83":"Iraq","dropdownlist1.230":"Montenegro","dropdownlist1.80":"India","dropdownlist1.81":"Indonesia"},"type":"dropdown_list"}];*/
     /*= [{"id":"title1.question","data":"Выберите место проживания","type":"title"},
        {"id":"table.1.radioButton-hotels-find-hotels-action-NEW-TABLE",
         "data":{
             "id":"table.1.radioButton-hotels-find-hotels-action-NEW-TABLE",
             "columnDefs":{"columns":[
                 {"name":"name","value":"Название отеля"},
                 {"name":"address","value":"Адрес"},
                 {"name":"price","value":"Стоимость"},
                 {"name":"price_period","value":"Период"},
                 {"name":"price_info","value":"Информация"},
                 {"name":"booking","value":"Ссылка"}
                ]},
             "rows":[
                 {"columns":[
                     {"name":"id","value":"100"},
                     {"name":"name","value":"Hotel Bristol Berlin"},
                     {"name":"address","value":"Kurfuerstendamm 27, Берлин, BE, 10719 Германия"},
                     {"name":"price","value":"9,060 RUB"},
                     {"name":"price_period","value":"за 1 ночь"},
                     {"name":"price_info","value":"включая налоги и сборы."},
                     {"name":"booking","value":"https://ru.hotels.com/ho109357/?pa=1&q-check-out=2018-03-25&tab=description&q-room-0-adults=2&YGF=1&q-check-in=2018-03-24&MGT=1&WOE=7&WOD=6&ZSX=0&SYE=3&q-room-0-children=0"}
                    ]},
                {"columns":[
                    {"name":"id","value":"101"},
                    {"name":"name","value":"Hilton Berlin"},
                    {"name":"address","value":"Mohrenstr. 30, Берлин, BE, 10117 Германия"},
                    {"name":"price","value":"17,560 RUB"},{"name":"price_period","value":"за 1 ночь"},
                    {"name":"price_info","value":"включая налоги и сборы."},
                    {"name":"booking","value":"https://ru.hotels.com/travelads/trackredirect.html?trackingUrl=H4sIAAAAAAAAAD2S2a6ySACE3-bcOB62ZvsTM0FkE1mURfEOoUH2pkG2px9nLiappJKq1HdV73FEwx-CGHE8wTpOh993PUA8wd-kawj_v1RKB-8bFQkkJobQuxHWw_-VXBdJ9fd3nlRFm5_iMT7IDdqP4jkyxxRXjFShh9z7omFOn5LC6nnGnGxkudHqN23or5vtVLuH-5wbzmgsjZ0ZTm3aHbtNaNUtbhtOhhDfYhlzW7u4auv6y33Y8LG2Xycp2ga_0q18AprbdUtujbfoCF9wEKBm3xmlvHwuONLl6JZWXK9ao3o2qFPDHkHeaJRkCmS4Pbe5QsrM9ww1lqyCjUwkrMBF3dRyLl9J0bx7TNdcmz8N0o7KZINgCnSVaJG3wAE4zIoeU1u5RedFW0O65-PuISkZv0Yyh2Ofsfn5bT-bOh6DMsnKMfTzGxZmKSd5zXb48BohlqQZr4yPhbesLoK-CP1Yco9-1rgBgUPBihTO1PtrmejMs0fhEp-5Pq-BLVlpPbjH6-mjqZLtgoEsgLQGfXa50vxMHCeR6_Ti6rAGb7xf7w6Q2ycXtFBZ7hVOpZgpYdsY6AJ8osrblrzdYXYdhk8S8pB63RJV-azzFyjcpCvlMaoWDNbmKSENkHV3IQDebesWS4rrED196yldxewBlpMlNvN4dCvXXu2JcEaXiajaV5CczIqWnpFekYhDQ2Ga3j0yEdkuYnlCGH9rHcu9Qs_itNjOFGHyLcs74JmSS607tbcyTSRlJcES-WTyhzjQXNdus5NAl9KE4PFSis0xxyxX1nVTh3aX6K0W-3JxbkzNPOp3fJ7sS-YkxHsqds4a8XJJcvkHeccwCzzbpvC8xYZY9w636op6Dk70a6W_b9WiIGE_BTE3Zd_NVJnpz4dEZxMldBLpXhOH8ppgY4JyCt0wvOMq8Hy7s-temnEY2WhV_HhYXLRj6y8ASSK-X66fqyS_luc7Y58ZBnziJ72Ebd7unuvoPhtz0NTEBuUFduLjhIy1mGS5fLMLref0NJ4dix8v07SOJnrhjXqGjdLS7XtRAMXfT1YOfUKWa0N0HMRanA2cQN6S3VjXF-RYbWRRuSTlM9UX6m0gLZvoGvUZASu--yko3q4eoIV-R8atth4ONHbkWERnG9jmdDlbPdJtcja3-LxrRk1Aaq0GTn2T35pi68zd9rgupQWkC4cfHLfVgfoZ4TCGEA9F1zoTxLhI4YEWSfGX_IvhGf5rALDcvyZy1C_5k8RtWqTxCPVG-xTpIeVfnJBx9J6MeXEPYsDs4xcD9xyTAhqkkARk_POCcdK1xvfDMD3QJCXsSWZPMz7J_aG_Yn_0ASdv43RgaUYQAQ33L5r60mgq3oskze7pjEsYIeYY7vX6B25iQ-AeBQAA&targetUrl=H4sIAAAAAAAAAEXMvQ6CMBQF4LfpdkN_FFhuXEQm44CJ4FbbJm3EXizl_e0ky1nO-U7lSchayaY6LRol-4LxzryBtoySixa4AnlkWb_QutWksORAscwS0Qc4aLvNeS1w6i8o_jzEXR_Ytb-X7nHrsCl5xpo9hxE5G6YO1f5lfJhtchH5D29PoAyWAAAA"}
                    ]},
                {"columns":[
                    {"name":"id","value":"102"},
                    {"name":"name","value":"Seminaris CampusHotel Berlin"},
                    {"name":"address","value":"Takustr. 39, Берлин, BE, 14195 Германия"},
                    {"name":"price","value":"5,863 RUB"},{"name":"price_period","value":"за 1 ночь"},
                    {"name":"price_info","value":"включая налоги и сборы."},
                    {"name":"booking","value":"https://ru.hotels.com/ho309052/?pa=3&q-check-out=2018-03-25&tab=description&q-room-0-adults=2&YGF=1&q-check-in=2018-03-24&MGT=1&WOE=7&WOD=6&ZSX=0&SYE=3&q-room-0-children=0"}
                    ]},
                {"columns":[
                    {"name":"id","value":"103"},
                    {"name":"name","value":"Ivbergs Hotel Premium am Kurfürstendamm"},
                    {"name":"address","value":"Kleiststr. 9-12, Берлин, BE, 10787 Германия"},
                    {"name":"price","value":"6,206 RUB"},
                    {"name":"price_period","value":"за 1 ночь"},
                    {"name":"price_info","value":"включая налоги и сборы."},
                    {"name":"booking","value":"https://ru.hotels.com/ho340734/?pa=4&q-check-out=2018-03-25&tab=description&q-room-0-adults=2&YGF=1&q-check-in=2018-03-24&MGT=1&WOE=7&WOD=6&ZSX=0&SYE=3&q-room-0-children=0"}
                    ]},
                {"columns":[
                    {"name":"id","value":"104"},
                    {"name":"name","value":"Centro Park Hotel Berlin-Neukölln"},
                    {"name":"address","value":"Buschkrugallee 60-62, Берлин, BE, 12359 Германия"},
                    {"name":"price","value":"7,278 RUB"},
                    {"name":"price_period","value":"за 1 ночь"},
                    {"name":"price_info","value":"включая налоги и сборы."},
                    {"name":"booking","value":"https://ru.hotels.com/ho196133/?pa=5&q-check-out=2018-03-25&tab=description&q-room-0-adults=2&YGF=1&q-check-in=2018-03-24&MGT=1&WOE=7&WOD=6&ZSX=0&SYE=3&q-room-0-children=0"}
                    ]},
                {"columns":[
                    {"name":"id","value":"105"},
                    {"name":"name","value":"Best Western Hotel Berlin-Mitte"},
                    {"name":"address","value":"Albrechtstrasse 25, Берлин, BE, 10117 Германия"},
                    {"name":"price","value":"6,393 RUB"},
                    {"name":"price_period","value":"за 1 ночь"},
                    {"name":"price_info","value":"включая налоги и сборы."},
                    {"name":"booking","value":"https://ru.hotels.com/travelads/trackredirect.html?trackingUrl=H4sIAAAAAAAAAD2Sx46rWABE_6Y3jBu4l-QntUbYRBNMMhh25Jyzv356ZjFSSUeqUtWqimUZ5j8oukzhljZhMn8XzZxOW_od9y3q_OeyyWz_WmWcohtEpX5Jm_n_6N6Ucf33bz2uyy7nwiX8ubfDZbk-fGVJphqy9fC-j85VVrZNuosG6FZkWeNyCE9Sw24occzBaFLOe3J28u4p6GuPd7XqTYr9UOviGeyBOG9LCGxXESrpY8WCsJafJ45WsQS0UctvRZJAtx0s8_PwRDnkqMHwWtnJOKYCOWfr2bKCQfOkgERH7DnICE9kjSheVQ7rSSLiJdzoee3jUUizUnxUwYg2B-sZTwQsA7x7i8Aajf6NTt3KCc1wziFTb9SsKUjVcTk3Fhv-CpnEpnaNWIZV1xurNanH3LooOZ3tC056yx6YPbRG0YOj4tsqo-nae-SahjLaLaQz3qMRY9HbOiUw0qbGXmH4gNQjWqhhCiy7umXIs9Axt561XqHklFivE4MM4aGJ0O0Q3f_kAZN9yOduKtTc6Ue1Rq90Z_r7ZimEwxn75DRdnjsqD23yZZenW7-9w3KOx6ZzYcRplcM5KJWYrs8B6ZpvZqSV22c5wO2kw0Q9sozd1fPG38Vaiw_3egTAbvf0YGkUqYcm9RJzCkAonnnRxPXL7TIbPttVjLTcaz1BsFT0DscdyEfPL58buQor6hkJ6nrFFDigPQeLtwyhSdEj9kfOVtHPw65pKUGcuyT6Pt1v_IMWDB4wQeNlB6LFg1G05NDfJArzku2BPt2g3uT3LPR5FEhEx3SBaZHC4vatNtyYPE8wJ-0qjqh1PtgPxTV0R_Uet0OeosAX13BK_LOFH3ZsmL4rdJmCqfyqbGZeLCQOzPFwGVPAMp5GXVGdU__9btsYqzzy45zPwuJStLcjKWcZoE6RuGyD2Uu--vRwdfDt3ugZOfPs0sC3-4ZxNZvhvaLgqq7vrTfoN2Ert9aVlXwrhRPJs7We1XG23Pt4Yq_sw-Jzj8jZHZZjC_BRNbOh9SdxVoFkZZGvw5tTZ1En2tk03p359y8MGw9R9dypTXuz2IJYqGAQ2Yp04hPDJzpHof00j7K4JXqeXsuuLmD-MlgyCcjVEHf3rXDr-mo01kZ8Bt-XpB6rPCN9BZpxeBoLemqWNWifghu5uRV9rsBNZdWs3fcTXXphTTfh88_XFHb1D_ha0nlx02ku--65pdNUJukPuGLXb-wvSEP6FwRBUv_iSuHf2FccdkmZhEsqteJaJj8JHVFMRoELFtLXCxES8BJGML1QMCEAkaQYgYVfURrGfSfP85omPwDDmQsGLwA6GPUH_Ir8kuYpLmTuhwSQuRIgvUQA_10DeHi5YoC8gIyKIRNSkIqifwA9BwDDHgUAAA..&targetUrl=H4sIAAAAAAAAAEWNsQ7CIBRF_4btpa8g6vLiYu1kOtRE64ZAAmmFltL_l8kudzn33Fu5yKVEIavLrOjIFtDO6hHiloljfQYUwCXL6kPGrjr5OfsYSi3F-AUEZbYpr8TZ0N6o_us-7PaB3dtHYc-uoVPJa7l59y9C1g8NiX1LOz-ZZAPhD10HIuSWAAAA"}
                    ]},
                {"columns":[
                    {"name":"id","value":"106"},
                    {"name":"name","value":"Steigenberger Hotel Am Kanzleramt"},
                    {"name":"address","value":"Ella-Trebe-Straße 5, Берлин, BE, 10557 Германия"},
                    {"name":"price","value":"19,309 RUB"},
                    {"name":"price_period","value":"за 1 ночь"},
                    {"name":"price_info","value":"включая налоги и сборы."},
                    {"name":"booking","value":"https://ru.hotels.com/travelads/trackredirect.html?trackingUrl=H4sIAAAAAAAAAD2S2a6yWBCF3-bc0B7m6U9MRyZBZhEV7jaDuGGDyLjx6dvui04q-ZJVqZWVrHpOUz_-IclpAEuJQDH-PtFYDkv5m79a8vKfeijG6CvBvCQXljRfU4nG_1cqgnnz9_c8b2BXaWACe7Xtd842G_bUehydMDxm4GOLwGmkjbhHoqlHZlkqxS3yphR-VBnT7ZKsXeUJlqjcBCG42RAczrSUv8_LeO5jsDJaH4e1ORKio0OrztlyLl5ZZREnIQ5jOVO0LdNFpu2yyXCI5ErQ0fPymZcaDEdPcunnTH88OSW9hMoxquJmqxKeCmTHkc1tdKGafox3aHL5kUG1n5SGbb552psCc_P63ItyluAF9pJrp7lPdLcxA5z0eghDl51kwD8Ryx0bkyAnwQlMJCmBwbd54PafFbK-i6zMJSjtcAtZUC_3XPQEcj0acCp6y-XWUkeyfrFK2GMYz4cyp9QcZafSjMmyuzqv4lPYiZ9qLTppw7tqzkY0qNOjo8KDdBZj3wVVNHAhqV4Mzm2YvqG77X0HOnDqrmiSR7bo5MGeRKENN3h4yd5TG2I9xxwKCO0zXDBouIfTJazY35TP1JMBPly6dggu79fn2D-54922DvIYgJbHQj6YYlo0NiYQhNW0YGpcG_ncQ7gq2l2CchI-xutnw-qFa4A1inE8dqYauSRUVYfE71LRPqT2xaLgdlOWFEdupoOVDnOjQHT-sn3eFF-QxsZa9HOBlaid_02R2tY7W_rxda-5_mgDOh0HG2L8Crlkwyfl7E5paBf3RVpcqWa2wZ63halw0ipOtLZtNcxe2hajkKaOdp99UnWoo3PYNhqfcN4iFt78anVx-MJ3Te7X1-Wsm59N764dJuUPyaOzbNkPPbsuzh2qnpp1iiiSNZEpvPDscy6dkM8u2CTTZklIxHt3P-IFDpiH3G80g0REZB9viCTHamzL8Fsrfz1nF39dNV0l2smMtn67XatA_v4roduoV_iaOg0o8a9xHVpmOCcVtQTPVtCbzPC5mOBBYuBVKEY8H6N5HgcJJ3XWAlJVUS3SNT8nzCFxC8zf3m_EcIP4vrVO9QSDWIwmCDC7JHdpOV9ZieWtkjKOmYC9HAvh3G5Qk85I5ObIvlP6IpT6U5Tq6YKUiPAXGGZMozTpIn1zrZg3nFukIQKANjfcuAnlSn5EanvY_wyga_bsz1SO07UcRvjq_KUcBliUe0am5F_qL1ZkxS84jhf-hSzQv9RPDroCFmAqzfY4w2JfiJkgPQRmRwFR3nGAY3cgY8udwBYcwxUlxVHgJytB_uqscZzLYs9QtLSj2B3DXijhD_Md_scch_xpaXueYSWZY8pdxtBfN4YGO5li-B3zEHJWAgIrZNk_aKxGDx4FAAA.&targetUrl=H4sIAAAAAAAAAEWNsQ6CMBRF_-ZtL5QWRYcXF5DJMGCCuNW2SRuxxVL-304yn3vuKWyoRH0WvLgskmr4orJGvTFsiTgrT8gE8gMk-SJtVhXdklzweRZD-CBDqbc5rcRh6q5U_nXnd7uCW3fPbOzbHBj7ho7wHB7EYJhaEvuXsm7W0XhiP_cSOt2WAAAA"}
                    ]},
                {"columns":[
                    {"name":"id","value":"107"},
                    {"name":"name","value":"Novum Hotel City B Berlin Centrum"},
                    {"name":"address","value":"Potsdamer Str. 129, Берлин, BE, 10783 Германия"},
                    {"name":"price","value":"4,522 RUB"},
                    {"name":"price_period","value":"за 1 ночь"},
                    {"name":"price_info","value":"включая налоги и сборы."},
                    {"name":"booking","value":"https://ru.hotels.com/ho252625/?pa=8&q-check-out=2018-03-25&tab=description&q-room-0-adults=2&YGF=1&q-check-in=2018-03-24&MGT=1&WOE=7&WOD=6&ZSX=0&SYE=3&q-room-0-children=0"}
                ]}
            ],
            "links":["booking"]},
            "type":"table_picker"}];
            */

    objectKeys = Object.keys;
    checks: {};
    
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
        error => {console.log(error); self.loading=false;}
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

}