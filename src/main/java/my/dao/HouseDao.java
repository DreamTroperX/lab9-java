package my.dao;

import lombok.AllArgsConstructor;
import my.house.House;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class HouseDao {
    private Connection connection;

    public List<House> findAll() {
        ArrayList<House> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("select * from house");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()){
                int id = rs.getInt("id");
                int num = rs.getInt("num");
                double area = rs.getDouble("area");
                int floor = rs.getInt("floor");
                int numberOfRooms = rs.getInt("numberOfRooms");
                String street = rs.getString("street");
                list.add(new House (id,num,area,floor,numberOfRooms,street));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void add(House house){
         try(PreparedStatement ps = connection.prepareStatement("insert into house (num,area,floor,numberOfRooms,street) VALUES (?,?,?,?,?)")){
             ps.setInt(1, house.getNum());
             ps.setDouble(2, house.getArea());
             ps.setInt(3, house.getFloor());
             ps.setInt(4, house.getNumberOfRooms());
             ps.setString(5, house.getStreet());
             ps.executeUpdate();
    } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<House> findByNumberOfRooms(int n, List<House> houseList) {
        List<House> result = new ArrayList<>();
        for (House house : houseList) {
            if (house.getNumberOfRooms() == n) {
                result.add(house);
            }
        }
        return result;
    }

    public static List<House> findByNumberOfRoomsAndFloors(int n, int fromFloor, int toFloor, List<House> houseList) {
        List<House> result = new ArrayList<>();
        for (House house : houseList) {
            if (house.getNumberOfRooms() == n && house.getFloor() >= fromFloor && house.getFloor() <= toFloor) {
                result.add(house);
            }
        }
        return result;
    }

    public static List<House> findByAreaAndSortByAreaAndFloor(List<House> houseList, double areaC) {
        return houseList.stream()
                .filter(house -> house.getArea() > areaC)
                .sorted(Comparator.comparing(House::getArea).reversed().thenComparing(House::getFloor))
                .collect(Collectors.toList());
    }

    public static List<House> sortByArea(List<House> houseList) {
        return houseList.stream()
                .sorted(Comparator.comparing(House::getArea))
                .collect(Collectors.toList());
    }

    public List<House> sortByFloor(List<House> list) {
        Map<Integer, List<House>> floorToHouses = list.stream()
                .collect(Collectors.groupingBy(House::getFloor));

        List<House> sortedHouses = floorToHouses.entrySet().stream()
                .sorted(Map.Entry.<Integer, List<House>>comparingByKey().reversed())
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.toList());

        return sortedHouses;
    }

    public List<List<House>> apartmentListOfFloor(List<House> list) {
        return list.stream()
                .collect(Collectors.groupingBy(House::getFloor))
                .values()
                .stream()
                .collect(Collectors.toList());
    }


    /*public void deleteById(int id) {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM house WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            shiftIds(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void shiftIds(int deletedId) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT id FROM house WHERE id > ? ORDER BY id ASC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int currentId = rs.getInt("id");
                int newId = currentId - 1;
                try (PreparedStatement updatePs = connection.prepareStatement("UPDATE house SET id = ? WHERE id = ?")) {
                    updatePs.setInt(1, newId);
                    updatePs.setInt(2, currentId);
                    updatePs.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/






}
