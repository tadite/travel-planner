
export class User{
    
    login: string;    
    eMail: string;    
    password: string;
    firstName: string;
    lastName: string;
    countryId:string;
    cityId: string;
    age: number;
    
   
    constructor(login: string, eMail: string, password: string) {  
        this.login = login;        
        this.eMail = eMail;
        this.password = password;        
    }

    setProfileData(firstName: string, lastName: string, countryId:string, cityId: string, age: number){
        this.firstName = firstName;
        this.lastName = lastName;
        this.countryId = countryId;
        this.cityId = cityId;
        this.age = age;
    }

    
}
