package com.project.customer.walkerSearch.service;

import com.project.customer.user.repository.UserRepository;
import com.project.customer.walkerSearch.repository.WalkerPriceRepository;
import com.project.customer.walkerSearch.repository.WalkerReserveRepository;
import com.project.customer.walkerSearch.repository.WalkerSchedulePermRepository;
import com.project.customer.walkerSearch.repository.WalkerScheduleTempRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WalkerSearchServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalkerSchedulePermRepository walkerSchedulePermRepository;

    @Mock
    private WalkerScheduleTempRepository walkerScheduleTempRepository;

    @Mock
    private WalkerPriceRepository walkerPriceRepository;

    @Mock
    private WalkerReserveRepository walkerReserveRepository;

    @InjectMocks
    private WalkerSearchService walkerSearchService;

    @Test
    void readAll() {

    }
}