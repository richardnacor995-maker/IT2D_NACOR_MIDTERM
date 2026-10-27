
package ui;

import dao.ScenarioDao;
import model.Asset;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class AssetUI {

    private final Scanner scanner;
    private final ScenarioDao assetDao;

    public AssetUI(Scanner scanner, ScenarioDao assetDao) {
        this.scanner = scanner;
        this.assetDao = assetDao;
    }

    public void showMenu() {

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("==============================================");
            System.out.println("          IT ASSET EQUIPMENT TRACKER");
            System.out.println("==============================================");
            System.out.println("[1] Add New Asset");
            System.out.println("[2] View Issued Assets");
            System.out.println("[3] Exit System");
            System.out.println("==============================================");

            int choice = readInteger("Enter choice: ");

            switch (choice) {
                case 1:
                    addAsset();
                    break;

                case 2:
                    viewAssets();
                    break;

                case 3:
                    System.out.println("Exiting system... Thank you!");
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid choice! Enter 1, 2, or 3."
                    );
            }
        }
    }

    private void addAsset() {

        System.out.println();
        System.out.println("========== ADD NEW IT ASSET ==========");

        String deviceName =
                readRequiredText("Enter Device Name: ");

        String assignedTo =
                readRequiredText("Enter Assigned Employee Name: ");

        String serialNo =
                readRequiredText("Enter Serial Number: ");

        String condition = readCondition();

        Asset asset = new Asset(
                deviceName,
                assignedTo,
                serialNo,
                condition
        );

        try {
            boolean inserted = assetDao.insertAsset(asset);

            if (inserted) {
                System.out.println();
                System.out.println(
                        "SUCCESS: IT asset logged into system!"
                );
            } else {
                System.out.println("Failed to add asset.");
            }

        } catch (SQLException e) {

            String message = e.getMessage() == null
                    ? "" : e.getMessage().toLowerCase();

            if (message.contains("unique")) {
                System.out.println(
                        "ERROR: This serial number already exists."
                );
            } else {
                System.out.println(
                        "Database error: " + e.getMessage()
                );
            }
        }
    }

    private void viewAssets() {

        System.out.println();
        System.out.println("====================== IT EQUIPMENT ASSETS ======================");

        try {
            List<Asset> assets = assetDao.getAllAssets();

            if (assets.isEmpty()) {
                System.out.println("No assets found.");
                return;
            }

            printBorder();

            System.out.printf(
                    "| %-4s | %-20s | %-20s | %-14s | %-15s |%n",
                    "ID",
                    "DEVICE NAME",
                    "ASSIGNED TO",
                    "SERIAL NO",
                    "CONDITION"
            );

            printBorder();

            for (Asset asset : assets) {

                System.out.printf(
                        "| %-4d | %-20s | %-20s | %-14s | %-15s |%n",
                        asset.getId(),
                        shorten(asset.getDeviceName(), 20),
                        shorten(asset.getAssignedTo(), 20),
                        shorten(asset.getSerialNo(), 14),
                        asset.getCondition()
                );
            }

            printBorder();

            System.out.println(
                    "Total Records Displayed: " + assets.size()
            );

        } catch (SQLException e) {
            System.out.println(
                    "Unable to retrieve assets: " + e.getMessage()
            );
        }
    }

    private void printBorder() {
        System.out.println(
                "+------+----------------------+----------------------+----------------+-----------------+"
        );
    }

    private String readRequiredText(String prompt) {

        while (true) {
            System.out.print(prompt);

            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println(
                    "Input cannot be empty. Please try again."
            );
        }
    }

    private String readCondition() {

        while (true) {
            System.out.print(
                    "Enter Condition (NEW / GOOD / REPAIR_NEEDED): "
            );

            String condition = scanner.nextLine()
                    .trim().toUpperCase();

            if (condition.equals("NEW")
                    || condition.equals("GOOD")
                    || condition.equals("REPAIR_NEEDED")) {

                return condition;
            }

            System.out.println(
                    "Invalid condition! Use NEW, GOOD, or REPAIR_NEEDED."
            );
        }
    }

    private int readInteger(String prompt) {

        while (true) {
            System.out.print(prompt);

            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input! Please enter a whole number."
                );
            }
        }
    }

    private String shorten(String value, int maxLength) {

        if (value.length() <= maxLength) {
            return value;
        }

        return value.substring(0, maxLength - 3) + "...";
    }
}
