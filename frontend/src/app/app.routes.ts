import { Routes } from '@angular/router';
import { SearchComponent } from './components/search/search.component';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { CustomerDetailComponent } from './components/customer-detail/customer-detail.component';
import { RegisterUserComponent } from './components/register-user/register-user.component';

export const routes: Routes = [
  { path: '', redirectTo: '/search', pathMatch: 'full' },
  { path: 'search', component: SearchComponent },
  { path: 'results', component: SearchResultsComponent },
  { path: 'customer/:masterId', component: CustomerDetailComponent },
  { path: 'register', component: RegisterUserComponent },
  { path: 'register/:masterId', component: RegisterUserComponent },
  { path: '**', redirectTo: '/search' }
];
