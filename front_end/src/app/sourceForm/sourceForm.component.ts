import {
    Component,
    Pipe,
    OnInit,
    Input
} from '@angular/core';
import { Router } from '@angular/router';
import { Console } from '@angular/core/src/console';
import {
    ReactiveFormsModule,
    FormsModule,
    FormGroup,
    FormArray,
    FormArrayName,
    FormControl,
    Validators,
    FormBuilder
} from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';


@Component({
    selector: 'model-form',
    templateUrl: './sourceForm.component.html' 
})
export class SourceFormComponent implements OnInit {
    types: string[] = [
        'http'
    ];
    myform: FormGroup;

    ngOnInit() {
        this.myform = new FormGroup({
            name: new FormControl('', [
                Validators.required,
            ]),
            url: new FormControl('', [
                Validators.required,
            ]),
            description: new FormControl('', [
                Validators.required,
            ]),
            type: new FormControl(),
            params: new FormArray([])
        });        
    }

    addParam() {
        (<FormArray>this.myform.get('params')).push(
            new FormControl('')
        );
    }

    removeParam(i: number){
        (<FormArray>this.myform.get('params')).removeAt(i);
    }
}