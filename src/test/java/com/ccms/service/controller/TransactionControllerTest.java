//package com.ccms.service.controller;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import com.ccms.service.exception.InvalidUsernameFormatException;
//import com.ccms.service.model.Transaction.TransactionDetail;
//import com.ccms.service.model.TransactionWithCardId;
//import com.ccms.service.service.TransactionService;
//import com.ccms.service.utilities.Decodename;
//
//import java.util.List;
//import java.util.Map;
//
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class TransactionControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private TransactionService transactionService;
//
//    @MockBean
//    private Decodename decodename;
//
//    @Test
//    public void testGetTransactionsForUser_Success() throws Exception {
//        String encodedUsername = "encodedUser123";
//        String username = "user123";
//
//        when(decodename.decodeUsername(encodedUsername)).thenReturn(username);
//
//        List<TransactionWithCardId> transactions = List.of(
//                new TransactionWithCardId(1, new TransactionDetail(1L, "2025-01-01", "10:00", "cr", 100.0, "Purchase")),
//                new TransactionWithCardId(2, new TransactionDetail(2L, "2025-01-02", "11:00", "db", 200.0, "Purchase"))
//        );
//
//        Page<TransactionWithCardId> transactionPage = new PageImpl<>(transactions);
//        when(transactionService.getTransactionsForUser(username, Pageable.unpaged())).thenReturn(transactionPage);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/transactions/{username}", encodedUsername)
//                        .param("page", "0")
//                        .param("size", "100"))
//                .andExpect(MockMvcResultMatchers.status().isOk())  // Ensure 200 OK for non-empty response
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].transactionId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].transactionId").value(2));
//    }
//
//    @Test
//    public void testGetTransactionsForUser_NoContent() throws Exception {
//        String encodedUsername = "encodedUser123";
//        String username = "user123";
//
//        when(decodename.decodeUsername(encodedUsername)).thenReturn(username);
//
//        Page<TransactionWithCardId> emptyPage = Page.empty();
//        when(transactionService.getTransactionsForUser(username, Pageable.unpaged())).thenReturn(emptyPage);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/transactions/{username}", encodedUsername)
//                        .param("page", "0")
//                        .param("size", "100"))
//                .andExpect(MockMvcResultMatchers.status().isOk())  // Ensure 200 OK even if empty
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty());  // No content should be empty, not 204
//    }
//
//    @Test
//    public void testGetTransactionsForUser_InvalidUsernameFormat() throws Exception {
//        String encodedUsername = "invalidEncodedUser";
//
//        when(decodename.decodeUsername(encodedUsername)).thenThrow(new InvalidUsernameFormatException("Invalid format"));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/transactions/{username}", encodedUsername)
//                        .param("page", "0")
//                        .param("size", "100"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // Ensure 400 Bad Request for invalid username format
//                .andExpect(MockMvcResultMatchers.jsonPath("$.error[0].error").value("Invalid username format"));
//    }
//
//    @Test
//    public void testGetMaxExpensesForLastMonth_Success() throws Exception {
//        String encodedUsername = "encodedUser123";
//        String username = "user123";
//
//        when(decodename.decodeUsername(encodedUsername)).thenReturn(username);
//
//        List<Map<String, Object>> maxExpenses = List.of(
//                Map.of("cardId", 1, "maxExpense", 500.0),
//                Map.of("cardId", 2, "maxExpense", 300.0)
//        );
//
//        Page<Map<String, Object>> maxExpensesPage = new PageImpl<>(maxExpenses);
//        when(transactionService.getMaxExpensesForLastMonth(username, "both", Pageable.unpaged())).thenReturn(maxExpensesPage);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/transactions/maxExpenses/lastMonth/{username}", encodedUsername)
//                        .param("status", "both")
//                        .param("page", "0")
//                        .param("size", "100"))
//                .andExpect(MockMvcResultMatchers.status().isOk())  // Ensure 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].maxExpense").value(500.0))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].maxExpense").value(300.0));
//    }
//
//    @Test
//    public void testGetMaxExpensesForLastMonth_InvalidStatus() throws Exception {
//        String encodedUsername = "encodedUser123";
//
//        when(decodename.decodeUsername(encodedUsername)).thenReturn("user123");
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/transactions/maxExpenses/lastMonth/{username}", encodedUsername)
//                        .param("status", "invalidStatus")
//                        .param("page", "0")
//                        .param("size", "100"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // Ensure BadRequest for invalid status
//                .andExpect(MockMvcResultMatchers.jsonPath("$.error[0].error").value("Invalid status value. Valid values are 'enabled', 'disabled', or 'both'"));
//    }
//
//    @Test
//    public void testGetHighValueExpenses_Success() throws Exception {
//        String encodedUsername = "encodedUser123";
//        String username = "user123";
//
//        when(decodename.decodeUsername(encodedUsername)).thenReturn(username);
//
//        // Mocking an empty list of high-value expenses, but ensure it's returned as a Page
//        List<Map<String, String>> highValueExpenses = List.of(); // Empty list
//        Map<String, Page<Map<String, String>>> highValueExpensesPage = Map.of(
//                "expenses", new PageImpl<>(highValueExpenses) // Page with no content
//        );
//
//        when(transactionService.getHighValueExpensesForUser(username, 2, "both", 500.0, Pageable.unpaged()))
//                .thenReturn(highValueExpensesPage);
//
//        // Ensure we get a 200 OK response with an empty list
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/transactions/highvalue/expenses/{username}", encodedUsername)
//                        .param("limit", "2")
//                        .param("amountThreshold", "500.0")
//                        .param("status", "both")
//                        .param("page", "0")
//                        .param("size", "100"))
//                .andExpect(MockMvcResultMatchers.status().isOk())  // Expect 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.expenses").isEmpty());  // Empty expenses list
//    }
//
//    @Test
//    public void testGetHighValueExpenses_InvalidAmountThreshold() throws Exception {
//        String encodedUsername = "encodedUser123";
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/transactions/highvalue/expenses/{username}", encodedUsername)
//                        .param("amountThreshold", "-1000.0")
//                        .param("status", "both")
//                        .param("limit", "1")
//                        .param("page", "0")
//                        .param("size", "100"))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // Ensure BadRequest for invalid threshold
//                .andExpect(MockMvcResultMatchers.jsonPath("$.error[0].error").value("Amount threshold must be a positive value"));
//    }
//
//    @Test
//    public void testGetLastXTransactionsForUser_Success() throws Exception {
//        String encodedUsername = "encodedUser123";
//        String username = "user123";
//
//        when(decodename.decodeUsername(encodedUsername)).thenReturn(username);
//
//        List<TransactionDetail> lastTransactions = List.of(
//                new TransactionDetail(1L, "2025-01-01", "10:00", "cr", 100.0, "Purchase"),
//                new TransactionDetail(2L, "2025-01-02", "11:00", "db", 200.0, "Purchase")
//        );
//
//        Map<Integer, Page<TransactionDetail>> lastXTransactionsPage = Map.of(
//                1, new PageImpl<>(lastTransactions)
//        );
//
//        when(transactionService.getLastXTransactionsForUser(username, 2, "both", Pageable.unpaged()))
//                .thenReturn(lastXTransactionsPage);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/customer/transactions/lastXTransactions/{username}", encodedUsername)
//                        .param("limit", "2")
//                        .param("status", "both")
//                        .param("page", "0")
//                        .param("size", "100"))
//                .andExpect(MockMvcResultMatchers.status().isOk())  // Ensure 200 OK
//                .andExpect(MockMvcResultMatchers.jsonPath("$.1.content[0].transactionId").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.1.content[1].transactionId").value(2));
//    }
//}
