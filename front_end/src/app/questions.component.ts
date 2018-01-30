import { Component } from '@angular/core';
import { trigger, state, style, transition, animate, keyframes, query, stagger } from '@angular/animations';
import { HttpService } from './http.service';

@Component({
    selector: 'questions-app',
    templateUrl: './questions.component.html',
    styleUrls: ['./questions.component.css'],
    animations: [
        trigger('visibilityChanged', [
            state('shown' , style({ opacity: 1, display: 'inline' })),
            state('hidden', style({ opacity: 0, display: 'none' })),
            transition('shown => hidden', animate('300ms')),
            transition('hidden => shown', animate('300ms')),
          ])     
    ],
    providers: [HttpService]
})

export class QuestionsComponent { 
    q1_state: string = 'shown';
    q2_state: string = 'hidden';
    q3_state: string = 'hidden';
    q4_state: string = 'hidden';
    
    nextQuestion() {

        if (this.q1_state === 'shown' ) this.q1_state = 'hidden';
        if (this.q2_state === 'hidden' ) this.q2_state = 'shown';
        if (this.q2_state === 'shown' ) this.q2_state = 'hidden';
        if (this.q3_state === 'hidden' ) this.q3_state = 'shown';
        if (this.q3_state === 'shown' ) this.q3_state = 'hidden';
        if (this.q4_state === 'hidden' ) this.q4_state = 'shown';
        if (this.q4_state === 'shown' ) this.q4_state = 'hidden';
    }

}