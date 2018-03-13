import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {Routes, RouterModule} from '@angular/router';
import { FormsModule }   from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoadingModule} from 'ngx-loading';
import { CdkTableModule } from '@angular/cdk/table';
import { MatTableModule, MatPaginatorModule } from '@angular/material';

import { AppComponent }   from './app.component';
import { LoginComponent }   from './login/login.component';
import { HomeComponent }   from './home/home.component';
import { NotFoundComponent }   from './not-found/not-found.component';
import { UserComponent }   from './user/user.component';
import { QuestionsComponent } from './question/questions.component';
import { AuthInterceptor } from './auth/auth.interceptor';
import { AuthGuardService }   from './auth/auth-guard.service';
import { UnauthGuardService }   from './auth/unauth-guard.service';
import { NotepadComponent }   from './notepad/notepad.component';
import  {ProfileComponent } from './profile/profile.component';
import { AdminComponent }   from './admin/admin.component';
import { ConfigComponent }   from './config/config.component';
import {ProfilesComponent} from "./profiles/profiles.component";


// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent},
    { path: 'login', component: LoginComponent, canActivate: [UnauthGuardService]},    
    { path: 'user', component: UserComponent},
    { path: 'questions', component: QuestionsComponent, canActivate: [AuthGuardService]}, 
    { path: 'notepad', component: NotepadComponent, canActivate: [AuthGuardService]},
    { path: 'profile', component: ProfileComponent, canActivate: [AuthGuardService]}, 
	{ path: 'admin', component: AdminComponent, canActivate: [AuthGuardService]},
    { path: 'config', component: ConfigComponent, canActivate: [AuthGuardService]},
    { path: 'profiles', component: ProfilesComponent, canActivate: [AuthGuardService]},	
    { path: '**', component: NotFoundComponent }
    
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes, { useHash: true }), FormsModule, BrowserAnimationsModule, HttpClientModule, NgbModule.forRoot(), LoadingModule, CdkTableModule, MatTableModule, MatPaginatorModule],
    declarations: [ AppComponent, HomeComponent, LoginComponent, UserComponent, NotFoundComponent, QuestionsComponent, NotepadComponent, ProfileComponent, AdminComponent, ConfigComponent, ProfilesComponent], 
   providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }, 
    CookieService,
    AuthGuardService,
    UnauthGuardService
  ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }