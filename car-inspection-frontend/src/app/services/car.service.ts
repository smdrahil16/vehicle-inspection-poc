import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private baseUrl =
    'http://localhost:8080/api/cars';

  constructor(
    private http: HttpClient
  ) {
  }

  addCar(
    formData: FormData
  ): Observable<any> {

    return this.http.post(
      this.baseUrl,
      formData
    );
  }

  getAllCars():
    Observable<any> {

    return this.http.get(
      this.baseUrl
    );
  }

    getCarByVin(vinNumber: string): Observable<any> {

    return this.http.get(
      `${this.baseUrl}/vin/${vinNumber}`
    );
  }

}