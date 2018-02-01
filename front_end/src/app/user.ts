
export class User{
    firstName: string;
    lastName: string;
    eMail: string;    
    password: string;
   
    constructor(firstName: string, lastName: string, eMail: string, password: string) {  
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;        
    }
}
