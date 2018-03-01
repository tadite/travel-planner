import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {Routes, RouterModule} from '@angular/router';
import { FormsModule }   from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoadingModule} from 'ngx-loading';

import { AppComponent }   from './app.component';
import { LoginComponent }   from './login/login.component';
import { HomeComponent }   from './home/home.component';
import { NotFoundComponent }   from './not-found/not-found.component';
import { UserComponent }   from './user/user.component';
import { QuestionsComponent } from './question/questions.component';
import { AuthInterceptor } from './auth/auth.interceptor';
import { AuthGuardService }   from './auth/auth-guard.service';
 
// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent},
    { path: 'login', component: LoginComponent},    
    { path: 'user', component: UserComponent},
    { path: 'questions', component: QuestionsComponent, canActivate: [AuthGuardService]}, 
    { path: '**', component: NotFoundComponent }
    
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes, { useHash: true }), FormsModule, BrowserAnimationsModule, HttpClientModule, NgbModule.forRoot(), LoadingModule],
    declarations: [ AppComponent, HomeComponent, LoginComponent, UserComponent, NotFoundComponent, QuestionsComponent], 
   providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }, 
    CookieService,
    AuthGuardService
  ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }