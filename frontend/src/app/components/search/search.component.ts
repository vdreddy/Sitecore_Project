import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CustomerService } from '../../services/customer.service';
import { CustomerSearchRequest, CustomerSearchResult } from '../../models/customer.model';

@Component({
  selector: 'app-search',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.scss'
})
export class SearchComponent {
  searchType = 'SSN_BIRTHYEAR';
  ssn = '';
  birthYear: number | null = null;
  taxId = '';
  policyNumber = '';
  firstName = '';
  lastName = '';
  address = '';
  username = '';
  email = '';

  loading = false;
  error = '';

  constructor(
    private customerService: CustomerService,
    private router: Router
  ) {}

  onSearch(): void {
    this.loading = true;
    this.error = '';

    const request: CustomerSearchRequest = {
      searchType: this.searchType,
      ssn: this.ssn || undefined,
      birthYear: this.birthYear || undefined,
      taxId: this.taxId || undefined,
      policyNumber: this.policyNumber || undefined,
      firstName: this.firstName || undefined,
      lastName: this.lastName || undefined,
      address: this.address || undefined,
      username: this.username || undefined,
      email: this.email || undefined
    };

    this.customerService.searchCustomers(request).subscribe({
      next: (results: CustomerSearchResult[]) => {
        this.loading = false;
        if (results.length === 0) {
          this.error = 'No results found. Please try different search criteria.';
        } else {
          this.router.navigate(['/results'], { state: { results, searchType: this.searchType } });
        }
      },
      error: (err) => {
        this.loading = false;
        this.error = err.error?.error || 'An error occurred during search.';
      }
    });
  }

  onReset(): void {
    this.ssn = '';
    this.birthYear = null;
    this.taxId = '';
    this.policyNumber = '';
    this.firstName = '';
    this.lastName = '';
    this.address = '';
    this.username = '';
    this.email = '';
    this.error = '';
  }

  isSearchValid(): boolean {
    switch (this.searchType) {
      case 'SSN_BIRTHYEAR':
        return !!this.ssn && !!this.birthYear;
      case 'TAX_ID':
        return !!this.taxId;
      case 'POLICY_NUMBER':
        return !!this.policyNumber;
      case 'NAME_ADDRESS':
        return (!!this.firstName || !!this.lastName) && !!this.address;
      case 'USERNAME':
        return !!this.username;
      case 'EMAIL':
        return !!this.email;
      default:
        return false;
    }
  }
}
