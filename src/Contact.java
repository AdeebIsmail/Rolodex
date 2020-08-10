public class Contact implements Comparable {
    String firstName;
    String lastName;
    String phoneNumber;
    String address;

    public Contact(String firstName, String lastName, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString() {
        String personContact;
        personContact = getFirstName() + "  " + getLastName() + "  " + getPhoneNumber() + "  " + getAddress();
        return personContact;
    }

    @Override
    public int compareTo(Object o) {
        Contact c = (Contact) o;
        if (c.getFirstName().equals(firstName) && c.getLastName().equals(lastName) && c.getAddress().equals(address) && c.getPhoneNumber().equals(phoneNumber)) {
            return 1;
        } else {
            return 0;
        }
    }


}
