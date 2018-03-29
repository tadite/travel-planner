import {
    Component,
    Pipe,
    OnInit,
    Input
} from '@angular/core';
import {Router} from '@angular/router';
import {Console} from '@angular/core/src/console';
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
import {BrowserModule} from '@angular/platform-browser';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {Subject} from 'rxjs/Subject';
import {Sourse} from '../sourse/source';


@Component({
    selector: 'model-form',
    templateUrl: './sourceForm.component.html'
})
export class SourceFormComponent implements OnInit {
    types: string[] = [
        'http'
    ];
    myform: FormGroup;

    source: Sourse = new Sourse('', '', '', '', []);

    public onClose: Subject<boolean> = new Subject<boolean>();

    constructor(public bsModalRef: BsModalRef) {
    }

    public getFormValue():Sourse {
        return this.myform.getRawValue();
    }

    ngOnInit() {
        console.log(this.source);
        this.myform = new FormGroup({
            name: new FormControl(this.source.name, [
                Validators.required,
            ]),
            url: new FormControl(this.source.url, [
                Validators.required,
            ]),
            description: new FormControl(this.source.description, [
                Validators.required,
            ]),
            type: new FormControl(this.source.type),
            params: new FormArray([])
        });
        if (this.source.params!=null)
            this.source.params.forEach(param => (<FormArray>this.myform.get('params')).push(new FormControl(param)));
    }

    addParam() {
        (<FormArray>this.myform.get('params')).push(
            new FormControl('')
        );
    }

    removeParam(i: number) {
        (<FormArray>this.myform.get('params')).removeAt(i);
    }

    public onConfirm(): void {
        this.onClose.next(true);
        this.bsModalRef.hide();
    }

    public onCancel(): void {
        this.onClose.next(false);
        this.bsModalRef.hide();
    }
}