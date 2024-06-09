import java.io.*;
import static ui.UI.*;
import ui.UI.Colors;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class ToDoListAndPlanner {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    private static final Calendar calendar = Calendar.getInstance();
    private static final Timer reminderTimer = new Timer();
    public static final String CSV_FILE_PATH = "tasks.csv";
  
    public static void main(String[] args) {
        //ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Task> tasks = loadTasksFromCSV(); // Load tasks from CSV at the beginning
        Scanner scanner = new Scanner(System.in);
        clearScreen();
        while (true) {
            
            printTitle('=',"To-Do List and Planner Menu");
           System.out.print("\n");
          System.out.println("1."+Colors.BOLD+Colors.GREEN+" Add task  "+Colors.RESET+Colors.PLUS_SYMBOL_EMOJI);
            System.out.print("\n");
            System.out.println("2."+Colors.BOLD+Colors.RED+" Remove task "+Colors.RESET+Colors.CROSS_MARK_EMOJI);
           System.out.print("\n");
          System.out.println("3."+Colors.BOLD+Colors.YELLOW+" View tasks "+Colors.RESET+Colors.EYES_EMOJI);
           System.out.print("\n");
          System.out.println("4."+Colors.BOLD+Colors.CYAN+" Calendar view "+Colors.RESET+Colors.APPOINTMENT_CALENDAR);
           System.out.print("\n");
          System.out.println("5."+Colors.BOLD+Colors.BLUE+" Tabular view "+Colors.RESET+Colors.NEWSPAPER_EMOJI);
           System.out.print("\n");
            System.out.println("6."+Colors.BOLD+Colors.RED+" Quit"+Colors.RESET);
            System.out.print(Colors.ITALIC+Colors.UNDERLINE+"\nEnter your choice"+Colors.RESET+": ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addTask(tasks, scanner);
                    writeTasksToCSV(tasks);
                    sleep(3000);
                    clearScreen();
                    break;
                case 2:
                    removeTask(tasks, scanner);
                    writeTasksToCSV(tasks);
                    sleep(3000);
                    clearScreen();
                    break;
                case 3:
                    viewTasksSortedByDueDateAndPriority(tasks);
                    break;
                case 4:
                    viewCalendar(tasks, scanner);
                    break;
                case 5:
                    displayTasksInTableFormat(tasks);
                break;
                case 6:
                    System.out.println("\n See you soon , Thanks for being here :) ");
                    scanner.close();
                    reminderTimer.cancel(); // Cancel the timer
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addTask(ArrayList<Task> tasks, Scanner scanner) {
        System.out.print(Colors.BOLD+Colors.ITALIC+"\nEnter a new task: "+Colors.RESET);
        String taskDescription = scanner.nextLine();
        System.out.print(Colors.BOLD+Colors.ITALIC+"Enter task priority (High, Medium, Low): "+Colors.RESET);
        String priority = scanner.nextLine();
        System.out.print(Colors.BOLD+Colors.ITALIC+"Enter due date and time (dd-MM-yyyy HH:mm): "+Colors.RESET);
        String dueDateStr = scanner.nextLine();

        try {
            Date dueDate = dateFormat.parse(dueDateStr);
            Date currentTime = new Date(); // Get the current date and time

            if (dueDate.before(currentTime)) {
                System.out.println(Colors.BOLD+Colors.RED+"Invalid due date. Task not added. Due date cannot be in the past."+Colors.RESET);
            } else {
                Task newTask = new Task(taskDescription, priority, dueDate);
                tasks.add(newTask);
                System.out.println(Colors.BOLD+
                        "Task added: " + taskDescription + " (Priority: " + priority + ", Due: " + dueDateStr + ")"+Colors.RESET);
                setReminder(newTask, reminderTimer);
            }
        } catch (Exception e) {
            System.out.println(Colors.BOLD+Colors.RED+"Invalid date format. Task not added."+Colors.RESET);
        }
    }


  private static void writeTasksToCSV(ArrayList<Task> tasks) {
      try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE_PATH))) {
          // Writing header
          writer.println("Description,Priority,Due Date");

          for (Task task : tasks) {
              // Formatting task data
              String taskData = String.format("%s,%s,%s",
                      escapeCsvField(task.getDescription()),
                      escapeCsvField(task.getPriority()),
                      dateFormat.format(task.getDueDate()));

              writer.println(taskData);
          }

          System.out.println(Colors.BOLD+Colors.GREEN+"Tasks written to CSV file: "+Colors.RESET + CSV_FILE_PATH);
      } catch (IOException e) {
          System.out.println(Colors.BOLD+Colors.RED+"Error writing tasks to CSV file: "+Colors.RESET + e.getMessage());
      }
  }
  
  private static String escapeCsvField(String field) {
      // If the field contains a comma, double-quote, or newline, enclose it in double quotes
      if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
          return "\"" + field.replace("\"", "\"\"") + "\"";
      } else {
          return field;
      }
  }

  private static void displayTasksInTableFormat(ArrayList<Task> tasks) {
      if (tasks.isEmpty()) {
          System.out.println(Colors.BOLD+Colors.YELLOW+"No tasks available."+Colors.RESET);
          return;
      }

      System.out.println(Colors.BOLD+"Your tasks:\n"+Colors.RESET);

      // Define column widths
      int descriptionWidth = 40;
      int priorityWidth = 15;
      int dueDateWidth = 25;

      // Print header
      System.out.format("%-" + descriptionWidth + "s%-" + priorityWidth + "s%-" + dueDateWidth + "s%n",
              "Description", "Priority", "Due Date");

      // Print separator line
      printSeparatorLine(descriptionWidth + priorityWidth + dueDateWidth);

      // Print tasks
      for (Task task : tasks) {
          String description = task.getDescription();
          String priority = task.getPriority();
          String dueDate = dateFormat.format(task.getDueDate());

          System.out.format("%-" + descriptionWidth + "s%-" + priorityWidth + "s%-" + dueDateWidth + "s%n",
                  description, priority, dueDate);
      }

      // Print separator line after tasks
      printSeparatorLine(descriptionWidth + priorityWidth + dueDateWidth);
    if (askClearScreen()) {
        clearScreen();
    }
  }

  private static void printSeparatorLine(int totalWidth) {
      System.out.println("-".repeat(totalWidth));
  }

    private static void removeTask(ArrayList<Task> tasks, Scanner scanner) {
        if (tasks.isEmpty()) {
            System.out.println(Colors.BOLD+Colors.RED+"No tasks to remove."+Colors.RESET);
            return;
        }

        System.out.println(Colors.BOLD+"Your tasks:"+Colors.RESET);
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).toStringWithPriority());
        }
        System.out.print(Colors.BOLD+Colors.ITALIC+"Enter the task number to remove: "+Colors.RESET);
        int taskNumber = scanner.nextInt();
        if (taskNumber >= 1 && taskNumber <= tasks.size()) {
            Task removedTask = tasks.remove(taskNumber - 1);
            System.out.println("Removed task: " + removedTask.getDescription());
            removeReminder(removedTask, reminderTimer);
        } else {
            System.out.println(Colors.BOLD+Colors.RED+"Invalid task number."+Colors.RESET);
        }
    }

    private static void viewTasksSortedByDueDateAndPriority(ArrayList<Task> tasks) {
        if (tasks.isEmpty()) {
            System.out.println(Colors.BOLD+Colors.RED+"No tasks available."+Colors.RESET);
            return;
        }

        System.out.println(Colors.BOLD+"Your tasks (sorted by due date and priority):"+Colors.RESET);
        Collections.sort(tasks, new TaskDueDateAndPriorityComparator());
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).toStringWithPriority());
        }
      if (askClearScreen()) {
          clearScreen();
      }
    }

    private static void viewCalendar(ArrayList<Task> tasks, Scanner scanner) {
        int currentMonth = -1;
        int currentYear = -1;
        Set<String> printedDates = new HashSet<>();

        for (Task task : tasks) {
            calendar.setTime(task.getDueDate());
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            if (currentMonth != month || currentYear != year) {
                if (currentMonth != -1) {
                    System.out.println(); // Separate previous month's output
                }
                currentMonth = month;
                currentYear = year;
                printedDates.clear(); // Clear the set for each new month
                printCalendarHeader(month, year);
                printCalendarGrid(tasks, currentMonth, currentYear, scanner);
            }

            String dateKey = year + "-" + month + "-" + day;
            if (!printedDates.contains(dateKey)) {
                System.out.print(day + ": ");
                printTasksForDate(tasks, year, month, day);
                printedDates.add(dateKey);
            }
        }
        System.out.println(); // Separate last month's output
        if (askClearScreen()) {
            clearScreen();
        }

    }

  private static ArrayList<Task> loadTasksFromCSV() {
      ArrayList<Task> tasks = new ArrayList<>();

      try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
          String line;
          boolean isFirstLine = true; // Skip the header line

          while ((line = reader.readLine()) != null) {
              if (isFirstLine) {
                  isFirstLine = false;
                  continue; // Skip the header line
              }

              String[] fields = line.split(",");
              String description = fields[0];
              String priority = fields[1];
              String dueDateStr = fields[2];

              try {
                  Date dueDate = dateFormat.parse(dueDateStr);
                  Task task = new Task(description, priority, dueDate);
                  tasks.add(task);
              } catch (Exception e) {
                  System.out.println("Error parsing date from CSV: " + e.getMessage());
              }
          }
      } catch (IOException e) {
          System.out.println(Colors.BOLD+Colors.RED+"Error reading tasks from CSV: "+Colors.RESET + e.getMessage());
      }

      return tasks;
  }

  
    private static void printCalendarGrid(ArrayList<Task> tasks, int month, int year, Scanner scanner) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentDay = 1;

        for (int i = 0; i < startDayOfWeek; i++) {
            System.out.print("\t\t");
        }

        while (currentDay <= daysInMonth) {
            for (int i = startDayOfWeek; i < 7 && currentDay <= daysInMonth; i++) {
                System.out.print(currentDay + "\t\t");
                currentDay++;
            }
            System.out.println();
            startDayOfWeek = 0; // Reset startDayOfWeek for the next row
        }

    }

    private static void printCalendarHeader(int month, int year) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
        System.out.println("\n" + monthFormat.format(new Date(year - 1900, month, 1)) + " " + year);
        System.out.println("Sun\t\tMon\t\tTue\t\tWed\t\tThu\t\tFri\t\tSat");
    }

    private static void printTasksForDate(ArrayList<Task> tasks, int year, int month, int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        boolean tasksPrinted = false;

        for (Task task : tasks) {
            calendar.setTime(task.getDueDate());
            int taskDay = calendar.get(Calendar.DAY_OF_MONTH);
            int taskMonth = calendar.get(Calendar.MONTH);
            int taskYear = calendar.get(Calendar.YEAR);

            if (day == taskDay && month == taskMonth && year == taskYear) {
                if (!tasksPrinted) {
                    tasksPrinted = true;
                    System.out.println(); // Move to the next line before printing tasks
                }
                System.out.print("\t\t" + task.toStringWithPriority() + "\n");
            }
        }
    }

    private static void setReminder(Task task, Timer timer) {
        Date dueDate = task.getDueDate();
        Calendar reminderTime = Calendar.getInstance();
        reminderTime.setTime(dueDate);
        reminderTime.add(Calendar.HOUR, -1); // Remind 1 hour before the due date

        timer.schedule(new ReminderTask(task), reminderTime.getTime());
    }

    private static void removeReminder(Task task, Timer timer) {
        timer.purge();
    }
}

class Task {
    private String description;
    private String priority;
    private Date dueDate;

    public Task(String description, String priority, Date dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String toStringWithPriority() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String priorityColor = getPriorityColor();

        return priorityColor + description + " (Priority: " + priority + ", Due: " + dateFormat.format(dueDate)
                + "\u001B[0m";
    }

    private String getPriorityColor() {
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_YELLOW = "\u001B[33m";

        if (priority.equalsIgnoreCase("High")) {
            return ANSI_RED;
        } else if (priority.equalsIgnoreCase("Medium")) {
            return ANSI_GREEN;
        } else if (priority.equalsIgnoreCase("Low")) {
            return ANSI_YELLOW;
        }

        return "";
    }
}

class ReminderTask extends TimerTask {
    private Task task;

    public ReminderTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println("Reminder: " + task.getDescription() + Colors.BOLD+Colors.RED+" is due soon!"+Colors.RESET);
    }
}

class TaskDueDateAndPriorityComparator implements Comparator<Task> {
    @Override
    public int compare(Task task1, Task task2) {
        int dateComparison = task1.getDueDate().compareTo(task2.getDueDate());

        if (dateComparison != 0) {
            return dateComparison;
        }

        return priorityToInteger(task2.getPriority()) - priorityToInteger(task1.getPriority());
    }

    private int priorityToInteger(String priority) {
        if (priority.equalsIgnoreCase("High")) {
            return 3;
        } else if (priority.equalsIgnoreCase("Medium")) {
            return 2;
        } else if (priority.equalsIgnoreCase("Low")) {
            return 1;
        }
        return 0;
    }
}
