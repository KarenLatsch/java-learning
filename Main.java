package academy.learnprogramming;

import java.awt.*;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static MobilePhone mobilePhone = new MobilePhone("618 456-7890");


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public static void main(String[] args) {
        boolean quit = false;
        startPhone();
        printActions();
        int action = 99;

        while (!quit) {
            if (scanner.hasNextInt()) {
                action = scanner.nextInt();
            } else {
                action = 99;
            }
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.print("\nShutting down... good bye!");
                    quit = true;
                    return;
                case 1:
                    mobilePhone.printContacts();
                    break;
                case 2:
                    addNewContact();
                    break;
                case 3:
                    updateContact();
                    break;
                case 4:
                    removeContact();
                    break;
                case 5:
                    queryContact();
                    break;
                case 6:
                    printActions();
                    break;
                default:
                    System.out.println(ANSI_BLUE + "Please select a valid option from the menu." + ANSI_RESET);
                    break;
            }
            if (action != 6) {
                System.out.println(ANSI_PURPLE + "\nEnter action: (6 to show available actions)" + ANSI_RESET);
            }
        }
    }

    private static void addNewContact() {
        boolean quit = false;

        while (!quit) {

            String contactName = EnterContactName("new");
            if (contactName == null) {
                quit = true;
                return;
            }

            String contactPhoneNumber = EnterContactPhoneNumber("new");
            if (contactPhoneNumber == null) {
                quit = true;
                return;
            }

            if (contactName != null && contactPhoneNumber != null) {
                Contact newContact = Contact.createContact(contactName, contactPhoneNumber);
                System.out.println();
                if (mobilePhone.addNewContact(newContact)) {
                    System.out.println("New contact added: " + newContact.getName() +
                            "  " + newContact.getPhoneNumber());
                } else {
                    System.out.println(ANSI_RED + "Cannot add, " + contactName + " already on file" + ANSI_RESET);
                }
            }
        }
    }

    private static void updateContact() {

        System.out.println();
        System.out.println(ANSI_BLUE + "Update Existing Contact (Q=Quit)" + ANSI_RESET);

        String contactName = EnterContactName("existing");
        if (contactName == null) {
            System.out.println(ANSI_RED + "Contact not updated." + ANSI_RESET);
            return;
        }


        Contact existingContactRecord = mobilePhone.queryContact(contactName);
        if (existingContactRecord == null) {
            System.out.println();
            System.out.println(ANSI_RED + "Contact not found." + ANSI_RESET);
            return;
        }
        System.out.println();
        System.out.println(ANSI_BLUE + "Retype same name if no change." + ANSI_RESET);
        String contactNewName = EnterContactName("changes for", contactName);
        if (contactNewName == null) {
            System.out.println(ANSI_RED + "Contact not updated." + ANSI_RESET);
            return;
        }
        System.out.println();
        System.out.println(ANSI_BLUE + "Retype same phone number if no change." + ANSI_RESET);
        String contactNewPhoneNumber = EnterContactPhoneNumber("changes for");
        if (contactNewPhoneNumber == null) {
            System.out.println(ANSI_RED + "Contact not updated." + ANSI_RESET);
            return;
        }

        Contact newContact = Contact.createContact(contactNewName, contactNewPhoneNumber);
        System.out.println();

        if (mobilePhone.updateContact(existingContactRecord, newContact)) {
            System.out.println("Contact updated sucessfully.");
            System.out.println(newContact + " Replaced " + existingContactRecord);
        } else {
            System.out.println(ANSI_RED + "Contact not updated." + ANSI_RESET);
        }
    }

    private static void removeContact() {

        System.out.println();
        System.out.println(ANSI_BLUE + "Remove Existing Contacts (Q=Quit)" + ANSI_RESET);
        String contactName = EnterContactName("existing");
        if (contactName == null) {
            System.out.println(ANSI_RED + "Contact not removed." + ANSI_RESET);
            return;
        }

        Contact existingContactRecord = mobilePhone.queryContact(contactName);
        if (existingContactRecord == null) {
            System.out.println();
            System.out.println(ANSI_RED + "Contact not found." + ANSI_RESET);
            return;
        }

        System.out.println();
        if (mobilePhone.removeContact(existingContactRecord)) {
            System.out.println(existingContactRecord + "Contact deleted.");
        } else {
            System.out.println(ANSI_RED + "Error deleting contact." + ANSI_RESET);
        }
    }

    private static void queryContact() {
        System.out.println();
        System.out.println(ANSI_BLUE + "Query Existing Contacts (Q=Quit)" + ANSI_RESET);
        String contactName = EnterContactName("existing");
        if (contactName != null) {
            mobilePhone.queryContact(contactName, "All");
        }
    }

    private static String EnterContactName(String type) {
        boolean redisplay = true;
        String contactName = "";


        if (type.equalsIgnoreCase("new")) {
            System.out.println();
            System.out.println(ANSI_BLUE + "Add New Contacts (Q=Quit)" + ANSI_RESET);
        }

        while (redisplay) {
            System.out.println("Enter " + type + " contact name:");
            contactName = scanner.nextLine();

            if (contactName.equalsIgnoreCase("Q")) {
                redisplay = false;
                return contactName = null;
            }

            if (type.equalsIgnoreCase("new")) {
                Contact existingContactRecord = mobilePhone.queryContact(contactName);
                if (existingContactRecord != null) {
                    System.out.println();
                    System.out.println(ANSI_RED + "Contact already exist." + ANSI_RESET);

                } else {
                    if (validateName(contactName)) {
                        redisplay = false;
                        return contactName.trim();
                    }
                }
            }

            if (type.equalsIgnoreCase("existing")) {
                if (validateName(contactName)) {
                    redisplay = false;
                    return contactName.trim();
                }
            }
        }
        return contactName = null;
    }

    private static String EnterContactName(String type, String existingName) {
        boolean redisplay = true;
        String contactName = "";

        System.out.println();

        while (redisplay) {
            System.out.println("Enter " + type + " contact name: " + existingName);
            contactName = scanner.nextLine();

            if (contactName.equalsIgnoreCase("Q")) {
                redisplay = false;
                return contactName = null;
            }

            if (!existingName.equalsIgnoreCase(contactName)) {
                Contact existingContactRecord = mobilePhone.queryContact(contactName);
                if (existingContactRecord != null) {
                    System.out.println();
                    System.out.println(ANSI_RED + "Contact already exist." + ANSI_RESET);

                } else {
                    if (validateName(contactName)) {
                        redisplay = false;
                        return contactName.trim();
                    }
                }
            } else {
                redisplay = false;
                return contactName.trim();
            }
        }
        return contactName = null;
    }

    private static boolean validateName(String name) {
        if (name.isBlank()) {
            System.out.println();
            System.out.println(ANSI_RED + "Name must be entered.(Q = Quit)" + ANSI_RESET);
            return false;
        }

        boolean hasCharacters = name.matches("^[a-zA-Z\\s]*$");
        if (!hasCharacters) {
            System.out.println();
            System.out.println(ANSI_RED + "Name must be entered.(Q = Quit)" + ANSI_RESET);
            return false;
        }

        return true;
    }

    private static String EnterContactPhoneNumber(String type) {
        boolean redisplay = true;
        String contactPhoneNumber = "";

        System.out.println();
        System.out.println("Enter " + type + " contact phone number (10 digits):");

        while (redisplay) {
            contactPhoneNumber = scanner.nextLine();

            if (contactPhoneNumber.equalsIgnoreCase("Q")) {
                redisplay = false;
                return contactPhoneNumber = null;
            }

            contactPhoneNumber = contactPhoneNumber.replaceAll("\\s", "")
                    .replaceAll("[-()]", "");

            if (validatePhoneNumber(contactPhoneNumber)) {
                redisplay = false;
                return contactPhoneNumber;
            }
        }
        return contactPhoneNumber = null;
    }

    private static boolean validatePhoneNumber(String inputPhoneNumber) {

        if (inputPhoneNumber.matches("^\\d+$") && inputPhoneNumber.length() == 10) {
            return true;
        }
        System.out.println();
        System.out.println(ANSI_RED + "Phone number must be a 10 digit number. (Q = Quit)" + ANSI_RESET);
        return false;
    }


    private static void startPhone() {
        System.out.println();
        System.out.println("Starting phone .... Welcome!");
    }

    private static void printActions() {
        System.out.println(ANSI_PURPLE + "\nAvailable actions:\nPress" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "0 - to shutdown\n" +
                "1 - to print contacts\n" +
                "2 - to add new contacts\n" +
                "3 - to update an existing contact\n" +
                "4 - to remove an existing contact\n" +
                "5 - to query existing contacts\n" +
                "6 - to print list of available actions." + ANSI_RESET);
        System.out.println(ANSI_PURPLE + "choose your action: " + ANSI_RESET
        );
    }
}
