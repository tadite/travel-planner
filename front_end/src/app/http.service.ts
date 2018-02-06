import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from './user';
  
@Injectable()
export class HttpService{
  
    constructor(private http: HttpClient){ }
      
    postData(user: User){         
      
       //const myHeaders = new HttpHeaders().set('Authorization', 'my-auth-token');
          
        //return this.http.post('http://localhost:60820/api/values', user, {headers:myHeaders}); 
        return this.http.post('http://localhost:60820/api/values', user);        
    }

    getDataFromServer(){
       return this.http.get('http://localhost:8090/action');
    }
}