package com.keyin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MetrobusClient {
    private static final String API_URL = "http://localhost:8080/api/mcard";
    private static final Scanner INPUT_SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            processUserChoice(choice);
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Metrobus Client ===");
        System.out.println("1. Issue New Card");
        System.out.println("2. View All Cards");
        System.out.println("3. Recharge Card");
        System.out.println("4. Pay for Ride");
        System.out.println("5. Check Card Balance");
        System.out.println("6. View Card History");
        System.out.println("7. Exit");
        System.out.print("Select an option: ");
    }

    private static int getUserChoice() {
        int option = INPUT_SCANNER.nextInt();
        INPUT_SCANNER.nextLine();
        return option;
    }

    private static void processUserChoice(int option) throws Exception {
        switch (option) {
            case 1 -> issueNewCard();
            case 2 -> fetchAllCards();
            case 3 -> rechargeCard();
            case 4 -> processRidePayment();
            case 5 -> checkCardBalance();
            case 6 -> fetchCardHistory();
            case 7 -> exitClient();
            default -> System.out.println("Invalid selection. Try again.");
        }
    }

    private static void issueNewCard() throws Exception {
        System.out.print("Enter card number: ");
        String cardNum = INPUT_SCANNER.nextLine();
        String requestUrl = API_URL + "/issue?cardNumber=" + cardNum;
        sendPostRequest(requestUrl, "New Card Issued");
    }

    private static void fetchAllCards() throws Exception {
        String requestUrl = API_URL + "/all";
        sendGetRequest(requestUrl, "List of Issued Cards");
    }

    private static void rechargeCard() throws Exception {
        System.out.print("Enter card number: ");
        String cardNum = INPUT_SCANNER.nextLine();
        System.out.print("Enter amount: ");
        double amount = INPUT_SCANNER.nextDouble();
        INPUT_SCANNER.nextLine();
        String requestUrl = API_URL + "/topup/" + cardNum + "?amount=" + amount;
        sendPostRequest(requestUrl, "Card Recharged Successfully");
    }

    private static void processRidePayment() throws Exception {
        System.out.print("Enter card number: ");
        String cardNum = INPUT_SCANNER.nextLine();
        System.out.print("Enter fare amount: ");
        double fare = INPUT_SCANNER.nextDouble();
        INPUT_SCANNER.nextLine();
        String requestUrl = API_URL + "/pay/" + cardNum + "?fare=" + fare;
        sendPostRequest(requestUrl, "Ride Payment Successful");
    }

    private static void checkCardBalance() throws Exception {
        System.out.print("Enter card number: ");
        String cardNum = INPUT_SCANNER.nextLine();
        String requestUrl = API_URL + "/balance/" + cardNum;
        sendGetRequest(requestUrl, "Current Balance");
    }

    private static void fetchCardHistory() throws Exception {
        System.out.print("Enter card number: ");
        String cardNum = INPUT_SCANNER.nextLine();
        String requestUrl = API_URL + "/history/" + cardNum;
        sendGetRequest(requestUrl, "Transaction History");
    }

    private static void exitClient() {
        System.out.println("Exiting Metrobus Client...");
        System.exit(0);
    }

    private static void sendPostRequest(String url, String successMessage) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            printResponse(connection, successMessage);
        } else {
            System.out.println("Error: " + responseCode);
        }
    }

    private static void sendGetRequest(String url, String title) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            printResponse(connection, title);
        } else {
            System.out.println("Error: " + responseCode);
        }
    }

    private static void printResponse(HttpURLConnection connection, String title) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        System.out.println("\n--- " + title + " ---\n" + response.toString() + "\n--------------------");
    }
}