import { Component } from '@angular/core';
import { trigger, state, style, transition, animate, keyframes, query, stagger } from '@angular/animations';

  
@Component({
    selector: 'home-app',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css'],
    animations: [
        trigger('visibilityChanged', [
            state('shown' , style({ opacity: 1, display: 'inline' })),
            state('hidden', style({ opacity: 0, display: 'none' })),
            transition('shown => hidden', animate('300ms')),
            transition('hidden => shown', animate('300ms')),
        ])
    ]
})
export class HomeComponent {

    txt_stateBerlin: string = 'hidden';
    img_stateBerlin: string = 'shown';
    txt_stateLondon: string = 'hidden';
    img_stateLondon: string = 'shown';
    txt_stateStambul: string = 'hidden';
    img_stateStambul: string = 'shown';
    txt_stateGreece: string = 'hidden';
    img_stateGreece: string = 'shown';
    txt_stateSpain: string = 'hidden';
    img_stateSpain: string = 'shown';
    txt_stateItaly: string = 'hidden';
    img_stateItaly: string = 'shown';

    animateBerlin() {
        this.txt_stateBerlin = (this.txt_stateBerlin === 'shown' ? 'hidden' : 'shown');
        this.img_stateBerlin = (this.img_stateBerlin === 'shown' ? 'hidden' : 'shown')
    }
    animateLondon() {
        this.txt_stateLondon = (this.txt_stateLondon === 'shown' ? 'hidden' : 'shown');
        this.img_stateLondon = (this.img_stateLondon === 'shown' ? 'hidden' : 'shown')
    }
    animateStambul() {
        this.txt_stateStambul = (this.txt_stateStambul === 'shown' ? 'hidden' : 'shown');
        this.img_stateStambul = (this.img_stateStambul === 'shown' ? 'hidden' : 'shown')
    }
    animateGreece() {
        this.txt_stateGreece = (this.txt_stateGreece === 'shown' ? 'hidden' : 'shown');
        this.img_stateGreece = (this.img_stateGreece === 'shown' ? 'hidden' : 'shown')
    }
    animateSpain() {
        this.txt_stateSpain = (this.txt_stateSpain === 'shown' ? 'hidden' : 'shown');
        this.img_stateSpain = (this.img_stateSpain === 'shown' ? 'hidden' : 'shown')
    }
    animateItaly() {
        this.txt_stateItaly = (this.txt_stateItaly === 'shown' ? 'hidden' : 'shown');
        this.img_stateItaly = (this.img_stateItaly === 'shown' ? 'hidden' : 'shown')
    }


}