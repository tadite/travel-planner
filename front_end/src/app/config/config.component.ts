import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import { Router } from "@angular/router";
import { HttpService } from '../http/http.service';
import { AuthService} from "../auth/auth.service";
import { CookieService } from 'ngx-cookie-service';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';


@Component({
    selector: 'config-app',
    templateUrl: './config.component.html',
    styleUrls: ['./config.component.css'],
    providers: [HttpService, AuthService]

})

export class ConfigComponent implements OnInit{
    login: string;
    actionUrl: string = '/api/manage/action';
    dataProducerUrl: string = '/api/manage/producer';
    sourceUrl: string = '/api/manage/source';
    ELEMENT_DATA: any;
    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService) {
        this.actions = new Array<Action>();
    }

    //типы шаблонов
    @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
    @ViewChild('editTemplate') editTemplate: TemplateRef<any>;

    editedAction: Action;
    actions: Array<Action>;
    isNewRecord: boolean;
    statusMessage: string;

    ngOnInit() {
        this.login = this.getLogin();
        this.loadActions();
    }

    //загрузка
    private loadActions() {
        this.http.get(this.actionUrl).subscribe((data: Action[]) => {
            this.actions = data;
            console.log(this.actions);
            },
            error => {console.log(error); }
            );
    }

    // добавление
    addAction() {
        this.editedAction = new Action("","","",{},"");
        this.actions.unshift(this.editedAction);
        this.isNewRecord = true;
    }

    // редактирование
    editAction(action: Action) {
        this.editedAction = new Action(action.name, action.viewName, action.type, action.parameters, action.dataProducerName);
    }
    // загружаем один из двух шаблонов
    loadTemplate(action: Action) {
        if (this.editedAction && this.editedAction.name == action.name) {
            return this.editTemplate;
        } else {
            return this.readOnlyTemplate;
        }
    }
    // сохраняем
    saveAction() {
        if (this.isNewRecord) {
            // добавляем
            this.http.postData(this.actionUrl,this.editedAction).subscribe(data => {
                this.statusMessage = 'Данные успешно добавлены',
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
            this.http.postData(this.actionUrl,this.editedAction).subscribe(data => {
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

    logout(){
        this.authService.logout();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }


}

export class Action{
    constructor(
        public name: string,
        public viewName: string,
        public type: string,
        public parameters: {[key:string]:object;},
        public dataProducerName: string) { }

}


