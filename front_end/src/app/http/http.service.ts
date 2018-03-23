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


    public getData(url: string, body: any): Observable<any>{
        return this.httpClient.get(url,body);
    }


    public postObs(url: string, body: any) : Observable<any>{
        return this.httpClient.post(url, JSON.stringify(body), {headers:{'Content-Type': 'application/json'}});
    }
    public postData( url: string, body: any){
        return this.httpClient.post(url, body);
    }

    public deleteDate(url: string){
        return this.httpClient.delete(url);
    }

    public deleteObs(url: string) : Observable<any> {
        return this.httpClient.delete(url);
    }

    public getCityList(countryId: string, cityURL: string): Observable<any> {
      
      /* let params = new HttpParams();
       params = params.append('countryId', countryId);*/        
       let url: string = cityURL + '/' + countryId;
        return this.httpClient.get(url);        
    } 

    

}