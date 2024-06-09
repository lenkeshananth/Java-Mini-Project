# To-Do List and Planner

This Java program implements a To-Do List and Planner application with various features to manage tasks effectively. It allows users to add tasks, remove tasks, view tasks in different formats, and set reminders for upcoming tasks.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Running the Program](#running-the-program)
- [Usage](#usage)
  - [Adding a Task](#adding-a-task)
  - [Removing a Task](#removing-a-task)
  - [Viewing Tasks](#viewing-tasks)
  - [Calendar View](#calendar-view)
  - [Tabular View](#tabular-view)
  - [Quitting the Application](#quitting-the-application)
- [File Persistence](#file-persistence)
- [Task Priority](#task-priority)
- [Reminder System](#reminder-system)

## Features

1. **Add Task:** Add new tasks with descriptions, priorities (High, Medium, Low), and due dates.

2. **Remove Task:** Remove tasks from the list.

3. **View Tasks:** View tasks sorted by due date and priority.

4. **Calendar View:** Display tasks in a calendar format.

5. **Tabular View:** Present tasks in a tabular format for easy readability.

6. **Quit:** Exit the application.

## Getting Started

### Prerequisites

- Java Runtime Environment (JRE) installed on your machine.

### Running the Program

1. Download the `ToDoListAndPlanner.java` file.

2. Open a terminal or command prompt and navigate to the directory containing the file.

3. Compile the Java program:
   ```bash
   javac ToDoListAndPlanner.java
   ```

4. Run the compiled program:
   ```bash
   java ToDoListAndPlanner
   ```

## Usage

### Adding a Task

To add a new task:

1. Choose option `1` from the menu.

2. Enter the task description.

3. Specify the task priority (High, Medium, Low).

4. Enter the due date and time in the format `dd-MM-yyyy HH:mm`.

### Removing a Task

To remove a task:

1. Choose option `2` from the menu.

2. Select the task number to remove.

### Viewing Tasks

To view tasks sorted by due date and priority:

1. Choose option `3` from the menu.

### Calendar View

To view tasks in a calendar format:

1. Choose option `4` from the menu.

### Tabular View

To view tasks in a tabular format:

1. Choose option `5` from the menu.

### Quitting the Application

To exit the application:

1. Choose option `6` from the menu.

## File Persistence

Tasks are persisted to a CSV file (`tasks.csv`). The program loads tasks from this file at startup and saves tasks to it when tasks are added or removed.

## Task Priority

Tasks have three priority levels: High, Medium, and Low. Tasks are sorted first by due date and then by priority.

## Reminder System

The program includes a reminder system that notifies users one hour before the due date of a task.

**Note:** The reminder system runs in the background and provides timely reminders for upcoming tasks.

Thank you for using the To-Do List and Planner! If you have any questions or feedback, feel free to reach out.