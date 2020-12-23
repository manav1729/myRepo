package com.manav.multiplecachemanager.bo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.manav.multiplecachemanager.entity.Customer;
import com.manav.multiplecachemanager.entity.Order;
import com.manav.multiplecachemanager.repository.CustomerDetailRepository;

@Component
public class CustomerDetailBO {

    @Autowired
    private CustomerDetailRepository customerDetailRepository;

    @Cacheable(cacheNames = "customers")
    public Customer getCustomerDetail(Integer customerId) {
        return customerDetailRepository.getCustomerDetail(customerId);
    }

    @Cacheable(cacheNames = "customerOrders", cacheManager = "alternateCacheManager")
    public List<Order> getCustomerOrders(Integer customerId) {
        return customerDetailRepository.getCustomerOrders(customerId);
    }
}
