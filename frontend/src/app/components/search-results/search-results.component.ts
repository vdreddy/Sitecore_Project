import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CustomerSearchResult } from '../../models/customer.model';

@Component({
  selector: 'app-search-results',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './search-results.component.html',
  styleUrl: './search-results.component.scss'
})
export class SearchResultsComponent implements OnInit {
  results: CustomerSearchResult[] = [];
  searchType = '';

  constructor(private router: Router) {
    const nav = this.router.getCurrentNavigation();
    if (nav?.extras.state) {
      this.results = nav.extras.state['results'] || [];
      this.searchType = nav.extras.state['searchType'] || '';
    }
  }

  ngOnInit(): void {
    if (this.results.length === 0) {
      this.router.navigate(['/search']);
    }
  }

  viewCustomer(masterId: string): void {
    this.router.navigate(['/customer', masterId]);
  }

  backToSearch(): void {
    this.router.navigate(['/search']);
  }

  getStatusClass(status: string): string {
    if (!status) return '';
    return status.toLowerCase();
  }
}
