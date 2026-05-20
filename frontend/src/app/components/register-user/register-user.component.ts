import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../services/customer.service';
import { RegistrationRequest } from '../../models/customer.model';

@Component({
  selector: 'app-register-user',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register-user.component.html',
  styleUrl: './register-user.component.scss'
})
export class RegisterUserComponent implements OnInit {
  masterId: string | null = null;
  loading = false;
  error = '';

  registration: RegistrationRequest = {
    customerType: 'INDIVIDUAL',
    ssn: '',
    yearOfBirth: new Date().getFullYear() - 30,
    phoneNumber: '',
    email: '',
    username: '',
    firstName: '',
    lastName: '',
    address: '',
    city: '',
    state: '',
    zip: '',
    country: 'US',
    taxId: '',
    companyName: ''
  };

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    this.masterId = this.route.snapshot.paramMap.get('masterId');
    if (this.masterId) {
      this.loadCustomerData();
    }
  }

  loadCustomerData(): void {
    if (!this.masterId) return;
    this.customerService.getCustomerDetail(this.masterId).subscribe({
      next: (data) => {
        const nameParts = data.personalInfo.name?.split(' ') || [];
        this.registration.firstName = nameParts[0] || '';
        this.registration.lastName = nameParts.slice(1).join(' ') || '';
        this.registration.address = data.personalInfo.address || '';
        this.registration.city = data.personalInfo.city || '';
        this.registration.state = data.personalInfo.state || '';
        this.registration.zip = data.personalInfo.zip || '';
        this.registration.country = data.personalInfo.country || 'US';
      },
      error: () => {}
    });
  }

  onSubmit(): void {
    this.loading = true;
    this.error = '';

    this.customerService.registerUser(this.registration).subscribe({
      next: (data) => {
        this.loading = false;
        this.router.navigate(['/customer', data.userInfo.masterId]);
      },
      error: (err) => {
        this.loading = false;
        if (err.error && typeof err.error === 'object') {
          const messages = Object.values(err.error);
          this.error = messages.join('. ');
        } else {
          this.error = 'Registration failed. Please try again.';
        }
      }
    });
  }

  onCancel(): void {
    if (this.masterId) {
      this.router.navigate(['/customer', this.masterId]);
    } else {
      this.router.navigate(['/search']);
    }
  }

  isFormValid(): boolean {
    return !!(
      this.registration.ssn &&
      this.registration.yearOfBirth &&
      this.registration.phoneNumber &&
      this.registration.email &&
      this.registration.username
    );
  }
}
