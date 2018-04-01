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
    selector: 'jump-model-form',
    templateUrl: './jumpForm.component.html'
})
export class JumpFormComponent implements OnInit {
    jumpTypes: string[];

    actions: string[] = [
        '1','2'
    ];

    from: string;
    to: string;

    myform: FormGroup;

    action:any;

    public onClose: Subject<boolean> = new Subject<boolean>();

    constructor(public bsModalRef: BsModalRef) {
    }

    public getFormValue():Sourse {
        return this.myform.getRawValue();
    }

    ngOnInit() {
        this.jumpTypes= [
            'without-condition','logic-condition-on-pick-result'
        ];

        console.log(this.actions);
        this.myform = new FormGroup({
            from: new FormControl(this.from, [
                Validators.required,
            ]),
            to: new FormControl(this.to, [
                Validators.required,
            ]),
            action: new FormControl(this.action, [
                Validators.required,
            ]),
            jumpType: new FormControl('', [
                Validators.required,
            ])
        });
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