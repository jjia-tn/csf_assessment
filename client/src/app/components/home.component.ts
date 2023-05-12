import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UploadService } from '../services/upload.service';
import { UploadResult } from '../model/upload-result';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  bundles$!: Observable<any>

  constructor(private activatedRoute: ActivatedRoute, private uploadSvc: UploadService) { }

  ngOnInit(): void {
      
    this.bundles$ = this.uploadSvc.getBundles()   
  }

}
