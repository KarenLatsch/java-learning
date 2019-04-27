package academy.learnprogramming;

public class Contact {
    private String name;
    private String phoneNumber;

    public Contact(String name, String phoneNumber) {
        String capitalizedName = toFirstCharUpperAll(name);
        this.name = capitalizedName;

        StringBuilder stringPhoneNumber = new StringBuilder(
                String.valueOf(phoneNumber));
        stringPhoneNumber.insert(3, '-');
        stringPhoneNumber.insert(7, '-');
        this.phoneNumber = String.valueOf(stringPhoneNumber);
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static Contact createContact(String name, String phoneNumber) {
        return new Contact(name, phoneNumber);
    }

    public String toFirstCharUpperAll(String string) {
        StringBuffer sb = new StringBuffer(string);
        for (int i = 0; i < sb.length(); i++)
            if (i == 0 || sb.charAt(i - 1) == ' ')//first letter to uppercase by default
                sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
        return sb.toString();
    }
}
