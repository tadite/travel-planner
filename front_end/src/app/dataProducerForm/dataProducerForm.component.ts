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
    selector: 'dataProducer-model-form',
    templateUrl: './dataProducerForm.component.html'
})
export class DataProducerFormComponent implements OnInit {
    sources: string[] = [
        'coast-of-living',
        '1', '2'
    ];

    filterTypes: string[] = [
        'json_path',
        'list_to_object_list',
        'list_to_map',
        'regexp_replace',
        'substring',
        'list_to_map_multiple',
        'regexp_first_match',
        'split_and_get_by_index',
        'get_value_if_contains_in_array_from_json_file',
        'get_valuelist_if_in_property_from_json_array_file',
        'date_format_parse'
    ]

    filterType: any;

    myform: FormGroup;

    dataProducer: DataProducer = new DataProducer('name',
        [new Filter("json_path", {"expression": "$.[*].fields.[?(@.title)]"})],
        "coast-of-living",
        [new Mapper('dropdownlist-citiesByCountryId-action.Value', 'city', null, null),
            new Mapper('', 'city', 'RUB', null)]);

    public onClose: Subject<boolean> = new Subject<boolean>();

    constructor(public bsModalRef: BsModalRef){}

    public getFormValue(): DataProducer {
        return this.myform.getRawValue();
    }

    ngOnInit() {
        console.log(this.dataProducer);
        //this.loadSourceNames();
        this.myform = new FormGroup({
            name: new FormControl(this.dataProducer.name, [
                Validators.required,
            ]),
            source: new FormControl(this.dataProducer.source),
            filters: new FormArray([]),
            mappers: new FormArray([])
        });
        /*if (this.source.params != null)
            this.source.params.forEach(param => (<FormArray>this.myform.get('params')).push(new FormControl(param)));*/
    }

    addFilter(filterType: any) {
        console.log(filterType);
        this.addFilterBase((<FormArray>this.myform.get('filters')), filterType);
    }

    addFilterBase(fArray: FormArray, filterType: any) {
        if (filterType == 'json_path' || filterType == 'list_to_object_list')
            fArray.push(this.initFilter(filterType));
    }

    initFilter(filterType: any): FormGroup {
        switch (filterType) {
            case 'json_path':
                return new FormGroup({
                    type: new FormControl('json_path'),
                    parameters: new FormGroup(
                        {
                            expression: new FormControl('')
                        })
                });
            case 'list_to_object_list':
                return new FormGroup({
                        type: new FormControl('list_to_object_list'),
                        parameters: new FormGroup(
                            {
                                keyName: new FormControl(''),
                                valueNames: new FormArray([new FormControl('')])
                            })
                    }
                );
        }
    }

    addFilterToMapper(filterType: any, i: number, j: number) {
        (<FormArray>this.myform.get('mappers')).at(i).get('filter').setValue(this.initFilter(filterType));
    }

    addMapper() {
        (<FormArray>this.myform.get('mappers')).push(new FormGroup({
            from: new FormControl(''),
            to: new FormControl(''),
            def: new FormControl(''),
            filter: new FormGroup({})
        }));
    }

    mapperHaveFilter(i: number): boolean {
        return ((typeof((<FormArray>this.myform.get('mappers')).at(i).get('filter')) != null) &&
            (typeof((<FormArray>this.myform.get('mappers')).at(i).get('filter')) != undefined));
    }

    removeMapper(i: number) {
        (<FormArray>this.myform.get('mappers')).removeAt(i);
    }

    addParam() {
        (<FormArray>this.myform.get('params')).push(
            new FormControl('')
        );
    }

    removeFilter(i: number) {
        (<FormArray>this.myform.get('filters')).removeAt(i);
    }

    removeValueName(i: number, j: number) {
        (<FormArray>(<FormArray>this.myform.get('filters')).at(i).get('parameters').get('valueNames')).removeAt(j);
    }

    addValueName(i: number) {
        (<FormArray>(<FormArray>this.myform.get('filters')).at(i).get('parameters').get('valueNames')).push(new FormControl(''));
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