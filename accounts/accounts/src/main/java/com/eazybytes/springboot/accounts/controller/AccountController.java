package com.eazybytes.springboot.accounts.controller;

import com.eazybytes.springboot.accounts.constants.AccountsConstants;
import com.eazybytes.springboot.accounts.dto.AccountsContactInfoDto;
import com.eazybytes.springboot.accounts.dto.CustomerDto;
import com.eazybytes.springboot.accounts.dto.ResponseDto;
import com.eazybytes.springboot.accounts.service.IAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {
    @Autowired
    private IAccountService accountService;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @GetMapping("sayHello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Is healthy...");
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {
        return ResponseEntity.ok(buildVersion);
    }

    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion() {
        String javaVersion = environment.getProperty("JAVA_HOME");
        String mavenHome = environment.getProperty("MAVEN_HOME");
        return ResponseEntity.ok("Java version : " + javaVersion + "Maven : " + mavenHome);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactDetails() {
        return ResponseEntity.ok(accountsContactInfoDto);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> create(@Valid @RequestBody CustomerDto customerDto) {
        accountService.create(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(AccountsConstants.STATUS_201)
                        .statusMessage(AccountsConstants.MESSAGE_201)
                        .build()
                );
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountService.getByPhoneNumber(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @PutMapping("/update")
    public  ResponseEntity<ResponseDto> update(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.update(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
                                                            @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                            String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
        }
    }

}
