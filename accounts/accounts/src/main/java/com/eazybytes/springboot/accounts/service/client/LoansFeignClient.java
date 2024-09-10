package com.eazybytes.springboot.accounts.service.client;

import com.eazybytes.springboot.accounts.dto.client.CardsDto;
import com.eazybytes.springboot.accounts.dto.client.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("cards")
public interface LoansFeignClient {

    @GetMapping("/api/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber);
}
