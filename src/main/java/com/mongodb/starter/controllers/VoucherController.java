package com.mongodb.starter.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.VoucherDTO;
import com.mongodb.starter.usecases.interfaces.VoucherUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/vouchers")
@Tag(name = "vouchers")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class VoucherController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherUsecase voucherUsecase;

    public VoucherController(VoucherUsecase voucherUsecase) {
        this.voucherUsecase = voucherUsecase;
    }

    @Operation(summary = "Get all vouchers", description = "Retrieves a list of all vouchers in the system. Can be filtered by providing a list of IDs.", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved vouchers")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherDTO> getVouchers(
            @Parameter(description = "Optional list of voucher IDs to filter by") 
            @RequestParam(required = false) List<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            return voucherUsecase.getVouchersByIds(ids);
        }
        return voucherUsecase.getAllVouchers();
    }
    
    @Operation(summary = "Get voucher by ID", description = "Retrieves a voucher by its ID", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the voucher"),
            @ApiResponse(responseCode = "404", description = "Voucher not found"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<VoucherDTO> getVoucherById(@PathVariable String id) {
        return voucherUsecase.getVoucherById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Create voucher", description = "Creates a new voucher", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "201", description = "Successfully created voucher")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoucherDTO createVoucher(@RequestBody VoucherDTO voucherDTO) {
        return voucherUsecase.createVoucher(voucherDTO);
    }
    
    @Operation(summary = "Update voucher", description = "Updates an existing voucher", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the voucher"),
            @ApiResponse(responseCode = "404", description = "Voucher not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VoucherDTO> updateVoucher(@PathVariable String id, @RequestBody VoucherDTO voucherDTO) {
        try {
            VoucherDTO updatedVoucher = voucherUsecase.updateVoucher(id, voucherDTO);
            return ResponseEntity.ok(updatedVoucher);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error updating voucher: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Delete voucher", description = "Deletes a voucher by its ID", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the voucher"),
            @ApiResponse(responseCode = "404", description = "Voucher not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable String id) {
        try {
            voucherUsecase.deleteVoucher(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error deleting voucher: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
