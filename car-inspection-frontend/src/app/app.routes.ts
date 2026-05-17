import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { CarForm } from './components/car-form/car-form';
import { CarList } from './components/car-list/car-list';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full'
  },

  {
    path: 'home',
    component: Home
  },

  {
    path: 'car-form',
    component: CarForm
  },

  {
    path: 'car-list',
    component: CarList
  }
];