package customer;

public abstract class Customer {
    private String customerId;
    private String name;
    private int age;
    private String contact;
    private String address;

    static int customerCounter;
    public Customer(){}

    public Customer(String name, int age, String contact, String address) {
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.address = address;
    }
    abstract void displayCustomerDetails();
    abstract String getCustomerType();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
