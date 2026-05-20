package com.adminportal.dto;

import jakarta.validation.constraints.NotBlank;

public class DisableRequest {

    @NotBlank(message = "Disable reason is required")
    private String reason;

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
