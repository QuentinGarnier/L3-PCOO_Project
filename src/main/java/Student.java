/**
 * @author Quentin Garnier
 */

public class Student {
    private int id;
    private String firstname;
    private String name;

    public Student(int i, String fn, String n) {
        this.id = i;
        this.firstname = fn;
        this.name = n;
    }

    public int getId() {
        return this.id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getName() {
        return this.name;
    }
}
