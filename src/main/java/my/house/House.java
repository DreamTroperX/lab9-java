package my.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class House {
    private int id;
    private int num;
    private double area;
    private int floor;
    private int numberOfRooms;
    private String street;

}
