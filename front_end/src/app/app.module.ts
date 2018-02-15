import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {Routes, RouterModule} from '@angular/router';
import { FormsModule }   from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';

import { AppComponent }   from './app.component';
import { LoginComponent }   from './login.component';
import { HomeComponent }   from './home.component';
import { NotFoundComponent }   from './not-found.component';
import { UserComponent }   from './user.component';
import { QuestionsComponent } from './questions.component';
import { AuthInterceptor } from './auth.interceptor';
 
 
// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent},
    { path: 'login', component: LoginComponent},    
    { path: 'user', component: UserComponent},
    { path: 'questions', component: QuestionsComponent}, 
    { path: '**', component: NotFoundComponent }
    
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes, { useHash: true }), FormsModule, BrowserAnimationsModule, HttpClientModule],
    declarations: [ AppComponent, HomeComponent, LoginComponent, UserComponent, NotFoundComponent, QuestionsComponent], 
   providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }, CookieService
  ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }