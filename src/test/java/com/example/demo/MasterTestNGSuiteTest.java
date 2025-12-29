package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import com.example.demo.util.TextSimilarityUtil;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Listeners(TestResultListener.class)
public class MasterTestNGSuiteTest {

    @Mock private UserRepository userRepository;
    @Mock private TicketRepository ticketRepository;
    @Mock private TicketCategoryRepository categoryRepository;
    @Mock private DuplicateRuleRepository ruleRepository;
    @Mock private DuplicateDetectionLogRepository logRepository;

    private UserService userService;
    private TicketCategoryService categoryService;
    private TicketService ticketService;
    private DuplicateRuleService ruleService;
    private DuplicateDetectionService detectionService;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, passwordEncoder);

        categoryService = new TicketCategoryServiceImpl(categoryRepository);
        ticketService = new TicketServiceImpl(
                ticketRepository, userRepository, categoryRepository
        );
        ruleService = new DuplicateRuleServiceImpl(ruleRepository);
        detectionService = new DuplicateDetectionServiceImpl(
                ticketRepository, ruleRepository, logRepository
        );
    }

    @Test(priority = 1)
    public void testRegisterUser() {
        User u = new User("Alice", "a@test.com", "password123", "USER");
        when(userRepository.existsByEmail("a@test.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        User saved = userService.registerUser(u);
        Assert.assertEquals(saved.getEmail(), "a@test.com");
    }

    @Test(priority = 2)
    public void testRegisterDuplicateEmail() {
        User u = new User("Bob", "b@test.com", "password123", "
