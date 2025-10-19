import java.util.InputMismatchException;
import java.util.Scanner;

abstract class Area {
    protected String name;
    protected int occupants;
    protected boolean[] lights = new boolean[3]; // index 0 -> light 1

    public Area(String name) {
        this.name = name;
        this.occupants = 0;
        for (int i = 0; i < lights.length; i++) lights[i] = false;
    }

    public String getName() { return name; }
    public int getOccupants() { return occupants; }

    public void addOccupants(int n) {
        if (n > 0) occupants += n;
    }

    public void removeOccupants(int n) {
        if (n > 0) occupants = Math.max(0, occupants - n);
    }

    public boolean switchOnLight(int lightNum) {
        if (lightNum >= 1 && lightNum <= 3) {
            lights[lightNum - 1] = true;
            return true;
        }
        return false;
    }

    public boolean switchOffLight(int lightNum) {
        if (lightNum >= 1 && lightNum <= 3) {
            lights[lightNum - 1] = false;
            return true;
        }
        return false;
    }

    public String lightsStatus() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lights.length; i++) {
            sb.append(String.format("Light %d: %s", i + 1, lights[i] ? "ON" : "OFF"));
            if (i < lights.length - 1) sb.append(" | ");
        }
        return sb.toString();
    }

    public String report() {
        return String.format("Area: %s | Occupants: %d | %s", name, occupants, lightsStatus());
    }
}

class GymArea extends Area {
    public GymArea() { super("Gym Area"); }
    // add gym-specific features if needed
}

class SwimmingArea extends Area {
    public SwimmingArea() { super("Swimming Area"); }
    // add swimming-specific features if needed
}

public class AccomodationArea {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Area gym = new GymArX
                Gea();
        Area swim = new SwimmingArea();
        Area active = gym; // default
        boolean running = true;

        System.out.println("Accommodation Areas Controller");
        while (running) {
            System.out.println("\nMain Menu:");
            System.out.println("S - Select active area (G = Gym, P = Swimming)");
            System.out.println("W - Add occupants (integer)");
            System.out.println("X - Remove occupants (integer)");
            System.out.println("Y - Switch ON light (1-3)");
            System.out.println("Z - Switch OFF light (1-3)");
            System.out.println("R - Report status");
            System.out.println("Q - Quit");
            System.out.print("Enter choice: ");
            String choice = sc.next().trim().toUpperCase();

            switch (choice) {
                case "S":
                    System.out.print("Select area (G for Gym, P for Swimming): ");
                    String area = sc.next().trim().toUpperCase();
                    if (area.equals("G")) {
                        active = gym;
                        System.out.println("Active area set to Gym.");
                    } else if (area.equals("P")) {
                        active = swim;
                        System.out.println("Active area set to Swimming.");
                    } else {
                        System.out.println("Invalid selection. Use G or P.");
                    }
                    break;

                case "W":
                    System.out.print("Enter integer number of occupants to ADD: ");
                    Integer add = readPositiveInt(sc);
                    if (add != null) {
                        active.addOccupants(add);
                        System.out.println(add + " occupants added to " + active.getName());
                    } else {
                        System.out.println("Invalid input. Action cancelled.");
                    }
                    break;

                case "X":
                    System.out.print("Enter integer number of occupants to REMOVE: ");
                    Integer rem = readPositiveInt(sc);
                    if (rem != null) {
                        active.removeOccupants(rem);
                        System.out.println(rem + " occupants removed from " + active.getName());
                    } else {
                        System.out.println("Invalid input. Action cancelled.");
                    }
                    break;

                case "Y":
                    System.out.print("Light number to SWITCH ON (1-3): ");
                    Integer on = readIntInRange(sc, 1, 3);
                    if (on != null) {
                        active.switchOnLight(on);
                        System.out.println("Light " + on + " switched ON for " + active.getName());
                    } else System.out.println("Invalid light number.");
                    break;

                case "Z":
                    System.out.print("Light number to SWITCH OFF (1-3): ");
                    Integer off = readIntInRange(sc, 1, 3);
                    if (off != null) {
                        active.switchOffLight(off);
                        System.out.println("Light " + off + " switched OFF for " + active.getName());
                    } else System.out.println("Invalid light number.");
                    break;

                case "R":
                    System.out.println("\n--- STATUS REPORT ---");
                    System.out.println(gym.report());
                    System.out.println(swim.report());
                    System.out.println("---------------------");
                    break;

                case "Q":
                    running = false;
                    System.out.println("Exiting program. Goodbye.");
                    break;

                default:
                    System.out.println("Unknown command. Please try again.");
            }
        }
        sc.close();
    }

    // helper: read positive integer (re-prompts once), returns null if invalid input detected
    private static Integer readPositiveInt(Scanner sc) {
        try {
            int val = sc.nextInt();
            if (val < 0) {
                System.out.println("Number must be >= 0.");
                return null;
            }
            return val;
        } catch (InputMismatchException ime) {
            sc.nextLine(); // clear
            System.out.println("Please enter a valid integer.");
            return null;
        }
    }

    private static Integer readIntInRange(Scanner sc, int low, int high) {
        try {
            int val = sc.nextInt();
            if (val < low || val > high) {
                System.out.println("Value out of range.");
                return null;
            }
            return val;
        } catch (InputMismatchException ime) {
            sc.nextLine();
            System.out.println("Please enter a valid integer.");
            return null;
        }
    }
}
