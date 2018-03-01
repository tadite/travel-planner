import {Component, ViewChild, NgModule } from '@angular/core';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import { CdkTableModule} from '@angular/cdk/table';
import {DataSource} from '@angular/cdk/table';
import { Router } from "@angular/router";
import {MatTableModule} from '@angular/material/table';


@Component({
    selector: 'admin-app',
    templateUrl: './admin.component.html',
    styleUrls: ['./admin.component.css'],

})

export class AdminComponent {
    displayedColumns = ['position', 'name', 'weight', 'symbol'];
    dataSource = new MatTableDataSource<Element>(ELEMENT_DATA);

    @ViewChild(MatPaginator) paginator: MatPaginator;

    /**
     * Set the paginator after the view init since this component will
     * be able to query its view for the initialized paginator.
     */
    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
    }
}

export interface Element {
    name: string;
    position: number;
    date: number;
    autor: string;
}

const ELEMENT_DATA: Element[] = [
    {position: 1, name: 'Moscow', date: 1.79, autor: 'H'},
    {position: 2, name: 'Paris', date: 4.0026, autor: 'He'},
    {position: 3, name: 'Berlin', date: 6.941, autor: 'Li'},
    {position: 4, name: 'Beryllium', date: 9.0122, autor: 'Be'},
    {position: 5, name: 'Boron', date: 10.811, autor: 'B'},
    {position: 6, name: 'Carbon', date: 12.0107, autor: 'C'},

];


