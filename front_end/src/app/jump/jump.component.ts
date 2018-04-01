import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Router} from "@angular/router";
import {HttpService} from '../http/http.service';
import {AuthService} from "../auth/auth.service";
import {CookieService} from 'ngx-cookie-service';
import 'rxjs/Rx';
import {Observable} from 'rxjs/Observable';
import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {JumpFormComponent} from '../jumpForm/jumpForm.component'

@Component({
    selector: 'jump-app',
    templateUrl: './jump.component.html',
    styleUrls: ['./jump.component.css'],
    providers: [HttpService, AuthService]

})

export class JumpComponent implements OnInit {
    public loading = false;
    login: string;
    actionUrl: string = '/api/manage/action';
    dataProducerUrl: string = '/api/manage/producer';
    sourceUrl: string = '/api/manage/source';
    sourceNamesUrl: string = '/api/manage/source/namesOnly';
    actionNamesUrl: string = '/api/manage/source/namesOnly';
    jumpsUrl: string = '/api/manage/jump';
    jumpsInsertUrl: string = '/api/manage/jump/insert';
    ELEMENT_DATA: any;

    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService, private modalService: BsModalService) {
        this.actions = new Array<any>();
    }

    actions: Array<any>;

    statusMessage: string;

    ngOnInit() {
        this.login = this.getLogin();
        this.loadActions();
    }

    bsModalRef: BsModalRef;

    openModalNew(action: any) {
        this.http.get(this.actionNamesUrl).subscribe((data: string[]) => {
                const initialState = {
                    actions: data,
                    from: action.fromActionName,
                    to: action.toActionName
                };

                this.bsModalRef = this.modalService.show(JumpFormComponent, {initialState});
                this.bsModalRef.content.closeBtnName = 'Close';
                this.bsModalRef.content.onClose.subscribe((result: any) => {
                    if (result) {
                        console.log('value', this.bsModalRef.content.getFormValue());
                        //this.editedAction=this.bsModalRef.content.getFormValue();
                        this.insertJump(this.bsModalRef.content.getFormValue());
                    }
                })
            },
            error => {
                console.log(error);
            }
        );
    }

    private loadActions() {
        this.loading = true;
        this.http.get(this.jumpsUrl).subscribe((data: any[]) => {
                this.loading = false;
                this.actions = data;
                console.log(this.actions);
            },
            error => {
                console.log(error);
            }
        );
    }

    // сохраняем
    insertJump(jump: any) {
        console.log(jump);
        this.http.postData(this.jumpsInsertUrl, jump).subscribe(data => {
            this.statusMessage = 'Данные успешно добавлены',
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

