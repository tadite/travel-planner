import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse, HttpParams} from '@angular/common/http';
import {User} from '../user/user';
import { Observable } from 'rxjs/Observable';
import{Question} from '../question/question';
import 'rxjs/add/operator/map';

  
@Injectable()
export class HttpService{
  
    [x: string]: any;
    constructor(private httpClient: HttpClient){ } 

    public get(url: string): Observable<any>{
        return this.httpClient.get(url);
    }

    public postObs(url: string, body: any) : Observable<any>{
        return this.httpClient.post(url, JSON.stringify(body), {headers:{'Content-Type': 'application/json'}});
    }

    public postData(user: User, url: string){
         
        return this.httpClient.post(url, user); 
    }

    getCityList(countryId: string, cityURL: string): Observable<any> {
      
      /* let params = new HttpParams();
       params = params.append('countryId', countryId);*/        
       let url: string = cityURL + '/' + countryId;
        return this.httpClient.get(url);        
    } 

    
}