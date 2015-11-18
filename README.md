# expenseLog

Decisions I made when writing ExpenseLog (some are different from HW requirements but the TA said it is OK):

I accidentally implemented HW3 (of course, before knowing what HW3 is about). I was under the understanding that the ExpenseLog app would be useless if data weren't persistant. So I used sqlite along with SimpleCursorAdaptor to achieve the same (almost) functionality.

However, if I implement the ExpenseLogEntryData class and store sqlite query result in the array before inflating the list view, there would be two iterations (1, traverse the database with a cursor object; 2, read ExpenseLogEntryData objects from the array to inflate the view). I decided to use SimpleCursorAdapter right away for lower complexity.

Delete buttons were added for fun.

