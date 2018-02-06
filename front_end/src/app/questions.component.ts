import { Component, OnInit } from '@angular/core';
import { trigger, state, style, transition, animate, keyframes, query, stagger } from '@angular/animations';
import { HttpService } from './http.service';
import { Question } from './question';
import {HttpClientModule, HttpClient} from '@angular/common/http';
import { Console } from '@angular/core/src/console';

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
    providers: [HttpClient]
  // providers: [HttpService]
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
    questions: any;
    questionData : Question[];
    jsonData : JSON;
    url: string = 'http://localhost:8090/action';


    constructor(private http: HttpClient){
   // constructor(private httpService: HttpService){
      //  this.getJSONFromServer();
        console.log(this.questions);
        this.getData(this.questions);
    }

    ngOnInit(){
         this.http.get(this.url).subscribe(data => {
            console.log('TEST toString' + data.toString); 
            this.questions = data; 
            console.log('TEST2 toString' + this.questions.toString);             
        });        
    }

   public getJSONFromServer(){
        /* this.httpClient.get('http://localhost:8090/action').subscribe((data) => 
         {
             this.questions = data; 
             console.log('TEST ' + this.questions.toString());
             console.log('TEST2 ' + data.toString());
            });*/
       
       //  this.questions = this.httpClient.get('http://localhost:8090/action').subscribe();
       
      
        
    }

    public getData(data: string) {        
        this.questionData = JSON.parse(data);       
    }
}