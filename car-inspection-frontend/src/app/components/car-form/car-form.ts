import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Navbar } from '../navbar/navbar';
import { CarService } from '../../services/car.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-car-form',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    Navbar
  ],
  templateUrl: './car-form.html',
  styleUrl: './car-form.css'
})
export class CarForm {

  car = {
    vinNumber: '',
    carNumber: '',
    carModel: '',
    inspectionType: '',
    comments: ''
  };

  selectedFile!: File;

  constructor(
    private carService:
    CarService,

    private router:
    Router
  ) {
  }

  onFileSelected(
    event: any
  ) {

    this.selectedFile =
      event.target.files[0];
  }

  onSubmit() {

    if (
      !this.car.vinNumber ||
      !this.car.carNumber ||
      !this.car.carModel ||
      !this.car.inspectionType ||
      !this.selectedFile
    ) {

      alert(
        'Please fill all required fields'
      );

      return;
    }

    const formData =
      new FormData();

    formData.append(
      'vinNumber',
      this.car.vinNumber
    );

    formData.append(
      'carNumber',
      this.car.carNumber
    );

    formData.append(
      'carModel',
      this.car.carModel
    );

    formData.append(
      'inspectionType',
      this.car.inspectionType
    );

    formData.append(
      'comments',
      this.car.comments
    );

    formData.append(
      'photo',
      this.selectedFile
    );

    this.carService
      .addCar(formData)
      .subscribe({

        next: (response) => {

          console.log(response);

          alert(
            'Car Added Successfully!'
          );

          this.resetForm();

          this.router.navigate(
            ['/car-list']
          );
        },

        error: (error) => {

          console.error(error);

          alert(
            'Failed to Add Car'
          );
        }
      });
  }

  resetForm() {

    this.car = {
      vinNumber: '',
      carNumber: '',
      carModel: '',
      inspectionType: '',
      comments: ''
    };
  }
}