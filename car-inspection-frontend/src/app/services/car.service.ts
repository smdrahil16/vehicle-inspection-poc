import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CarService {

  private baseUrl =
    'https://ca-8bd6ff4508d04e199bda6738c8caf0c9.ecs.ap-south-1.on.aws';

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