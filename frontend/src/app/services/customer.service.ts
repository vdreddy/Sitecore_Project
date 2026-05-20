import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  CustomerSearchRequest,
  CustomerSearchResult,
  CustomerDetailResponse,
  RegistrationRequest,
  ProfileUpdateRequest,
  DisableRequest,
  AuditLog
} from '../models/customer.model';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  searchCustomers(request: CustomerSearchRequest): Observable<CustomerSearchResult[]> {
    return this.http.post<CustomerSearchResult[]>(`${this.baseUrl}/customers/search`, request);
  }

  getCustomerDetail(masterId: string): Observable<CustomerDetailResponse> {
    return this.http.get<CustomerDetailResponse>(`${this.baseUrl}/customers/${masterId}`);
  }

  registerUser(request: RegistrationRequest): Observable<CustomerDetailResponse> {
    return this.http.post<CustomerDetailResponse>(`${this.baseUrl}/customers/register`, request);
  }

  updateProfile(masterId: string, request: ProfileUpdateRequest): Observable<CustomerDetailResponse> {
    return this.http.put<CustomerDetailResponse>(`${this.baseUrl}/customers/${masterId}/profile`, request);
  }

  enableUser(masterId: string): Observable<CustomerDetailResponse> {
    return this.http.put<CustomerDetailResponse>(`${this.baseUrl}/customers/${masterId}/enable`, {});
  }

  disableUser(masterId: string, request: DisableRequest): Observable<CustomerDetailResponse> {
    return this.http.put<CustomerDetailResponse>(`${this.baseUrl}/customers/${masterId}/disable`, request);
  }

  deleteUser(masterId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/customers/${masterId}`);
  }

  getAuditLogs(masterId: string): Observable<AuditLog[]> {
    return this.http.get<AuditLog[]>(`${this.baseUrl}/audit/${masterId}`);
  }
}
