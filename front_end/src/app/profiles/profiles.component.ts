import {Component, ViewChild, NgModule, OnInit} from '@angular/core';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import { CdkTableModule} from '@angular/cdk/table';
import {DataSource} from '@angular/cdk/table';
import { Router } from "@angular/router";
import {MatTableModule} from '@angular/material/table';
import { HttpService } from '../http/http.service';
import { AuthService} from "../auth/auth.service";
import { CookieService } from 'ngx-cookie-service';


@Component({
    selector: 'profiles-app',
    templateUrl: './profiles.component.html',
    styleUrls: ['./profiles.component.css'],
    providers: [HttpService, AuthService]

})

export class ProfilesComponent implements OnInit{
    displayedColumns = ['clientId', 'userName', 'firstName', 'lastName', 'email', 'age', 'countryName'];
    dataSource = new MatTableDataSource<Element>(ELEMENT_DATA);
    login: string;
    profilesUrl: string = '/api/manage/user';
    Element: any;
    @ViewChild(MatPaginator) paginator: MatPaginator;

    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService) {

    }

    ngOnInit(){
        this.login = this.getLogin();
       // this.getProfiles();
    }

    /**
     * Set the paginator after the view init since this component will
     * be able to query its view for the initialized paginator.
     */
    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
    }

    logout(){
        this.authService.logout();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }

    getProfiles(){
        this.http.get(this.profilesUrl).subscribe(value => {
                this.Element = value;

            },
            error => { }
        );
    }

}

export interface Element {

    clientId: number;
    userName: string;
    firstName: string;
    lastName: string;
    email: string;
    age: number;
    countryName: string;
}

const ELEMENT_DATA: Element[] = [
    {clientId: 1, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 2, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 3, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 4, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 5, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 6, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 7, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 8, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 9, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 10, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 11, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},
    {clientId: 12, userName: 'Иван', firstName: 'Иван', lastName: 'Иванов', email: 'ivan@mail.ru', age: 20, countryName: 'Россия'},

];


