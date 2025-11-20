package hotel_management_system;   // <-- agar tumhare project me ye line already hai to rehne do
                                  // agar package line kuch aur ho to usko mat badlo
                                  // agar package line hi nahi hai to is line ko hata dena

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Hotel_Management_System extends JFrame {

    // ====== DATA LISTS (In-Memory) ======
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Customer> customers = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

    private int nextCustomerId = 1;
    private int nextBookingId = 1;

    // ====== UI COMPONENTS ======
    private JTabbedPane tabbedPane;

    // Rooms tab
    private JTextField txtRoomNumber, txtRoomPrice;
    private JComboBox<String> comboRoomType;
    private DefaultTableModel modelRooms;
    private JTable tableRooms;

    // Customers tab
    private JTextField txtCustomerName, txtCustomerPhone;
    private DefaultTableModel modelCustomers;
    private JTable tableCustomers;

    // Bookings tab
    private JComboBox<Room> comboBookingRoom;
    private JComboBox<Customer> comboBookingCustomer;
    private JTextField txtCheckIn, txtCheckOut;
    private DefaultTableModel modelBookings;
    private JTable tableBookings;

    // ====== CONSTRUCTOR ======
    public Hotel_Management_System() {
        setTitle("Hotel Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);              // Center screen
        setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Rooms", createRoomsPanel());
        tabbedPane.addTab("Customers", createCustomersPanel());
        tabbedPane.addTab("Bookings", createBookingsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ====== ROOMS PANEL ======
    private JPanel createRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.add(new JLabel("Room Number:"));
        txtRoomNumber = new JTextField();
        formPanel.add(txtRoomNumber);

        formPanel.add(new JLabel("Room Type:"));
        comboRoomType = new JComboBox<>(new String[] {
            "Single", "Double", "Deluxe", "Suite"
        });
        formPanel.add(comboRoomType);

        formPanel.add(new JLabel("Price per Night:"));
        txtRoomPrice = new JTextField();
        formPanel.add(txtRoomPrice);

        panel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton btnAddRoom = new JButton("Add Room");
        JButton btnClearRoom = new JButton("Clear Fields");

        buttonPanel.add(btnAddRoom);
        buttonPanel.add(btnClearRoom);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        modelRooms = new DefaultTableModel(
            new Object[] { "Room No", "Type", "Price", "Available" }, 0
        );
        tableRooms = new JTable(modelRooms);
        JScrollPane scroll = new JScrollPane(tableRooms);
        panel.add(scroll, BorderLayout.CENTER);

        btnAddRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoom();
            }
        });

        btnClearRoom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtRoomNumber.setText("");
                txtRoomPrice.setText("");
                comboRoomType.setSelectedIndex(0);
            }
        });

        return panel;
    }

    // ====== CUSTOMERS PANEL ======
    private JPanel createCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Customer Name:"));
        txtCustomerName = new JTextField();
        formPanel.add(txtCustomerName);

        formPanel.add(new JLabel("Phone:"));
        txtCustomerPhone = new JTextField();
        formPanel.add(txtCustomerPhone);

        panel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton btnAddCustomer = new JButton("Add Customer");
        JButton btnClearCustomer = new JButton("Clear Fields");

        buttonPanel.add(btnAddCustomer);
        buttonPanel.add(btnClearCustomer);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        modelCustomers = new DefaultTableModel(
            new Object[] { "Customer ID", "Name", "Phone" }, 0
        );
        tableCustomers = new JTable(modelCustomers);
        JScrollPane scroll = new JScrollPane(tableCustomers);
        panel.add(scroll, BorderLayout.CENTER);

        btnAddCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        btnClearCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtCustomerName.setText("");
                txtCustomerPhone.setText("");
            }
        });

        return panel;
    }

    // ====== BOOKINGS PANEL ======
    private JPanel createBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        formPanel.add(new JLabel("Select Customer:"));
        comboBookingCustomer = new JComboBox<>();
        formPanel.add(comboBookingCustomer);

        formPanel.add(new JLabel("Select Room (Available Only):"));
        comboBookingRoom = new JComboBox<>();
        formPanel.add(comboBookingRoom);

        formPanel.add(new JLabel("Check-in Date (dd-mm-yyyy):"));
        txtCheckIn = new JTextField();
        formPanel.add(txtCheckIn);

        formPanel.add(new JLabel("Check-out Date (dd-mm-yyyy):"));
        txtCheckOut = new JTextField();
        formPanel.add(txtCheckOut);

        panel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton btnCreateBooking = new JButton("Create Booking");
        JButton btnCheckout = new JButton("Check-out (Complete Booking)");

        buttonPanel.add(btnCreateBooking);
        buttonPanel.add(btnCheckout);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        modelBookings = new DefaultTableModel(
            new Object[] { "Booking ID", "Customer", "Room", "Check-in", "Check-out" }, 0
        );
        tableBookings = new JTable(modelBookings);
        JScrollPane scroll = new JScrollPane(tableBookings);
        panel.add(scroll, BorderLayout.CENTER);

        btnCreateBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createBooking();
            }
        });

        btnCheckout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkoutBooking();
            }
        });

        return panel;
    }

    // ====== LOGIC: ADD ROOM ======
    private void addRoom() {
        String roomNoText = txtRoomNumber.getText().trim();
        String priceText = txtRoomPrice.getText().trim();
        String type = (String) comboRoomType.getSelectedItem();

        if (roomNoText.isEmpty() || priceText.isEmpty()) {
            showMessage("Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int roomNo;
        double price;

        try {
            roomNo = Integer.parseInt(roomNoText);
        } catch (NumberFormatException e) {
            showMessage("Room number must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            showMessage("Price must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (Room r : rooms) {
            if (r.number == roomNo) {
                showMessage("Room number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Room room = new Room(roomNo, type, price, true);
        rooms.add(room);

        refreshRoomTable();
        refreshRoomCombo();

        txtRoomNumber.setText("");
        txtRoomPrice.setText("");
        comboRoomType.setSelectedIndex(0);
    }

    // ====== LOGIC: ADD CUSTOMER ======
    private void addCustomer() {
        String name = txtCustomerName.getText().trim();
        String phone = txtCustomerPhone.getText().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            showMessage("Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Customer customer = new Customer(nextCustomerId++, name, phone);
        customers.add(customer);

        refreshCustomerTable();
        refreshCustomerCombo();

        txtCustomerName.setText("");
        txtCustomerPhone.setText("");
    }

    // ====== LOGIC: CREATE BOOKING ======
    private void createBooking() {
        Customer customer = (Customer) comboBookingCustomer.getSelectedItem();
        Room room = (Room) comboBookingRoom.getSelectedItem();
        String checkIn = txtCheckIn.getText().trim();
        String checkOut = txtCheckOut.getText().trim();

        if (customer == null || room == null) {
            showMessage("Please select customer and room!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (checkIn.isEmpty() || checkOut.isEmpty()) {
            showMessage("Please enter check-in and check-out dates!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Booking booking = new Booking(nextBookingId++, customer, room, checkIn, checkOut);
        bookings.add(booking);

        room.available = false;

        refreshRoomTable();
        refreshRoomCombo();
        refreshBookingTable();

        txtCheckIn.setText("");
        txtCheckOut.setText("");
    }

    // ====== LOGIC: CHECK-OUT ======
    private void checkoutBooking() {
        int selectedRow = tableBookings.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a booking to check-out!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int bookingId = (Integer) modelBookings.getValueAt(selectedRow, 0);

        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            if (b.id == bookingId) {
                b.room.available = true;
                bookings.remove(i);
                break;
            }
        }

        refreshRoomTable();
        refreshRoomCombo();
        refreshBookingTable();
    }

    // ====== REFRESH METHODS ======
    private void refreshRoomTable() {
        modelRooms.setRowCount(0);
        for (Room r : rooms) {
            modelRooms.addRow(new Object[] {
                r.number,
                r.type,
                r.price,
                r.available ? "Yes" : "No"
            });
        }
    }

    private void refreshCustomerTable() {
        modelCustomers.setRowCount(0);
        for (Customer c : customers) {
            modelCustomers.addRow(new Object[] {
                c.id,
                c.name,
                c.phone
            });
        }
    }

    private void refreshBookingTable() {
        modelBookings.setRowCount(0);
        for (Booking b : bookings) {
            modelBookings.addRow(new Object[] {
                b.id,
                b.customer.name,
                b.room.number + " (" + b.room.type + ")",
                b.checkIn,
                b.checkOut
            });
        }
    }

    private void refreshRoomCombo() {
        comboBookingRoom.removeAllItems();
        for (Room r : rooms) {
            if (r.available) {
                comboBookingRoom.addItem(r);
            }
        }
    }

    private void refreshCustomerCombo() {
        comboBookingCustomer.removeAllItems();
        for (Customer c : customers) {
            comboBookingCustomer.addItem(c);
        }
    }

    // ====== HELPER ======
    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    // ====== DATA CLASSES ======
    private static class Room {
        int number;
        String type;
        double price;
        boolean available;

        Room(int number, String type, double price, boolean available) {
            this.number = number;
            this.type = type;
            this.price = price;
            this.available = available;
        }

        @Override
        public String toString() {
            return number + " - " + type + " (Rs. " + price + ")";
        }
    }

    private static class Customer {
        int id;
        String name;
        String phone;

        Customer(int id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }

        @Override
        public String toString() {
            return id + " - " + name;
        }
    }

    private static class Booking {
        int id;
        Customer customer;
        Room room;
        String checkIn;
        String checkOut;

        Booking(int id, Customer customer, Room room,
                String checkIn, String checkOut) {
            this.id = id;
            this.customer = customer;
            this.room = room;
            this.checkIn = checkIn;
            this.checkOut = checkOut;
        }
    }

    // ====== MAIN METHOD (bilkul aise hi rehna chahiye) ======
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Hotel_Management_System().setVisible(true);
            }
        });
    }
}
