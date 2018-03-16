import java.sql.Timestamp;

public class Test {
    public static void main(String[] args) {
        long l = 1520524800000l;
        Timestamp timestamp = new Timestamp(l);
        String s = timestamp.toString();
        System.out.println(s);
        System.out.println(s.indexOf(" "));
        System.out.println(s.substring(0,10));
    }
}
