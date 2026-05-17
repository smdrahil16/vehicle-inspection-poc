import {
  Component,
  OnInit
} from '@angular/core';

import {
  CommonModule
} from '@angular/common';

import {
  FormsModule
} from '@angular/forms';

import {
  CarService
} from '../../services/car.service';

import {
  Navbar
} from '../navbar/navbar';

@Component({
  selector: 'app-car-list',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    Navbar
  ],
  templateUrl: './car-list.html',
  styleUrl: './car-list.css'
})
export class CarList
implements OnInit {

  cars: any[] = [];

  vinNumber = '';

  searchedCar: any = null;

  showAllCars = false;

  constructor(
    private carService:
    CarService
  ) {}

  ngOnInit() {

    this.loadCars();
  }

  loadCars() {

    this.carService
      .getAllCars()
      .subscribe({

        next: (response) => {

          this.cars =
            response;
        },

        error: (error) => {

          console.error(error);
        }
      });
  }

  searchCar() {

    if (!this.vinNumber) {

      this.searchedCar =
        null;

      alert(
        'Please enter VIN Number'
      );

      return;
    }

    // clear previous result
    this.searchedCar =
      null;

    this.carService
      .getCarByVin(
        this.vinNumber
      )
      .subscribe({

        next: (response) => {

          this.searchedCar =
            response;
        },

        error: () => {

          this.searchedCar =
            null;

          this.vinNumber =
            '';

          alert(
            'No Car Found'
          );
        }
      });
  }

  toggleAllCars() {

    this.showAllCars =
      !this.showAllCars;
  }
}