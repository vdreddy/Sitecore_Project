export interface CustomerSearchRequest {
  searchType: string;
  ssn?: string;
  birthYear?: number;
  taxId?: string;
  policyNumber?: string;
  firstName?: string;
  lastName?: string;
  address?: string;
  username?: string;
  email?: string;
}

export interface CustomerSearchResult {
  masterId: string;
  policyOwnerName: string;
  policyNumber: string;
  policyStatus: string;
  role: string;
  address: string;
  username: string;
}

export interface CustomerDetailResponse {
  personalInfo: PersonalInfo;
  userInfo: UserInfo;
  policies: PolicyInfo[];
  registered: boolean;
}

export interface PersonalInfo {
  name: string;
  address: string;
  city: string;
  state: string;
  zip: string;
  country: string;
  dateOfBirth: string;
  ssn: string;
  taxId: string;
}

export interface UserInfo {
  masterId: string;
  username: string;
  email: string;
  mobileNumber: string;
  userStatus: string;
  disableReason: string;
  creationDate: string;
  lastLoginDate: string;
}

export interface PolicyInfo {
  policyNumber: string;
  policyStatus: string;
  role: string;
  policyType: string;
  product: string;
  issueDate: string;
  portalVisibility: boolean;
}

export interface RegistrationRequest {
  customerType: string;
  ssn: string;
  yearOfBirth: number;
  phoneNumber: string;
  email: string;
  username: string;
  firstName?: string;
  lastName?: string;
  address?: string;
  city?: string;
  state?: string;
  zip?: string;
  country?: string;
  taxId?: string;
  companyName?: string;
}

export interface ProfileUpdateRequest {
  phoneNumber?: string;
  email?: string;
  address?: string;
  city?: string;
  state?: string;
  zip?: string;
  country?: string;
}

export interface DisableRequest {
  reason: string;
}

export interface AuditLog {
  id: number;
  adminUsername: string;
  action: string;
  targetMasterId: string;
  details: string;
  timestamp: string;
}
