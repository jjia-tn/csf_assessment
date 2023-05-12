import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { UploadService } from '../services/upload.service';
import { UploadResult } from '../model/upload-result';

@Component({
  selector: 'app-view',
  templateUrl: './view.component.html',
  styleUrls: ['./view.component.css']
})
export class ViewComponent implements OnInit, OnDestroy {

  bundleId = ""
  param$!: Subscription
  title!: string
  name!: string
  comments!: string
  url!: string

  constructor(private activatedRoute: ActivatedRoute, private uploadSvc: UploadService) { }

  ngOnInit(): void {
      
    this.activatedRoute.params.subscribe(
      async (params) => {
        this.bundleId = params['postId']
        let r = await this.uploadSvc.getBundleByBundleId(this.bundleId)
        this.title = r.title
        this.name = r.name
        this.comments = r.comments
        this.url = r.url
      }
    )
  }

  ngOnDestroy(): void {
    this.param$.unsubscribe()
  }

}
