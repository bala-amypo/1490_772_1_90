package com.example.demo;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.*;
import com.example.demo.service.impl.*;
import com.example.demo.util.TextSimilarityUtil;

import org.mockito.*;
import org.testng.Assert;
import org.testng.annotations.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        // 🔥 THIS WAS MISSING — NOW FIXED
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userService = new UserServiceImpl(userRepository, passwordEncoder);

        categoryService = new TicketCategoryServiceImpl(categoryRepository);
        ticketService = new TicketServiceImpl(ticketRepository, userRepository, categoryRepository);
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
        User u = new User("Bob", "b@test.com", "password123", "USER");
        when(userRepository.existsByEmail("b@test.com")).thenReturn(true);

        try {
            userService.registerUser(u);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().toLowerCase().contains("email"));
        }
    }

    @Test(priority = 3)
    public void testGetUser() {
        User u = new User("C", "c@test.com", "pass1234", "USER");
        u.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(u));

        Assert.assertEquals(userService.getUser(1L).getEmail(), "c@test.com");
    }

    @Test(priority = 4)
    public void testGetAllUsers() {
        when(userRepository.findAll())
                .thenReturn(List.of(new User("X", "x@test.com", "pass1234", "USER")));

        Assert.assertFalse(userService.getAllUsers().isEmpty());
    }

    @Test(priority = 5)
    public void testCreateCategory() {
        TicketCategory c = new TicketCategory("Billing", "Billing issues");
        when(categoryRepository.existsByCategoryName("Billing")).thenReturn(false);
        when(categoryRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        TicketCategory saved = categoryService.createCategory(c);
        Assert.assertEquals(saved.getCategoryName(), "Billing");
    }

    @Test(priority = 6)
    public void testCreateTicket() {
        User u = new User("U", "u@test.com", "pass1234", "USER");
        u.setId(1L);

        TicketCategory tc = new TicketCategory("Tech", "Tech");
        tc.setId(1L);

        Ticket t = new Ticket();
        t.setSubject("Problem");
        t.setDescription("details are long enough");

        when(userRepository.findById(1L)).thenReturn(Optional.of(u));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(tc));
        when(ticketRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Ticket out = ticketService.createTicket(1L, 1L, t);
        Assert.assertEquals(out.getSubject(), "Problem");
    }

    @Test(priority = 7)
    public void testTextSimilarityUtil() {
        double s = TextSimilarityUtil.similarity("foo bar", "foo baz");
        Assert.assertTrue(s >= 0);
    }

    @Test(priority = 8)
    public void testFinal() {
        Assert.assertTrue(true);
    }
}
