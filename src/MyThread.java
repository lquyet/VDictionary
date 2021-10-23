import java.sql.SQLException;

public class MyThread extends Thread{
    @Override
    public void run() {
        try {
            Dictionary.importData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
