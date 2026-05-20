import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CustomerService } from '../../services/customer.service';
import { CustomerDetailResponse, ProfileUpdateRequest, AuditLog } from '../../models/customer.model';

@Component({
  selector: 'app-customer-detail',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './customer-detail.component.html',
  styleUrl: './customer-detail.component.scss'
})
export class CustomerDetailComponent implements OnInit {
  masterId = '';
  customer: CustomerDetailResponse | null = null;
  auditLogs: AuditLog[] = [];
  loading = true;
  error = '';
  successMessage = '';

  showEditProfile = false;
  showDisableDialog = false;
  showDeleteConfirm = false;

  editProfile: ProfileUpdateRequest = {};
  disableReason = '';

  actionLoading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private customerService: CustomerService
  ) {}

  ngOnInit(): void {
    this.masterId = this.route.snapshot.paramMap.get('masterId') || '';
    this.loadCustomer();
  }

  loadCustomer(): void {
    this.loading = true;
    this.error = '';
    this.customerService.getCustomerDetail(this.masterId).subscribe({
      next: (data) => {
        this.customer = data;
        this.loading = false;
        this.loadAuditLogs();
      },
      error: (err) => {
        this.error = err.error?.error || 'Failed to load customer details.';
        this.loading = false;
      }
    });
  }

  loadAuditLogs(): void {
    this.customerService.getAuditLogs(this.masterId).subscribe({
      next: (logs) => this.auditLogs = logs,
      error: () => {}
    });
  }

  backToSearch(): void {
    this.router.navigate(['/search']);
  }

  registerUser(): void {
    this.router.navigate(['/register', this.masterId]);
  }

  openEditProfile(): void {
    if (this.customer) {
      this.editProfile = {
        phoneNumber: this.customer.userInfo.mobileNumber || '',
        email: this.customer.userInfo.email || '',
        address: this.customer.personalInfo.address || '',
        city: this.customer.personalInfo.city || '',
        state: this.customer.personalInfo.state || '',
        zip: this.customer.personalInfo.zip || '',
        country: this.customer.personalInfo.country || ''
      };
    }
    this.showEditProfile = true;
  }

  saveProfile(): void {
    this.actionLoading = true;
    this.customerService.updateProfile(this.masterId, this.editProfile).subscribe({
      next: (data) => {
        this.customer = data;
        this.showEditProfile = false;
        this.actionLoading = false;
        this.showSuccess('Profile updated successfully.');
        this.loadAuditLogs();
      },
      error: (err) => {
        this.error = err.error?.error || 'Failed to update profile.';
        this.actionLoading = false;
      }
    });
  }

  enableUser(): void {
    this.actionLoading = true;
    this.customerService.enableUser(this.masterId).subscribe({
      next: (data) => {
        this.customer = data;
        this.actionLoading = false;
        this.showSuccess('User enabled successfully.');
        this.loadAuditLogs();
      },
      error: (err) => {
        this.error = err.error?.error || 'Failed to enable user.';
        this.actionLoading = false;
      }
    });
  }

  openDisableDialog(): void {
    this.disableReason = '';
    this.showDisableDialog = true;
  }

  confirmDisable(): void {
    this.actionLoading = true;
    this.customerService.disableUser(this.masterId, { reason: this.disableReason }).subscribe({
      next: (data) => {
        this.customer = data;
        this.showDisableDialog = false;
        this.actionLoading = false;
        this.showSuccess('User disabled successfully.');
        this.loadAuditLogs();
      },
      error: (err) => {
        this.error = err.error?.error || 'Failed to disable user.';
        this.actionLoading = false;
      }
    });
  }

  openDeleteConfirm(): void {
    this.showDeleteConfirm = true;
  }

  confirmDelete(): void {
    this.actionLoading = true;
    this.customerService.deleteUser(this.masterId).subscribe({
      next: () => {
        this.showDeleteConfirm = false;
        this.actionLoading = false;
        this.router.navigate(['/search']);
      },
      error: (err) => {
        this.error = err.error?.error || 'Failed to delete user.';
        this.actionLoading = false;
      }
    });
  }

  getStatusClass(status: string): string {
    if (!status) return '';
    return status.toLowerCase();
  }

  private showSuccess(message: string): void {
    this.successMessage = message;
    setTimeout(() => this.successMessage = '', 4000);
  }
}
