import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {Routes, RouterModule} from '@angular/router';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { CookieService } from 'ngx-cookie-service';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoadingModule} from 'ngx-loading';
import { CdkTableModule } from '@angular/cdk/table';
import { MatTableModule, MatPaginatorModule } from '@angular/material';
import { AgmCoreModule, GoogleMapsAPIWrapper } from '@agm/core';
import { ModalModule } from 'ngx-bootstrap';

import { AppComponent }   from './app.component';
import { LoginComponent }   from './login/login.component';
import { HomeComponent }   from './home/home.component';
import { NotFoundComponent }   from './not-found/not-found.component';
import { UserComponent }   from './user/user.component';
import { QuestionsComponent } from './question/questions.component';
import { AuthInterceptor } from './auth/auth.interceptor';
import { AuthGuardService }   from './auth/auth-guard.service';
import { UnauthGuardService }   from './auth/unauth-guard.service';
import  {ProfileComponent } from './profile/profile.component';
import { AdminComponent }   from './admin/admin.component';
import { SourseComponent }   from './sourse/sourse.component';
import { ConfigComponent }   from './config/config.component';
import { ProfilesComponent } from "./profiles/profiles.component";
import { DataProducerComponent } from "./dataProducer/dataProducer.component";
import { SourceFormComponent } from "./sourceForm/sourceForm.component";
import { MyTravelsComponent }   from './myTravels/myTravels.component';
import { PagerService } from './service/pager.service';
import { TravelPagerService } from './service/travel.pager.service';
import { AdminGuardService} from "./auth/admin-guard.service";
import { AuthService} from "./auth/auth.service"; 


// определение маршрутов
const appRoutes: Routes =[
    { path: '', component: HomeComponent},
    { path: 'login', component: LoginComponent, canActivate: [UnauthGuardService]},    
    { path: 'user', component: UserComponent},
    { path: 'questions', component: QuestionsComponent, canActivate: [AuthGuardService]},   
    { path: 'profile', component: ProfileComponent, canActivate: [AuthGuardService]}, 
	{ path: 'admin', component: ProfilesComponent, canActivate: [AdminGuardService]},
    { path: 'config', component: ConfigComponent, canActivate: [AdminGuardService]},
    { path: 'sourse', component: SourseComponent, canActivate: [AdminGuardService]},
    { path: 'producer', component: DataProducerComponent, canActivate: [AdminGuardService]},
    { path: 'sourceForm', component: SourceFormComponent, canActivate: [AdminGuardService]},
    { path: 'myTravels', component: MyTravelsComponent, canActivate: [AuthGuardService]},
    { path: '**', component: NotFoundComponent }
    
];

@NgModule({
    imports:      [ BrowserModule, RouterModule.forRoot(appRoutes, { useHash: true }), FormsModule, ReactiveFormsModule, BrowserAnimationsModule, HttpClientModule, NgbModule.forRoot(), LoadingModule, CdkTableModule, MatTableModule, MatPaginatorModule, LoadingModule, ModalModule.forRoot(), AgmCoreModule.forRoot({
        apiKey: 'AIzaSyD28p35CCvhDbiAPL7u96KayO36U6Ny84o'
    })],
    declarations: [ AppComponent, HomeComponent, LoginComponent, UserComponent, NotFoundComponent, QuestionsComponent, ProfileComponent, AdminComponent, ConfigComponent, ProfilesComponent, SourseComponent, DataProducerComponent, SourceFormComponent, MyTravelsComponent],
   providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true,
    },
       CookieService,
       AuthService,
       AuthGuardService,
       AdminGuardService,
       UnauthGuardService,
       GoogleMapsAPIWrapper,
       PagerService,
       TravelPagerService
  ],
    bootstrap:    [ AppComponent ]
})
export class AppModule { }