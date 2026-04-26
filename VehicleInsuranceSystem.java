import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

// ---------------- Interface ----------------
interface Insurable {
    double calculatePremium();
    void showInsuranceDetails();
}

// ---------------- Base Class ----------------
abstract class Vehicle implements Insurable {
    protected String registrationNumber;
    protected String ownerName;
    protected double vehicleValue;

    public Vehicle(String registrationNumber, String ownerName, double vehicleValue) {
        this.registrationNumber = registrationNumber;
        this.ownerName = ownerName;
        this.vehicleValue = vehicleValue;
    }

    public abstract double calculatePremium();

    public String getInsuranceDetails() {
        return "Owner: " + ownerName +
                "\nRegistration Number: " + registrationNumber +
                "\nVehicle Value: " + vehicleValue +
                "\nPremium: " + calculatePremium() +
                "\n---------------------------------\n";
    }

    @Override
    public void showInsuranceDetails() {
        JOptionPane.showMessageDialog(null, getInsuranceDetails(),
                "Insurance Details", JOptionPane.INFORMATION_MESSAGE);
    }
}

// ---------------- Subclasses ----------------
class Car extends Vehicle {
    private int numberOfAirbags;

    public Car(String regNo, String owner, double value, int airbags) {
        super(regNo, owner, value);
        this.numberOfAirbags = airbags;
    }

    @Override
    public double calculatePremium() {
        return (vehicleValue * 0.05) - (numberOfAirbags * 100);
    }
}

class Bike extends Vehicle {
    private boolean hasABS;

    public Bike(String regNo, String owner, double value, boolean abs) {
        super(regNo, owner, value);
        this.hasABS = abs;
    }

    @Override
    public double calculatePremium() {
        return (vehicleValue * 0.03) + (hasABS ? 200 : 0);
    }
}

class Truck extends Vehicle {
    private double loadCapacity;

    public Truck(String regNo, String owner, double value, double capacity) {
        super(regNo, owner, value);
        this.loadCapacity = capacity;
    }

    @Override
    public double calculatePremium() {
        return (vehicleValue * 0.06) + (loadCapacity * 10);
    }
}

// ---------------- Main Application ----------------
public class VehicleInsuranceSystem extends JFrame {
    private java.util.List<Vehicle> vehicles = new ArrayList<>();
    private JTextArea displayArea;

    public VehicleInsuranceSystem() {
        setTitle("Vehicle Insurance System");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // allow maximize
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Panels for each tab
        tabbedPane.add("Car", createCarPanel());
        tabbedPane.add("Bike", createBikePanel());
        tabbedPane.add("Truck", createTruckPanel());
        tabbedPane.add("Show All", createShowAllPanel());

        add(tabbedPane);
    }

    // Panel for Car input
    private JPanel createCarPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField regField = new JTextField();
        JTextField ownerField = new JTextField();
        JTextField valueField = new JTextField();
        JTextField airbagsField = new JTextField();

        JButton addButton = new JButton("Add Car");
        addButton.setFont(new Font("Serif",Font.BOLD,28));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Serif",Font.BOLD,28));

        JLabel regno=new JLabel("Registration Number:");
        regno.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(regno);
        panel.add(regField);
        
        JLabel l2=new JLabel("Owner Name:");
        l2.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(l2);
        panel.add(ownerField);
        
        JLabel l3=new JLabel("Vehicle Value:");
        l3.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(l3);
        panel.add(valueField);
        
        JLabel l4=new JLabel("Number of Airbags:");
        l4.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(l4);
        panel.add(airbagsField);
        panel.add(addButton);
        panel.add(cancelButton);

        addButton.addActionListener(e -> {
            try {
                String regNo = regField.getText();
                String owner = ownerField.getText();
                double value = Double.parseDouble(valueField.getText());
                int airbags = Integer.parseInt(airbagsField.getText());

                vehicles.add(new Car(regNo, owner, value, airbags));
                JOptionPane.showMessageDialog(this, "Car Insurance Added Successfully!");
                regField.setText(""); ownerField.setText(""); valueField.setText(""); airbagsField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input. Please try again.");
            }
        });

        cancelButton.addActionListener(e -> {
            regField.setText(""); ownerField.setText(""); valueField.setText(""); airbagsField.setText("");
        });

        return panel;
    }

    // Panel for Bike input
    private JPanel createBikePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JTextField regField = new JTextField();
        JTextField ownerField = new JTextField();
        JTextField valueField = new JTextField();
        JCheckBox absCheck = new JCheckBox("Has ABS");
        absCheck.setFont(new Font("Serif",Font.BOLD,28));

        JButton addButton = new JButton("Add Bike");
        addButton.setFont(new Font("Serif",Font.BOLD,28));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Serif",Font.BOLD,28));

        JLabel regno=new JLabel("Registration Number:");
        regno.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(regno);
        panel.add(regField);
        
        JLabel l2=new JLabel("Owner Name:");
        l2.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(l2);
        panel.add(ownerField);
        
        JLabel l3=new JLabel("Vehicle Value:");
        l3.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(l3);
        panel.add(valueField);
        
        panel.add(absCheck);
        panel.add(new JLabel(""));
        panel.add(addButton);
        panel.add(cancelButton);

        addButton.addActionListener(e -> {
            try {
                String regNo = regField.getText();
                String owner = ownerField.getText();
                double value = Double.parseDouble(valueField.getText());
                boolean hasABS = absCheck.isSelected();

                vehicles.add(new Bike(regNo, owner, value, hasABS));
                JOptionPane.showMessageDialog(this, "Bike Insurance Added Successfully!");
                regField.setText(""); ownerField.setText(""); valueField.setText(""); absCheck.setSelected(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input. Please try again.");
            }
        });

        cancelButton.addActionListener(e -> {
            regField.setText(""); ownerField.setText(""); valueField.setText(""); absCheck.setSelected(false);
        });

        return panel;
    }

    // Panel for Truck input
    private JPanel createTruckPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JTextField regField = new JTextField();
        JTextField ownerField = new JTextField();
        JTextField valueField = new JTextField();
        JTextField capacityField = new JTextField();

        JButton addButton = new JButton("Add Truck");
        addButton.setFont(new Font("Serif",Font.BOLD,28));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Serif",Font.BOLD,28));

        JLabel regno=new JLabel("Registration Number:");
        regno.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(regno);
        panel.add(regField);
        
        JLabel l2=new JLabel("Owner Name:");
        l2.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(l2);
        panel.add(ownerField);
        
        JLabel l3=new JLabel("Vehicle Value:");
        l3.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(l3);
        panel.add(valueField);
        
        JLabel l4=new JLabel("Load Capacity (tons):");
        l4.setFont(new Font("Serif",Font.BOLD,28));
        panel.add(l4);
        panel.add(capacityField);
        panel.add(addButton);
        panel.add(cancelButton);

        addButton.addActionListener(e -> {
            try {
                String regNo = regField.getText();
                String owner = ownerField.getText();
                double value = Double.parseDouble(valueField.getText());
                double capacity = Double.parseDouble(capacityField.getText());

                vehicles.add(new Truck(regNo, owner, value, capacity));
                JOptionPane.showMessageDialog(this, "Truck Insurance Added Successfully!");
                regField.setText(""); ownerField.setText(""); valueField.setText(""); capacityField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input. Please try again.");
            }
        });

        cancelButton.addActionListener(e -> {
            regField.setText(""); ownerField.setText(""); valueField.setText(""); capacityField.setText("");
        });

        return panel;
    }

    // Panel to display all vehicles
    private JPanel createShowAllPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Serif",Font.BOLD,18));
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Serif",Font.BOLD,28));
        JButton saveButton = new JButton("Save to File");
        saveButton.setFont(new Font("Serif",Font.BOLD,28));
        try{
            BufferedReader reader =new BufferedReader(new FileReader("InsuranceRecords.txt"));
            
            StringBuilder filecontent= new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                filecontent.append(line);
                filecontent.append("\n");
            }
            reader.close();
            displayArea.setText(filecontent.toString());
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error reading file: "+e.getMessage());

        }
        refreshButton.addActionListener(e -> {
            try{
            BufferedReader reader =new BufferedReader(new FileReader("InsuranceRecords.txt"));
            
            StringBuilder filecontent= new StringBuilder();
            String line;
            while((line=reader.readLine())!=null){
                filecontent.append(line);
                filecontent.append("\n");
            }
            reader.close();
            displayArea.setText(filecontent.toString());
        }
        catch(IOException e1){
            JOptionPane.showMessageDialog(null,"Error reading file: "+e1.getMessage());

        }
            if (vehicles.isEmpty()) {
                displayArea.append("------End of the list-----");
            } else {
                for (Vehicle v : vehicles) {
                    displayArea.append(v.getInsuranceDetails());
                }
            }
        });
        
        saveButton.addActionListener(e -> {
            if (vehicles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No records to save!");
                return;
            }
            try (FileWriter writer = new FileWriter("InsuranceRecords.txt",true)) {
                for (Vehicle v : vehicles) {
                    writer.write(v.getInsuranceDetails());
                }
                vehicles.clear();
                JOptionPane.showMessageDialog(this, "Records saved to InsuranceRecords.txt successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage());
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(refreshButton,BorderLayout.NORTH);
        panel.add(saveButton,BorderLayout.SOUTH);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VehicleInsuranceSystem().setVisible(true);
        });
    }
}
