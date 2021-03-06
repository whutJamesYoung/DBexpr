package handle;

import entity.*;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class ParseEntity {
    public Vector ParseDriver() {
        Driver[] data = new Driver[0];
        try {
            data = DataProcessing.searchDriver(Search_SQL_sen.get_all_driver());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Vector vector = new Vector();
        if (data == null)
            data = new Driver[0];
        for (Driver driver : data) { // 生成一行
            Vector temp = new Vector();
            temp.add(driver.getId());
            temp.add(driver.getName());
            temp.add(ParseDate2S(driver.getEnroll_date()));
            vector.add(temp);
        }
        return vector;
    }

    public Vector ParseCar() {
        Car[] data = new Car[0];
        try {
            data = DataProcessing.searchCar(Search_SQL_sen.get_all_car());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Vector vector = new Vector();
        if (data == null)
            data = new Car[0];
        for (Car car : data) {
            Vector temp = new Vector();
            temp.add(car.getId());
            temp.add(car.getType());
            temp.add(car.getRent_rate());
            temp.add(ParseDate2S(car.getPurchase_date()));
            temp.add(ParseDate2S(car.getMaintain_date()));
            temp.add(car.getMile());
            temp.add(car.getWorking_time());
            vector.add(temp);
        }
        return vector;
    }

    public Vector ParseClient() {
        Client[] data = new Client[0];
        try {
            data = DataProcessing.searchClient(Search_SQL_sen.get_all_client());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Vector vector = new Vector();
        if (data == null)
            data = new Client[0];
        for (Client client : data) {
            Vector temp = new Vector();
            temp.add(client.getId());
            temp.add(client.getName());
            temp.add(client.getCompany());
            temp.add(client.getTel());
            temp.add(client.getAddr());
            temp.add(client.getZipcode());
            vector.add(temp);
        }
        return vector;
    }

    public Vector ParseTransaction(int client_id) {
        Transaction[] data = new Transaction[0];
        try {
            data = DataProcessing.searchTransaction(Search_SQL_sen.get_all_TR_by_client(client_id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Vector vector = new Vector();
        if (data == null)
            data = new Transaction[0];
        for (Transaction transaction : data) {
            Vector temp = new Vector();
            temp.add(transaction.getId());
            temp.add(ParseDate2S(transaction.getDate()));
            temp.add(transaction.getCar_id());
            temp.add(transaction.getLocal());
            temp.add(transaction.getMiles());
            temp.add(transaction.getTimes());
            temp.add(transaction.getClient_id());
            temp.add(transaction.getDriver_id());
            vector.add(temp);
        }
        return vector;
    }

    public Vector ParseTransaction() {
        Transaction[] data = new Transaction[0];
        try {
            data = DataProcessing.searchTransaction(Search_SQL_sen.get_available_TR());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Vector vector = new Vector();
        if (data == null)
            data = new Transaction[0];
        for (Transaction transaction : data) {
            Vector temp = new Vector();
            temp.add(transaction.getId());
            temp.add(ParseDate2S(transaction.getDate()));
            temp.add(transaction.getCar_id());
            temp.add(transaction.getLocal());
            temp.add(transaction.getMiles());
            temp.add(transaction.getTimes());
            temp.add(transaction.getClient_id());
            vector.add(temp);
        }
        return vector;
    }

    public static Date ParseDate2D(java.util.Date date) { // date转换为Java date
        return new Date(date.getTime());
    }

    public static java.util.Date ParseDate2D(Date date) {
        return new java.util.Date(date.getTime());
    }

    public static String ParseDate2S(java.util.Date date) {
        if (date == null)
            return "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
