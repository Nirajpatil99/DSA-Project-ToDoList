import java.util.Scanner;

class ToDoItem {
    String description;
    int priority;

    public ToDoItem(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }
}

class CircularQueue {
    int size;
    ToDoItem[] queue;
    int front;
    int rear;

    public CircularQueue(int size) {
        this.size = size;
        this.queue = new ToDoItem[size];
        this.front = -1;
        this.rear = -1;
    }

    public boolean isFull() {
        return (front == 0 && rear == size - 1) || (rear == (front - 1) % (size - 1));
    }

    public boolean isEmpty() {
        return front == -1;
    }

    public void enqueue(ToDoItem item) {
        if (isFull()) {
            System.out.println("Queue is full. Cannot add more items.");
            return;
        }

        if (isEmpty()) {
            front = rear = 0;
            queue[rear] = item;
        } else {
            int i;
            for (i = rear; i >= front; i--) {
                if (item.priority > queue[i].priority) {
                    queue[(i + 1) % size] = queue[i];
                } else {
                    break;
                }
            }
            queue[(i + 1) % size] = item;
            rear = (rear + 1) % size;
        }
    }

    public ToDoItem dequeue() {
        if (isEmpty()) {
            System.out.println("Queue is empty. Nothing to remove.");
            return null;
        }

        ToDoItem removedItem = queue[front];
        if (front == rear) {
            front = rear = -1;
        } else {
            front = (front + 1) % size;
        }
        return removedItem;
    }

    public void updateDescription(int priority, String newDescription) {
        for (int i = front; i <= rear; i++) {
            if (queue[i].priority == priority) {
                queue[i].description = newDescription;
                return;
            }
        }
    }

    public void display() {
        if (isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        int current = front;
        while (true) {
            ToDoItem item = queue[current];
            System.out.println("Priority: " + item.priority + ", Description: " + item.description);
            if (current == rear) {
                break;
            }
            current = (current + 1) % size;
        }
    }
}

public class ToDoListMiniProject {
    public static void main(String[] args) {
        CircularQueue todoQueue = new CircularQueue(10);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Add a task");
            System.out.println("2. Remove the highest priority task");
            System.out.println("3. Update a task's description");
            System.out.println("4. Display tasks");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter task priority: ");
                    int priority = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    ToDoItem newItem = new ToDoItem(description, priority);
                    todoQueue.enqueue(newItem);
                    System.out.println("Task added.");
                    break;

                case 2:
                    ToDoItem removedItem = todoQueue.dequeue();
                    if (removedItem != null) {
                        System.out.println("Removed item - Priority: " + removedItem.priority + ", Description: "
                                + removedItem.description);
                    }
                    break;

                case 3:
                    System.out.print("Enter the priority of the task you want to update: ");
                    int updatePriority = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Enter the new task description: ");
                    String newDescription = scanner.nextLine();
                    todoQueue.updateDescription(updatePriority, newDescription);
                    System.out.println("Task updated.");
                    break;

                case 4:
                    todoQueue.display();
                    break;

                case 5:
                    scanner.close();
                    System.out.println("Exiting the program.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
