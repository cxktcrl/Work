package houserent.domain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HouseService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/real_estate";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public House findByid(int findId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            // 建立连接
            conn = getConnection();

            // 创建PreparedStatement对象
            stmt = conn.prepareStatement("select * from house where id = ?");

            // 设置参数值
            stmt.setInt(1, findId);

            // 执行查询操作
            rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int rent = rs.getInt("rent");
                String state = rs.getString("state");

                return new House(id, name, phone, address, rent, state);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, rs);
        }

        return null;
    }

    public boolean del(int delId) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 建立连接
            conn = getConnection();

            // 创建PreparedStatement对象
            stmt = conn.prepareStatement("delete from house where id = ?");

            // 设置参数值
            stmt.setInt(1, delId);

            // 执行删除操作
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, null);
        }

        return false;
    }

    public boolean add(House newHouse) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 建立连接
            conn = getConnection();

            // 创建PreparedStatement对象
            stmt = conn.prepareStatement("insert into house(name, phone, address, rent, state) values( ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            // 设置参数值
            stmt.setString(1, newHouse.getName());
            stmt.setString(2, newHouse.getPhone());
            stmt.setString(3, newHouse.getAddress());
            stmt.setInt(4, newHouse.getRent());
            stmt.setString(5, newHouse.getState());

            // 执行插入操作
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                // 获取生成的自增长主键值
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    newHouse.setId(id);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, rs);
        }

        return false;
    }

    public boolean update(House house) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // 建立连接
            conn = getConnection();

            // 创建PreparedStatement对象
            stmt = conn.prepareStatement("update house set name = ?, phone = ?, address = ?, rent = ?, state = ? where id = ?");

            // 设置参数值
            stmt.setString(1, house.getName());
            stmt.setString(2, house.getPhone());
            stmt.setString(3, house.getAddress());
            stmt.setInt(4, house.getRent());
            stmt.setString(5, house.getState());
            stmt.setInt(6, house.getId());

            // 执行更新操作
            int affectedRows = stmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, null);
        }

        return false;
    }

    public House[] list() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // 建立连接
            conn = getConnection();

            // 创建PreparedStatement对象
            stmt = conn.prepareStatement("select * from house");

            // 执行查询操作
            rs = stmt.executeQuery();

            List<House> houseList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int rent = rs.getInt("rent");
                String state = rs.getString("state");

                House house = new House(id, name, phone, address, rent, state);
                houseList.add(house);
            }

            return houseList.toArray(new House[0]);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn, stmt, rs);
        }

        return new House[0];
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}