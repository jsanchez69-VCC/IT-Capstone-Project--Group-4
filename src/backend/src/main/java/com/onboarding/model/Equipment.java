package com.onboarding.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String type; // e.g. "Laptop", "iPad", "Cell Phone"

    @Column(unique = true)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status = EquipmentStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "issued_to_employee_id")
    private Employee issuedTo;

    private LocalDate issuedDate;

    private LocalDate returnedDate;

    // Signed receipt confirmation - simple flag + text for the basic version
    private boolean receiptSigned = false;

    public Equipment() {}

    public Equipment(String type, String serialNumber) {
        this.type = type;
        this.serialNumber = serialNumber;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }

    public EquipmentStatus getStatus() { return status; }
    public void setStatus(EquipmentStatus status) { this.status = status; }

    public Employee getIssuedTo() { return issuedTo; }
    public void setIssuedTo(Employee issuedTo) { this.issuedTo = issuedTo; }

    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }

    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate returnedDate) { this.returnedDate = returnedDate; }

    public boolean isReceiptSigned() { return receiptSigned; }
    public void setReceiptSigned(boolean receiptSigned) { this.receiptSigned = receiptSigned; }
}
