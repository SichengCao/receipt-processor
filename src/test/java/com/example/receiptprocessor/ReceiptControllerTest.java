package com.example.receiptprocessor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testProcessReceipt() throws Exception {
        String receiptJson = """
        {
            "retailer": "Walmart",
            "purchaseDate": "2022-07-01",
            "purchaseTime": "14:30",
            "total": "35.00",
            "items": [
                { "shortDescription": "Milk", "price": "2.50" },
                { "shortDescription": "Bread", "price": "3.00" }
            ]
        }
        """;

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void testGetPoints() throws Exception {
        mockMvc.perform(get("/receipts/nonexistent-id/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(0));
    }

    @Test
    void testGetPointsForValidReceipt() throws Exception {
        String receiptJson = """
    {
        "retailer": "Target",
        "purchaseDate": "2022-07-01",
        "purchaseTime": "14:30",
        "total": "50.00",
        "items": [
            { "shortDescription": "Soda", "price": "5.00" }
        ]
    }
    """;


        String response = mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(receiptJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        String receiptId = response.replaceAll("\\{\"id\":\"(.*?)\"\\}", "$1");


        mockMvc.perform(get("/receipts/" + receiptId + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").isNumber());
    }

    @Test
    void testGetPointsForInvalidReceipt() throws Exception {
        mockMvc.perform(get("/receipts/nonexistent-id/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(0));
    }


}
