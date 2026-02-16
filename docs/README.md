# Gojo Satoru - The Cursed Task Manager

> "Throughout heaven and earth, I alone am the honored one."

**Gojo** is a Command Line Interface (CLI) task manager bot inspired by the strongest sorcerer, Gojo Satoru. It helps you track your Todos, Deadlines, and Events with the precision of the Six Eyes.

> [!NOTE]
> **Persistence:** Your tasks are automatically saved to the "Infinite Void" (your hard disk) after every command. You won't lose them even if you exit.

## Features 🤞

- **Manage Tasks**: Add Todo, Deadline, and Event tasks.
- **Task Tracking**: Mark tasks as done (`[X]`) or not done (`[ ]`).
- **Search**: Find tasks using keywords.
- **Schedule View**: Check your schedule for a specific date.
- **Persona**: Enjoy interactions with the charismatic and powerful Gojo Satoru.

---

## Command Summary

For those with the Six Eyes who can read fast, here is the quick reference:

| Command | Description | Format |
| :--- | :--- | :--- |
| `todo` | Add a standard task | `todo <description>` |
| `deadline` | Add a task with a due date | `deadline <desc> /by <date>` |
| `event` | Add a task with a duration | `event <desc> /from <start> /to <end>` |
| `list` | Show all tasks | `list` |
| `mark` | Mark task as done | `mark <index>` |
| `unmark` | Mark task as not done | `unmark <index>` |
| `delete` | Remove a task | `delete <index>` |
| `find` | Search for keywords | `find <keyword>` |
| `schedule`| View tasks for a date | `schedule <date>` |
| `bye` | Exit the application | `bye` |

---

## Detailed Usage

### 1. `todo` - Add a Todo Task
Adds a standard todo task to your list.

**Example:**
```bash
todo master the infinite void
Output:

Plaintext
I've added this to the Infinite Void. Don't get lost:
  [T][ ] master the infinite void
Now you have 5 tasks in the list.
2. deadline - Add a Deadline Task

Adds a task that needs to be done before a specific date and time.

[!TIP]
Date Formats Accepted:

yyyy-MM-dd (e.g., 2026-02-20)

d/M/yyyy HHmm (e.g., 2/12/2019 1800)

Natural language: today, tomorrow

Example:

Bash
deadline exorcise curses /by 2026-02-20 1800
Output:

Plaintext
I've added this to the Infinite Void. Don't get lost:
  [D][ ] exorcise curses (by: Feb 20 2026 18:00)
Now you have 6 tasks in the list.
3. event - Add an Event Task

Adds a task that starts at a specific time and ends at a specific time.

Example:

Bash
event teach yuji /from today /to tomorrow
Output:

Plaintext
I've added this to the Infinite Void. Don't get lost:
  [E][ ] teach yuji (from: Feb 16 2026 23:59 to: Feb 17 2026 23:59)
Now you have 7 tasks in the list.
4. list - List All Tasks

Displays all tasks currently in your list.

Example:

Bash
list
Output:

Plaintext
My Six Eyes see everything. Here are your tasks:
1. [T][X] buy sweets
2. [D][ ] submit report (by: Aug 30 2025)
3. [E][ ] training (from: Mon 2pm to: 4pm)
5. mark / unmark - Update Task Status

Marks a task as done or not done. Use the index number from the list command.

Example:

Bash
mark 1
Output:

Plaintext
Hollow... Purple! Task obliterated (completed):
  [T][X] buy sweets
6. delete - Delete a Task

Removes a task from the list permanently.

Example:

Bash
delete 2
Output:

Plaintext
Noted. I've removed this task:
  [D][ ] submit report (by: Aug 30 2025)
Now you have 6 tasks in the list.
7. find - Search Tasks

Finds tasks that contain the specified keyword. My Six Eyes don't miss a thing—if you wrote it, I'll find it.

Example:

Bash
find book
Output:

Plaintext
Found matches! My Six Eyes don't miss a thing:
1. [D][ ] return book (by: Sunday)
2. [T][ ] read book
8. schedule - View Schedule

Checks your timeline for a specific date. A sorcerer must always be aware of their future commitments.

Example:

Bash
schedule 2026-02-20
Output:

Plaintext
Time is relative, but here is your schedule for Feb 20 2026:
1. [D][ ] exorcise curses (by: Feb 20 2026 18:00)
9. bye - Exit

Exits the application.

Output:

Plaintext
Bye! Don't let the curses bite. Stay Limitless ♾️
Getting Started
[!IMPORTANT]
Prerequisite: Ensure you have Java 17 (or higher) installed on your machine to run this sorcery.

Navigate to the project root.

Run the application using Gradle:

Bash
./gradlew run
Optional: Run the text UI tests to verify functionality:

Bash
./text-ui-test/runtest.sh
"Don't worry, I'm the strongest."
"Nah, I'd win." 