import { Component, ViewChild, NgModule, OnInit } from '@angular/core';
import { MatPaginator, MatTableDataSource } from '@angular/material';
import { CdkTableModule } from '@angular/cdk/table';
import { DataSource } from '@angular/cdk/table';
import { Router } from "@angular/router";
import { MatTableModule } from '@angular/material/table';
import { HttpService } from '../http/http.service';
import { AuthService } from "../auth/auth.service";
import { CookieService } from 'ngx-cookie-service';


@Component({
    selector: 'admin-app',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css'],
    providers: [HttpService, AuthService]

})

export class AdminComponent implements OnInit{
    displayedColumns = ['position', 'name', 'weight', 'symbol'];
    dataSource = new MatTableDataSource<Element>(ELEMENT_DATA);
    login: string;
    @ViewChild(MatPaginator) paginator: MatPaginator;

    constructor(private http: HttpService, private cookieService: CookieService, private router: Router, private authService: AuthService) {

    }

    ngOnInit(){
        this.login = this.getLogin();
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

}

export interface Element {
    name: string;
    position: number;
    date: string;
    autor: string;
}

const ELEMENT_DATA: Element[] = [
    {position: 1, name: 'Moscow', date: '10.10.2018', autor: 'H'},
    {position: 2, name: 'Paris', date: '10.10.2018', autor: 'He'},
    {position: 3, name: 'Berlin', date: '10.10.2018', autor: 'Li'},
    {position: 4, name: 'Beryllium', date: '10.10.2018', autor: 'Be'},
    {position: 5, name: 'Boron', date: '10.10.2018', autor: 'B'},
    {position: 6, name: 'Carbon', date: '10.10.2018', autor: 'C'},
    {position: 1, name: 'Moscow', date: '10.10.2018', autor: 'H'},
    {position: 2, name: 'Paris', date: '10.10.2018', autor: 'He'},
    {position: 3, name: 'Berlin', date: '10.10.2018', autor: 'Li'},
    {position: 4, name: 'Beryllium', date: '10.10.2018', autor: 'Be'},
    {position: 5, name: 'Boron', date: '10.10.2018', autor: 'B'},
    {position: 6, name: 'Carbon', date: '10.10.2018', autor: 'C'},

];


