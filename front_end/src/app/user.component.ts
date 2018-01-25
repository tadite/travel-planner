import { Component} from '@angular/core';
  
@Component({
    selector: 'user-app',
    template: `<div class="page-header">
    <h1 align="center"> Планировщик путешествий </h1>
</div>
<div class="panel">
    <div align="center" class="form-inline">           
        <div class="form-group">      
        <p>Вы уже знаете, каким будет Ваше будущее путешествие?</p>     
             <button class="btn" style="width:120x; height:50px" [routerLink]="['./notepad']" routerLinkActive="active">Да </button>             
             <button class="btn" style="width:120x; height:50px" [routerLink]="['./questionnaire']" routerLinkActive="active">Нет</button>                                              
           <br>
        </div>
    </div>
    
</div>`
})
export class UserComponent { }