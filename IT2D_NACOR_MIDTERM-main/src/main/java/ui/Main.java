
package ui;

import dao.DatabaseConnection;
import dao.ScenarioDao;

import java.sql.SQLException;
import java.util.Scanner;

public class 1Main {

    public static void main(String[] args) {

        try {
            DatabaseConnection.initializeDatabase();

            try (Scanner scanner = new Scanner(System.in)) {

                ScenarioDao assetDao = new ScenarioDao();

                AssetUI assetUI = new AssetUI(
                        scanner,
                        assetDao
                );

                assetUI.showMenu();
            }

        } catch (SQLException e) {
            System.out.println(
                    "Unable to start the application: "
                            + e.getMessage()
            );
        }
    }
}
