package com.example.receiptprocessor.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final Map<String, Integer> receiptStorage = new ConcurrentHashMap<>();
    private static final Pattern ALPHA_NUMERIC = Pattern.compile("[a-zA-Z0-9]");

    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {
        int points = calculatePoints(receipt);
        String receiptId = UUID.randomUUID().toString();
        receiptStorage.put(receiptId, points);
        return Map.of("id", receiptId);
    }

    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {
        return receiptStorage.containsKey(id) ? Map.of("points", receiptStorage.get(id)) : Map.of("error", 0);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;
        points += (int) receipt.getRetailer().chars().filter(ch -> ALPHA_NUMERIC.matcher(String.valueOf((char) ch)).matches()).count();
        double total = Double.parseDouble(receipt.getTotal());
        if (total == (int) total) points += 50;
        if (total % 0.25 == 0) points += 25;
        for (Item item : receipt.getItems()) {
            if (item.getShortDescription().trim().length() % 2 == 0) {
                points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 == 1) points += 6;
        int hour = Integer.parseInt(receipt.getPurchaseTime().split(":")[0]);
        if (hour >= 14 && hour < 16) points += 10;
        return points;
    }
}

class Receipt {
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private String total;
    private List<Item> items;

    public String getRetailer() { return retailer; }
    public String getPurchaseDate() { return purchaseDate; }
    public String getPurchaseTime() { return purchaseTime; }
    public String getTotal() { return total; }
    public List<Item> getItems() { return items; }
}

class Item {
    private String shortDescription;
    private String price;

    public String getShortDescription() { return shortDescription; }
    public String getPrice() { return price; }
}
