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
import {DataProducer} from '../dataProducer/dataProducer';
import {Filter} from '../dataProducer/filter';
import {Mapper} from '../dataProducer/mapper';


@Component({
    selector: 'action-model-form',
    templateUrl: './actionForm.component.html'
})
export class ActionFormComponent implements OnInit {
    dataProducers: string[] = [
        'coast-of-living',
        '1', '2'
    ];

    myform: FormGroup;

    action: TableIntegrationAction = new TableIntegrationAction('table_integration', '', '', '', {
        "columnDefs": [
            {
                "key": "",
                "title": ""
            }
        ],
        "canPick": false
    });

    public onClose: Subject<boolean> = new Subject<boolean>();

    constructor(public bsModalRef: BsModalRef) {
    }

    public getFormValue(): DataProducer {
        return this.myform.getRawValue();
    }

    ngOnInit() {
        console.log(this.action);
        //this.loadSourceNames();
        this.myform = new FormGroup({
            type: new FormControl('table_integration', [
                Validators.required
            ]), name: new FormControl('', [
                Validators.required,
            ]),
            viewName: new FormControl('', [
                Validators.required,
            ]),
            dataProducer: new FormControl('', [
                Validators.required,
            ]),
            parameters: new FormGroup({
                columnDefs: new FormArray([new FormGroup({
                    key: new FormControl(''),
                    title: new FormControl('')
                })]),
                canPick: new FormControl(false)
            })
        });
    }

    public addDef() {
        (<FormArray>this.myform.get('parameters').get('columnDefs')).push(
            new FormGroup({
                key: new FormControl(''),
                title: new FormControl('')
            })
        );
    }

    public removeDef(i: number) {
        (<FormArray>this.myform.get('parameters').get('columnDefs')).removeAt(i);
    }

    public onConfirm(): void {
        this.onClose.next(true);
        //this.bsModalRef.hide();
    }

    public onCancel(): void {
        this.onClose.next(false);
        // this.bsModalRef.hide();
    }
}

export class TableIntegrationAction {
    constructor(public type: string,
                public name: string,
                public viewName: string,
                public dataProducer: string,
                public parameters: any) {
    }
}