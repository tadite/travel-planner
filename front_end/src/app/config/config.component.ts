import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {HttpService} from '../http/http.service';
import {AuthService} from "../auth/auth.service";
import {CookieService} from 'ngx-cookie-service';
import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import {ActionFormComponent} from '../actionForm/actionForm.component'


@Component({
    selector: 'config-app',
    templateUrl: './config.component.html',
    styleUrls: ['./config.component.css'],
    providers: [HttpService, AuthService]

})

export class ConfigComponent implements OnInit {
    public loading = false;
    login: string;
    actionUrl: string = '/api/manage/action';
    dataProducerUrl: string = '/api/manage/producer';
    sourceUrl: string = '/api/manage/source';
    dataProducerNamesUrl: string = '/api/manage/producer/namesOnly';
    Action: any;
    ELEMENT_DATA: any;

    actionTypes: string[] = ['table_integration','no_view_text_integration','radiolist_integration','radiolist',
        'dropdown_integration','info_integration','checklist_integration','date_interval_input','dropdown_input','text_input','checklist'];

    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService, private modalService: BsModalService) {
        this.actions = new Array<Action>();
    }

    //типы шаблонов
    @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
    @ViewChild('editTemplate') editTemplate: TemplateRef<any>;

    editedAction: Action;
    // actions: Array<Action>;
    actions: any;
    isNewRecord: boolean;
    statusMessage: string;

    ngOnInit() {
        this.login = this.getLogin();
        this.loadActions();
    }

    bsModalRef: BsModalRef;

    openActionNewModal() {
        this.http.get(this.dataProducerNamesUrl).subscribe((data: string[]) => {
                const initialState = {
                    dataProducers: data
                };

                this.bsModalRef = this.modalService.show(ActionFormComponent, {initialState});
                this.bsModalRef.content.closeBtnName = 'Close';
                this.bsModalRef.content.onClose.subscribe((result: Action) => {
                    if (result) {
                        console.log('value', this.bsModalRef.content.getFormValue());
                        //this.editedAction=this.bsModalRef.content.getFormValue();
                        this.saveModalAction(this.bsModalRef.content.getFormValue());
                    }
                })
            },
            error => {
                console.log(error);
            }
        );
    }

    //загрузка
    private loadActions() {
        this.loading = true;
        this.http.get(this.actionUrl).subscribe((data: Action[]) => {
                this.loading = false;
                this.actions = data;
                console.log(this.actions);
            },
            error => {
                console.log(error);
            }
        );
    }

    // добавление
    addAction() {
        this.editedAction = new Action("", "", "", null, "");
        this.actions.unshift(this.editedAction);
        this.isNewRecord = true;
    }

    // редактирование
    editAction(action: Action) {
        this.editedAction = new Action(action.name, action.viewName, action.type, action.parameters, action.dataProducer);
    }

    // загружаем один из двух шаблонов
    loadTemplate(action: Action) {
        if (this.editedAction && this.editedAction.name == action.name) {
            return this.editTemplate;
        } else {
            return this.readOnlyTemplate;
        }
    }

    saveModalAction(dataAction: Action) {

        this.http.postData(this.actionUrl, JSON.stringify(dataAction)).subscribe(data => {
            this.statusMessage = 'Данные успешно добавлены',
                console.log(data);
            this.loadActions();
        });
    }

    // сохраняем
    saveAction() {
        if (this.isNewRecord) {
            // добавляем
            this.http.postData(this.actionUrl, JSON.stringify(this.editedAction)).subscribe(data => {
                this.statusMessage = 'Данные успешно добавлены',
                    console.log(this.editedAction);
                this.loadActions();
            });
            this.isNewRecord = false;
            this.editedAction = null;
        } else {
            // изменяем
            let url: string = '/api/manage/action' + '/' + this.editedAction.name;
            this.http.get(url).subscribe((data: Action) => {
                this.editedAction = data;
            });
            this.http.postData(this.actionUrl, this.editedAction).subscribe(data => {
                this.statusMessage = 'Данные успешно обновлены',
                    this.loadActions();
            });
            this.editedAction = null;
        }
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
    deleteAction(action: Action) {
        let url: string = '/api/manage/action' + '/' + action.name;
        this.http.deleteDate(url).subscribe(data => {
            this.statusMessage = 'Данные успешно удалены',
                this.loadActions();
        });
    }

    logout() {
        this.authService.logout();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }


}

export class Action {
    constructor(public name: string,
                public viewName: string,
                public type: string,
                public parameters: any,
                public dataProducer: string) {
    }

}


