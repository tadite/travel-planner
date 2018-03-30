import {Component, OnInit, TemplateRef, ViewChild, Input} from '@angular/core';
import { Router } from "@angular/router";
import { HttpService } from '../http/http.service';
import { AuthService} from "../auth/auth.service";
import { CookieService } from 'ngx-cookie-service';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {SourceFormComponent} from '../sourceForm/sourceForm.component';
import {Sourse} from './source';


@Component({
    selector: 'sourse-app',
    templateUrl: './sourse.component.html',
    styleUrls: ['./sourse.component.css'],
    providers: [HttpService, AuthService]

})

export class SourseComponent implements OnInit{
    public loading = false;
    login: string;
    actionUrl: string = '/api/manage/action';
    dataProducerUrl: string = '/api/manage/producer';
    sourceUrl: string = '/api/manage/source';
    ELEMENT_DATA: any;
    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService, private modalService: BsModalService) {
        this.actions = new Array<Sourse>();
    }

    //типы шаблонов
    @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
    @ViewChild('editTemplate') editTemplate: TemplateRef<any>;

    actions: Array<Sourse>;
    statusMessage: string;

    ngOnInit() {
        this.login = this.getLogin();
        this.loadActions();
    }

    bsModalRef: BsModalRef;

    //Open Modal
    openModalNew() {
        this.openModalEdit(new Sourse('','','','',['']));
    }

    openModalEdit(source: Sourse) {
        const initialState = { source: source};
        this.bsModalRef = this.modalService.show(SourceFormComponent, {initialState});
        this.bsModalRef.content.closeBtnName = 'Close';
        this.bsModalRef.content.onClose.subscribe((result :Sourse)=> {
            if (result){
                console.log('value', this.bsModalRef.content.getFormValue());
                //this.editedAction=this.bsModalRef.content.getFormValue();
                this.saveAction(this.bsModalRef.content.getFormValue());
            }
        })
    }

    loadTemplate(action: Sourse) {
        return this.readOnlyTemplate;
    }

    //загрузка
    private loadActions() {
        this.loading = true;
        this.http.get(this.sourceUrl).subscribe((data: Sourse[]) => {
                this.loading = false;
                this.actions = data;
                console.log(this.actions);
            },
            error => {console.log(error); }
        );
    }

    // сохраняем
    saveAction(source : Sourse) {
        this.http.postData(this.sourceUrl,source).subscribe(data => {
            this.statusMessage = 'Данные успешно обновлены',
                this.loadActions();
        });
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
