import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate, keyframes, query, stagger } from '@angular/animations';
import { HttpService } from '../http/http.service';
import { Question } from './question';
import {HttpClientModule, HttpClient} from '@angular/common/http';
import { Console } from '@angular/core/src/console';
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';
import { AuthService } from '../auth/auth.service';


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

   // questions: string = '[{"id":"1", "data": "Выберите страну", "type": "label"}, {"id": "5", "data": "Россия", "type": "checkbox"}, {"id": "3","data": "Франция", "type": "checkbox"}, {"id": "6","data": "Китай", "type": "checkbox"}]';
   //questions: string;
    //questions: any;
    questionData : Question[];
    
    //url: string = 'http://localhost:8090/action';    
    url: string = '/action';    
    
    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService) {}
    
    ngOnInit(){
        this.http.get(this.url).subscribe(value => {
            this.questionData = value;
            console.log('value id ' + value['id']); 
            console.log('value data ' + value['data']); 
            console.log('questionsData (data) ' + this.questionData['data']);
          
        },
        error => { }
    );
    }

    logout(){
        this.authService.logout();
    }
}