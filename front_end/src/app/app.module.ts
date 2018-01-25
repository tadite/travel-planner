import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {Routes, RouterModule} from '@angular/router';
import { FormsModule }   from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule }   from '@angular/common/http';
 
import { AppComponent }   from './app.component';
import { LoginComponent }   from './login.component';
import { HomeComponent }   from './home.component';
import { NotFoundComponent }   from './not-found.component';
import { UserComponent }   from './user.component';
 
// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent},
    { path: 'login', component: LoginComponent},
    { path: 'user', component: UserComponent},
    { path: '**', component: NotFoundComponent }
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes), FormsModule, BrowserAnimationsModule, HttpClientModule],
    declarations: [ AppComponent, HomeComponent, LoginComponent, UserComponent, NotFoundComponent],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }