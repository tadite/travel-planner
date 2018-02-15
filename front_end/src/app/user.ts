
export class User{
    login: string;    
    eMail: string;    
    password: string;
    
   
    constructor(login: string, eMail: string, password: string) {  
        this.login = login;        
        this.eMail = eMail;
        this.password = password;        
    }
}
