import { HttpClient } from '@angular/common/http';
import { ElementRef, Injectable } from '@angular/core';
import { lastValueFrom } from 'rxjs';
import { UploadResult } from '../model/upload-result';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private httpClient: HttpClient) { }

  // upload(form: any, image: Blob) {
  upload(form: any, imageFile: ElementRef) {

    const formData = new FormData()
    formData.set("name", form['name'])
    formData.set("title", form['title'])
    formData.set("comments", form['comments'])
    formData.set("archive", imageFile.nativeElement.files[0])

    return lastValueFrom(this.httpClient.post<UploadResult>("/upload", formData))
  }

  getBundleByBundleId(bundleId: string) {

    return lastValueFrom(this.httpClient.get<UploadResult>("/bundle/" + bundleId))
  }

  getBundles() {

    return this.httpClient.get<UploadResult>("/bundles")
  }
}
