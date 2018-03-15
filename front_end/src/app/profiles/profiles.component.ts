import {Component, ViewChild, NgModule, OnInit} from '@angular/core';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import { CdkTableModule} from '@angular/cdk/table';
import {DataSource} from '@angular/cdk/table';
import { Router } from "@angular/router";
import {MatTableModule} from '@angular/material/table';
import { HttpService } from '../http/http.service';
import { AuthService} from "../auth/auth.service";
import { CookieService } from 'ngx-cookie-service';
import 'rxjs/add/operator/map';



@Component({
    selector: 'profiles-app',
    templateUrl: './profiles.component.html',
    styleUrls: ['./profiles.component.css'],
    providers: [HttpService, AuthService]

})

export class ProfilesComponent implements OnInit{
    displayedColumns = ['clientId', 'login', 'firstName', 'lastName', 'email', 'age', 'countryName', 'isBlocked'];

    login: string;
    profilesUrl: string = '/api/manage/user';
    blocProfilesUrl: string = '/api/manage/user/block';
    //Element: any;
    ELEMENT_DATA: Element[];
    dataSource: any;

    @ViewChild(MatPaginator) paginator: MatPaginator;

    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService) {

    }

    ngOnInit(){
        this.login = this.getLogin();
        this.getProfiles();

     // this.dataSource.paginator = this.paginator;

    }

    logout(){
        this.authService.logout();
    }

    getLogin(): string {
        return this.authService.getLogin();
    }


    getProfiles(){
        this.http.get(this.profilesUrl).subscribe(value => {
           this.ELEMENT_DATA=value;
                console.log(value);
                console.log(this.ELEMENT_DATA);
                this.dataSource = new MatTableDataSource<Element>(this.ELEMENT_DATA);
                console.log(this.dataSource);
                this.dataSource.paginator = this.paginator;
            },
            error => {console.log(error); }
        );
    }

    blocUser(element: Element) {
        this.http.postData(this.blocProfilesUrl, element.clientId).subscribe(result => {
                element.isBlocked = !element.isBlocked;
            },
            error => {
                console.log(error);
            }
        );
    }

}

export interface Element {

    clientId: number;
    login: string;
    firstName: string;
    lastName: string;
    email: string;
    age: number;
    countryName: string;
    isBlocked: boolean;
}

/*const ELEMENT_DATA: Element[] = [
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
*/

