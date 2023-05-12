import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UploadService } from '../services/upload.service';
import { UploadResult } from '../model/upload-result';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {

  @ViewChild('file') 
  imageFile!: ElementRef;

  form!: FormGroup
  uploadResult!: UploadResult

  constructor(private router: Router, private fb: FormBuilder, private uploadSvc: UploadService) { }

  ngOnInit(): void {
    
    this.createForm()
  }

  private createForm() {

    this.form = this.fb.group({
      name: this.fb.control<string>('', [ Validators.required ]),
      title: this.fb.control<string>('', [ Validators.required ]),
      comments: this.fb.control<string>('')

    })
  }

  invalid(): boolean {
    return this.form.invalid
  }

  upload() {

    const formVal = this.form.value
    this.uploadSvc.upload(formVal, this.imageFile)
      .then((result) => {
        this.uploadResult = result
        this.router.navigate(['/view'])
      }).catch(error => console.log(error))
  }

  back() {
    this.router.navigate(['/'])
  }

}
