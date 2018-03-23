import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import { Router } from "@angular/router";
import { HttpService } from '../http/http.service';
import { AuthService} from "../auth/auth.service";
import { CookieService } from 'ngx-cookie-service';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';


@Component({
    selector: 'sourse-app',
    templateUrl: './sourse.component.html',
    styleUrls: ['./sourse.component.css'],
    providers: [HttpService, AuthService]

})

export class SourseComponent implements OnInit{
    login: string;
    actionUrl: string = '/api/manage/action';
    dataProducerUrl: string = '/api/manage/producer';
    sourceUrl: string = '/api/manage/source';
    ELEMENT_DATA: any;
    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService) {
        this.actions = new Array<Sourse>();
    }

    //типы шаблонов
    @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
    @ViewChild('editTemplate') editTemplate: TemplateRef<any>;

    editedAction: Sourse;
    actions: Array<Sourse>;
    isNewRecord: boolean;
    statusMessage: string;

    ngOnInit() {
        this.login = this.getLogin();
        this.loadActions();
    }

    //загрузка
    private loadActions() {
        this.http.get(this.sourceUrl).subscribe((data: Sourse[]) => {
                this.actions = data;
                console.log(this.actions);
            },
            error => {console.log(error); }
        );
    }

    // добавление
    addAction() {
        this.editedAction = new Sourse("","","","",[]);
        this.actions.unshift(this.editedAction);
        this.isNewRecord = true;
    }

    // редактирование
    editAction(action: Sourse) {
        this.editedAction = new Sourse(action.name, action.type, action.url, action.description, action.params);
    }
    // загружаем один из двух шаблонов
    loadTemplate(action: Sourse) {
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
            this.http.postData(this.sourceUrl,JSON.stringify(this.editedAction)).subscribe(data => {
                this.statusMessage = 'Данные успешно добавлены',
                    console.log(this.editedAction);
                    this.loadActions();
            });
            this.isNewRecord = false;
            this.editedAction = null;
        } else {
            // изменяем
            let url: string = '/api/manage/source' + '/' + this.editedAction.name;
            this.http.get(url).subscribe((data: Sourse) => {
                this.editedAction = data;
            });
            this.http.postData(this.sourceUrl,this.editedAction).subscribe(data => {
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
    deleteAction(action: Sourse) {
        let url: string = '/api/manage/source' + '/' + action.name;
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

export class Sourse{
    constructor(
        public name: string,
        public type: string,
        public url: string,
        public description: string,
        public params:string[]) { }

}


