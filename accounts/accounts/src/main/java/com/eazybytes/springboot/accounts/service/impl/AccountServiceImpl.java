package com.eazybytes.springboot.accounts.service.impl;

import com.eazybytes.springboot.accounts.constants.AccountsConstants;
import com.eazybytes.springboot.accounts.dto.AccountsDto;
import com.eazybytes.springboot.accounts.dto.CustomerDto;
import com.eazybytes.springboot.accounts.entity.Accounts;
import com.eazybytes.springboot.accounts.entity.Customer;
import com.eazybytes.springboot.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.springboot.accounts.exception.ResourceNotFoundException;
import com.eazybytes.springboot.accounts.mapper.AccountMapper;
import com.eazybytes.springboot.accounts.mapper.CustomerMapper;
import com.eazybytes.springboot.accounts.repository.AccountRepository;
import com.eazybytes.springboot.accounts.repository.CustomerRepository;
import com.eazybytes.springboot.accounts.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void create(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exist with Id " + customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Customer savedCustomer = customerRepository.save(customer);
        Accounts accounts = createNewAccount(savedCustomer);
        accountRepository.save(accounts);
    }

    @Override
    public CustomerDto getByPhoneNumber(String phoneNumber) {
       Customer customer = customerRepository.findByMobileNumber(phoneNumber).orElseThrow(() ->
               new ResourceNotFoundException("Customer", "mobileNumber", phoneNumber)
       );
       Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() ->
               new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())

       );
       CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
       customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
       return customerDto;
    }

    @Override
    public boolean update(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto !=null ){
            Accounts accounts = accountRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }
}
