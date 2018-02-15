import { Component } from '@angular/core';
  
@Component({
    selector: 'home-app',
    template: `<div class="page-header">
    <h1 align="center"> Планировщик путешествий </h1>
</div>
<div class="">
    <div align="center" class="form-inline">           
        <div class="form-group">           
             <button class="btn btn-lg center-block" routerLink="./login" routerLinkActive="active">Попробовать планировщик!</button>                                     
           <br>
        </div>
    </div>
    <div class="background-image" style="background-image : url('http://upload.akusherstvo.ru/image1458482.jpg')"> </div>
</div>`,
styles: [`.background-image{
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    z-index:-2;
  }`]
})
export class HomeComponent { }