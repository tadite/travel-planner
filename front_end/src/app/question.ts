export class Question{
    id: number;
    data: string;
    type: string;

    constructor(id: number, data: string, type: string) {  
        this.id = id;
        this.data = data;
        this.type = type;              
    }
}
