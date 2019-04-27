package academy.learnprogramming;

import java.util.ArrayList;
import java.util.Collections;

public class MobilePhone {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private String myNumber;
    ArrayList<Contact> myContacts;
    ArrayList<String> myContactsSorted;

    public MobilePhone(String myNumber) {
        this.myNumber = myNumber;
        this.myContacts = new ArrayList<Contact>();
        this.myContactsSorted = new ArrayList<String>();
    }

    public boolean addNewContact(Contact contact) {

        if (findContact(contact.getName()) >= 0) {
            System.out.println();
            System.out.println(ANSI_RED + "Contact is already on file." + ANSI_RESET);
            return false;
        }
        myContacts.add(contact);
        return true;
    }

    public boolean updateContact(Contact oldContact, Contact newContact) {

        int foundPosition = findContact(oldContact);
        if (foundPosition < 0) {
            System.out.println();
            System.out.println(ANSI_RED + oldContact.getName() + ", was not found." + ANSI_RESET);
            return false;
        }
        this.myContacts.set(foundPosition, newContact);
        return true;
    }

    public boolean removeContact(Contact contact) {

        int foundPosition = findContact(contact);
        if (foundPosition < 0) {
            System.out.println();
            System.out.println(ANSI_RED + contact.getName() + ", was not found." + ANSI_RESET);
            return false;
        }
        this.myContacts.remove(foundPosition);
        System.out.println();
        System.out.println(contact.getName() + ", was deleted.");
        return true;
    }

    private int findContact(Contact contact) {
        return this.myContacts.indexOf(contact);
    }

    private int findContact(String contactName) {

        for (int i = 0; i < myContacts.size(); i++) {
            Contact contact = this.myContacts.get(i);
            if (contact.getName().equalsIgnoreCase(contactName)) {
                return i;
            }
        }
        return -1;
    }

    public Contact queryContact(String name) {

        int position = findContact(name);
        if (position >= 0) {
            return this.myContacts.get(position);
        }
        return null;
    }

    public void queryContact(String name, String allMatches) {

        copyQueryContacts(name);
        printSortedContacts(name, "");
    }

    public void printContacts() {

        copyAllContacts();
        printSortedContacts(null, "All");
    }

    private void copyAllContacts() {

        String myContactsString = "";
        myContactsSorted.clear();
        int j = 0;

        if (myContacts.size() > 0) {
            for (int i = 0; i < this.myContacts.size(); i++) {
                myContactsString = (myContacts.get(i).getName() + "  "
                        + myContacts.get(i).getPhoneNumber());
                myContactsSorted.add(j, myContactsString);
                j++;
            }
        }
    }

    private void copyQueryContacts(String name) {
        String myContactsString = "";
        String lowerCaseName = "";
        String lowerCaseNameContact = "";
        myContactsSorted.clear();
        int j = 0;

        if (myContacts.size() > 0) {
            for (int i = 0; i < this.myContacts.size(); i++) {
                lowerCaseName = name;
                lowerCaseNameContact = myContacts.get(i).getName().toLowerCase();
                if (lowerCaseNameContact.contains(lowerCaseName)) {
                    myContactsString = (myContacts.get(i).getName() + "  "
                            + myContacts.get(i).getPhoneNumber());
                    myContactsSorted.add(j, myContactsString);
                    j++;
                }
            }
        }
    }

    private void printSortedContacts(String name, String all) {

        if (myContactsSorted.size() > 0) {

            System.out.println();
            if (all.equalsIgnoreCase("All")) {
                System.out.println(ANSI_BLUE + "Contact List" + ANSI_RESET);
            } else {
                System.out.println(ANSI_BLUE + "Query List for : " + name + ANSI_RESET);
            }

            Collections.sort(myContactsSorted);
            for (int i = 0; i < this.myContactsSorted.size(); i++) {
                System.out.println((i + 1) + ". " + this.myContactsSorted.get(i));
            }

        } else {
            System.out.println();
            System.out.println(ANSI_RED + "Contact not found." + ANSI_RESET);
        }
    }
}
