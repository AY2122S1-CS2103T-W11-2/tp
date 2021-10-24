---
layout: page
title: User Guide
---

Staff’d helps food & beverage managers manage details and schedules of their staff. It is optimized for CLI users so that frequent tasks can be done faster by typing in commands. It is optimized for restaurants with two active shifts.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

### Firing up Staff'd

1. Ensure you have _Java 11_ or above installed in your Computer.

1. Download the latest _staffd.jar_ from [here](https://github.com/AY2122S1-CS2103T-W11-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your Staff’d.

2. If linux is your operating system, run `chmod +x staffd.jar` from the _home folder_.

3. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

### First Steps on Staff'd

1. On the first initialisation, Staff'd will have some sample data for you to play with.

1. The following is an example of how you might use Staff'd.

1. Joe wants to join your restaurant.

1. Try adding a Joe using the `add` command.<br>`add n/Joe s/fulltime r/kitchen p/98765432 $/1234789 e/Joe@example.com a/John street, block 123, #01-01`

1. Type the command in the command box and press Enter to execute it.

1. Say the Staff in question has to work on the next monday's afternoon shift.

1. Use the `addShift` command to add the staff to the morning shift. <br> `addShift n/Joe d/monday-0`
  
1. Joe has a flu and is now unable to work on the next monday which is on 25th October 2021.

1. Mark Joe as absent with the `mark` command. <br> `mark -n Joe d/2021-10-25`

1. Joe has recovered faster than expected and is able to work on monday.

1. Remove the mark from Joe with the `unmark` command. <br> `unmark -n Joe d/2021-10-25`

1. On the first day of work, Joe has caused 10 customers food poisoning and is fired.

1. Remove Joe from Staff'd with the delete command. <br> `delete -n Joe`

1. Refer to the [Features](#features) below for more details of each command. Images are provided for commands with significant output.

--------------------------------------------------------------------------------------------------------------------

## Features

### Flag legend

#### Flags for Specific Fields

Flags|Description
---|-----------
n/|Name
s/|Status (as a full-time/part-time worker)
r/|Role (e.g. kitchen, floor)
a/|Address
$/|Salary
i/|Index
e/|Email
t/|Extra tags
d/|Date (for mark command) and Shift (for shift related commands)

#### Flags for Lookup

Flags|Name
---|----
-n|Name
-i|Index
-d|Day Of the Week
-t|Tag
-ti|Time



<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>

  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend` (i.e 1 times), `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extra parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Utility Features

#### Viewing help : `help`

Shows a message explaning how to access the help page.

Format: `help`

![help message](images/helpMessage.png)

#### Listing all persons : `list`

Shows a list of all staffs in the staff view.

![staff view](images/staff_view.png)

Format: `list`

#### Saving the data

Staff'd data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

#### Editing the data file

Staff'd data are saved as a JSON file. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Staff'd will discard all data and start with an empty data file at the next run.
</div>


### Basic management of Staff Details

#### Viewing a staff : `view`

Displays a staff by a specific lookup of the fields (e.g. name, tags, roles, email, address) of a staff or by index of the staff.

 * Does not allow for a non-specific lookup. Any fields entered must be an exact reference.
 * For example, the query `-n Candice` will result in any Staff with the exact name "Candice" and no-one else.

Format:  
`view [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-$ SALARY] [-s STATUS] [-r ROLE]... [-t TAG]...`

Examples:  
`view -n Candice`  
`view -t friends -t neighbours`  

#### Adding a staff : `add`

Adds a staff to the system. 

 * Staff of the same name cannot be added.
 * The tags and information are optional and can be presented in any order.
 * Upon creation of a staff, the system creates an index for them which can be used to refer to them and access the system.

Format:  
`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS $/SALARY [s/STATUS] [r/ROLE]... [t/TAG]...`  

Examples:  
`add n/Joe s/fulltime r/kitchen p/98765432 $/1234789 e/Joe@example.com a/John street, block 123, #01-01`  
`add n/Candice s/parttime p/91234567 $/2 e/candice@example.com a/Newgate Prison`



#### Marking a staff as absent : `mark`

Marks a specified staff(s) as not working for a specified date.

 * The salary for that date will be not included in calculation,
depending on the staff's status. By default, the staff is recorded
as present for all shifts.
 * The format of the input date is in: `YYYY-MM-DD`.

Format:  

Marking a period:  

`mark [-i INDEX] [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-$ SALARY] [-s STATUS] [-r ROLE]... d/DATE [d/END DATE]`  


Possible to mark a single date:

`mark -t TAG d/DATE`  
`mark -n NAME d/DATE`

Examples:  
`mark -i 1 d/2020-01-03 d/2021-01-03`  
`mark -n Alex Yeoh d/2020-01-03`


#### Removing the absent mark : `unmark`

Removes the period that was marked by the `mark` command.

The format of the input date is in: `YYYY-MM-DD`.

Format:  
`unmark [-i INDEX] [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-$ SALARY] [-s STATUS] [-r ROLE]... d/DATE [d/END DATE]`

Examples:  
`unmark -i 1 d/2020-01-03 d/2021-01-03`  
`unmark -t friends d/2020-01-03`

#### Deleting a Staff : `delete`

Deletes the specified staff `Staff'd`.

* Deletes the staff(s) with the specified `NAME`, `ROLE`, `STATUS`, `INDEX`.
* The index refers to the index number shown in the displayed staff list. It **must be a positive integer** 1, 2, 3, within the range of the staff view.

Formats:  
`delete -n NAME`  
`delete -i INDEX`  
`delete -r ROLE`  
`delete -s STATUS`  

Examples:  
`delete -n Candice`  
`delete -i 2`  
`delete -r cashiers`  
`delete -s fulltime`


![delete result](images/DeleteResult.png)

#### Editing a staff : `edit`

Edits an existing staff in the Staff List.

Formats:  
`edit -n NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [$/SALARY] [s/STATUS] [r/ROLE]... [t/TAG]...`  
`edit -i INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [$/SALARY] [s/STATUS] [r/ROLE]... [t/TAG]...`


* Edits the staff of the specified `NAME`, `INDEX`.
The index refers to the index number shown in the displayed staff list. The index **must be a positive integer** 1, 2, 3, … within the range of staff list.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:  
`edit -i 1 p/91234567 e/johndoe@example.com`  
`edit -n Bob p/69696969 e/candicepleasedateme@tinder.com`  
`edit -n Candice r/cook`


#### Finding staff : `find`

Finds staff whose names contain any of the given keywords, or by their index in the staff list.

Name Search:

* The search is case-insensitive. e.g `bob` will match `Bob`.
* The order of the keywords does not matter. e.g. `Candice Dee` will match `Dee Candice`.
* Only full words will be matched e.g. `Boba` will not match `Bob`.
* Staff matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `John Nathan` will return `John Wick`, `Nathan Tan`.

Index Search:

* If previous searches have been made, the search is conducted on the displayed list. Otherwise, it will
  be performed on the overall staff list.
* The index must be within range. (i.e. from 1 until the size of the Staff List)
* If the list is empty, no input is accepted.
* Only single search is supported, and this search will return only the specific Staff at that index.

Format:  
`find -n KEYWORD [MORE_KEYWORDS]`  
`find -i INDEX`  


Examples:  
`find -n John`  
`find -n alex david`    
`find -i 3`  

List before using Find command:
![List before using the find command](images/findCommand/BeforeFindCommand.jpg)

List after using Find command:
![List after find command is called](images/findCommand/AfterFindCommand.jpg)


#### Clearing all entries : `clear`

Clears all entries from the Staff List.

Format: `clear`

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Basic Management of Staff Schedules

#### Viewing schedule of staff(s): `viewSchedule`

Views the schedule of staff's that satisfy the query conditions.


Formats:  
`viewSchedule [-n NAME] [-i INDEX] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-$ SALARY] [-s STATUS] [-r ROLE]... [-t TAG]...`

Examples:  
`viewSchedule -n Candice`  
`viewSchedule -i 123`

The output will look like the following.

![viewShedule](images/viewScheduleImage.png)




#### Viewing all the staff(s) working a shift : `viewShift`

Finds all the staff working at a particular shift. The shift can be specified either by detailing the day of the week and the time, or the day of the week and slot number.

* When using the -ti flag, it is in 24-hour format. Example, for 4.pm on wednesday, we use <br> `wednesday-16:00`.
* The DAY entry is not case sensitive.

Formats:  
`viewShift -d DAY-shift_number`  
`viewShift -ti DAY-HH:mm`

Note that day refers to the day of the week, and it is case-insensitive. However, it should be spelt in full (e.g. MONDAY instead of Mon).

Examples:  
`viewShift -d monday-1`  
`viewShift -d TUESDAY-0`  
`viewShift -ti wednesday-12:00`  
`viewShift -ti THURSDAY-16:30`

Demonstration:  
![Example of ViewShiftCommand](images/viewShiftCommand/viewShift.jpg)

#### Adding a shift to a staff's schedule : `addShift`

Adds a time period where the staff is working to the staff’s schedule.

* There are two ways to identify the staff to add the time period to: by their `name` or by their staff `index`.
* The `fulldayname` field required to specify shifts are not case sensitive.
* The start time and end time will be set to the default one (If it's a morning slot, then the period of shift is from
  10:00 to 16:00; If it's an afternoon slot, then the period of shift is 16:00 to 22:00).

Formats:  
`addShift -n name d/fullDayName-shiftNumber`  
`addShift -i index d/fullDayName-shiftNumber`


Examples:  
`addShift -n Candice d/Monday-1`   
`addShift -i 1234 d/tuesday-0`

### Updating start time and end time for a shift

Updates the start time and end time of a specific shift of a specific staff.

Formats:\
`setShiftTime -n name d/fullDayName-shiftNumber st/hh:mm-hh:mm`
`setShiftTime -i index d/fullDayName-shiftNumber st/hh:mm-hh:mm`

* Start time and end time must follow the format (hh:mm).
* Start time must be earlier than end time.
* Both start time and end time must be within the bound (10:00-16:00 for morning slot, 16:00-22:00 for afternoon slot).
* If the shift does not exist in the staff's schedule, it will be created.

Examples:\
`setShiftTime -n Candice d/Monday-0 st/10:30-12:30`
`setShiftTime -i 12 d/tuesday-1 st/17:00-21:30`


#### Deleting a shift from a staff : `deleteShift`

Deletes a time period from the staff schedule.  There are two ways to identify the staff to delete the time period from: by their `name` or by their staff `index`. The deleted period must be the same as a period previously entered by the manager.

Formats:  
`deleteShift -n NAME d/fullDayName-shiftNumber`  
`deleteShift -i INDEX d/fullDayName-shiftNumber`

Examples:  
`deleteShift -n Joe d/tuesday-2`  
`deleteShift -i 1278 d/friday-1`

#### Editing a staff schedule : `editSchedule`
Edits a staff schedule start and end date time. There are two ways to identify the staff who’s schedule will be edited: by their name or by their staff ID.

Formats:  
`editSchedule n/name old/fullDayName-shiftNumber new/fullDayName-shiftNumber`  
`editSchedule id/ID old/fullDayName-shiftNumber new/fullDayName-shiftNumber`

Examples:  
`editSchedule n/Candice old/tuesday-1 new/tuesday-2`  
`editSchedule n/12345678 old/wednesday-2 new/thursday-2`  

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**View** | `view [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-$ SALARY] [-s STATUS] [-r ROLE]... [-t TAG]...`
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS $/SALARY [s/STATUS] [r/ROLE]... [t/TAG]...`
**Delete** | `delete -n NAME` <br> `delete -i INDEX` <br> `delete -r role` <br> `delete -s STATUS`
**Edit** | `edit -n NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [$/SALARY] [s/STATUS] [r/ROLE]... [t/TAG]...` <br> `edit -i INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [$/SALARY] [s/STATUS] [r/ROLE]... [t/TAG]...`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**View staff schedule** | `viewSchedule [-n NAME] [-i INDEX] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-$ SALARY] [-s STATUS] [-r ROLE]... [-t TAG]...`
**Add staff to shift** | `addShift -n NAME d/DAY-SHIFTNUMBER` <br> `addShift -i INDEX d/DAY-SHIFTNUMBER`
**Set shift time** | `setShiftTime -n NAME d/FULLDAYNAME-SHIFTNUMBER st/hh:mm-hh:mm` <br> `setShiftTime -i INDEX d/FULLDAYNAME-SHIFTNUMBER st/hh:mm-hh:mm`
**Delete staff shift** | `deleteShift -n NAME d/DAY-SHIFTNUMBER` <br> `deleteShift -i INDEX d/DAY-SHIFTNUMBER`
**View shift** | `viewShift -d DAY-SHIFTNUMBER` <br> `viewShift -ti DAY-HH:mm`
**Mark absent** | `mark [-i INDEX] [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-$ SALARY] [-s STATUS] [-r ROLE]... d/DATE [d/END DATE]`
**Remove mark** | `unmark [-i INDEX] [-n NAME] [-p PHONE] [-e EMAIL] [-a ADDRESS] [-$ SALARY] [-s STATUS] [-r ROLE]... d/DATE [d/END DATE]`
**List** | `list`
**Help** | `help`
**Clear** | `clear`
**Exit** | `exit`

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Staff'd home folder.

**Q**: Where is the save file located?<br>
**A**: In the data folder in the _home folder_ called _addressbook.json_.
