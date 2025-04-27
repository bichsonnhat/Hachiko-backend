package com.mongodb.starter.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.dtos.VoucherDTO;
import com.mongodb.starter.usecases.interfaces.VoucherUsecase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/vouchers")
@Tag(name = "vouchers")
@SecurityScheme(name = "api_key", type = SecuritySchemeType.APIKEY, in = io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER, paramName = "X-API-KEY", description = "API key for authentication. Add 'X-API-KEY' header with your API key.")
public class VoucherController {
    private final VoucherUsecase voucherUsecase;

    public VoucherController(VoucherUsecase voucherUsecase) {
        this.voucherUsecase = voucherUsecase;
    }

    @Operation(summary = "Get all vouchers", description = "Retrieves a list of all vouchers in the system", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all vouchers")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherDTO> getVouchers() {
        return voucherUsecase.getAllVouchers();
    }

    @Operation(summary = "Get all vouchers by userId", description = "Retrieves a list of all voucher for the authenticated user", security = {
            @SecurityRequirement(name = "api_key") })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all vouchers"),
            @ApiResponse(responseCode = "404", description = "User not found"),
    })
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherDTO> getFavouriteProducts(@PathVariable String userId) {
        return voucherUsecase.getVouchersByUserId(userId);
    }
}
