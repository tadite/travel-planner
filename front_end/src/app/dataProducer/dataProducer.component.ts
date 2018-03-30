import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import { Router } from "@angular/router";
import { HttpService } from '../http/http.service';
import { AuthService} from "../auth/auth.service";
import { CookieService } from 'ngx-cookie-service';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import { DataProducerFormComponent} from '../dataProducerForm/dataProducerForm.component'
import {DataProducer} from './dataProducer';

@Component({
    selector: 'dataProducer-app',
    templateUrl: './dataProducer.component.html',
    styleUrls: ['./dataProducer.component.css'],
    providers: [HttpService, AuthService]

})

export class DataProducerComponent implements OnInit{
    public loading = false;
    login: string;
    actionUrl: string = '/api/manage/action';
    dataProducerUrl: string = '/api/manage/producer';
    sourceUrl: string = '/api/manage/source';
    ELEMENT_DATA: any;
    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService, private modalService: BsModalService) {
        this.actions = new Array<DataProducer>();
    }

    //типы шаблонов
    @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
    @ViewChild('editTemplate') editTemplate: TemplateRef<any>;

    editedAction: DataProducer;
    actions: Array<DataProducer>;
    isNewRecord: boolean;
    statusMessage: string;

    ngOnInit() {
        this.login = this.getLogin();
        this.loadActions();
    }

    bsModalRef: BsModalRef;

    openModalNew() {
        this.bsModalRef = this.modalService.show(DataProducerFormComponent, {});
        this.bsModalRef.content.closeBtnName = 'Close';
        this.bsModalRef.content.onClose.subscribe((result :DataProducer)=> {
            if (result){
                console.log('value', this.bsModalRef.content.getFormValue());
                //this.editedAction=this.bsModalRef.content.getFormValue();
                this.saveAction(this.bsModalRef.content.getFormValue());
            }
        })
    }

    //загрузка
    private loadActions() {
        this.loading = true;
        this.http.get(this.dataProducerUrl).subscribe((data: DataProducer[]) => {
                this.loading = false;
                this.actions = data;
                console.log(this.actions);
            },
            error => {console.log(error); }
        );
    }

    // добавление
    addAction() {

    }

    // редактирование
    editAction(action: DataProducer) {
        //this.editedAction = new DataProducer(action.name, action.filters, action.mappers, action.source);
    }
    // загружаем один из двух шаблонов
    loadTemplate(action: DataProducer) {
       // if (this.editedAction && this.editedAction.name == action.name) {
        //    return this.editTemplate;
        //} else {
            return this.readOnlyTemplate;
        //}
    }
    // сохраняем
    saveAction(dataProducer : DataProducer) {
        console.log(dataProducer);
            this.http.postData(this.dataProducerUrl,dataProducer).subscribe(data => {
                this.statusMessage = 'Данные успешно добавлены',
                    this.loadActions();
            });
    }
    // отмена редактирования
    cancel() {
        // если отмена при добавлении, удаляем последнюю запись
        if (this.isNewRecord) {
            this.actions.shift();
            this.isNewRecord = false;
        }
        this.editedAction = null;
    }
    // удаление
    deleteAction(action: DataProducer) {
        let url: string = '/api/manage/producer' + '/' + action.name;
        this.http.deleteDate(url).subscribe(data => {
            this.statusMessage = 'Данные успешно удалены',
                this.loadActions();
        });
    }

    logout(){
        this.authService.logout();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }
}
/*
export class DataProducer{
    constructor(
        public name: string,
        public filters: any,
        public mappers:  any,
        public source: string) { }

}*/


