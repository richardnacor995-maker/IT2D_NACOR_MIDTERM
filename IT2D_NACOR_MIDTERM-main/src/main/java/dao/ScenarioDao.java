
package dao;

import model.Asset;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScenarioDao {

    public boolean insertAsset(Asset asset) throws SQLException {

        String sql = "INSERT INTO assets " +
                "(device_name, assigned_to, serial_no, condition) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, asset.getDeviceName());
            statement.setString(2, asset.getAssignedTo());
            statement.setString(3, asset.getSerialNo());
            statement.setString(4, asset.getCondition());

            return statement.executeUpdate() == 1;
        }
    }

    public List<Asset> getAllAssets() throws SQLException {

        List<Asset> assets = new ArrayList<>();

        String sql = "SELECT id, device_name, assigned_to, " +
                "serial_no, condition FROM assets ORDER BY id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Asset asset = new Asset(
                        resultSet.getInt("id"),
                        resultSet.getString("device_name"),
                        resultSet.getString("assigned_to"),
                        resultSet.getString("serial_no"),
                        resultSet.getString("condition")
                );

                assets.add(asset);
            }
        }

        return assets;
    }
}
