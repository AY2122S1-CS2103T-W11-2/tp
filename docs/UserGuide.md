---
layout: page
title: User Guide
---

Staff’d helps F&B managers manage details and schedules of their staff. It is optimized for CLI users so that frequent tasks can be done faster by typing in commands.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Copy the file to the folder you want to use as the _home folder_ for your Staff’d.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/NewUi.jpg)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>

  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

## Basic management of Staff Details


### Tag legend

#### Tags for Specific Fields

|Tag|Description|
|---|-----------|
|n/|Name|
|s/|Status (as a full-time/part-time worker)|
|r/|Role (e.g. Cook, Staff Management)|
|a/|Address|
|$/|Salary|
|i/|Index|
|e/|Email|
|t/|Extra tags|

#### Tags for Lookup

|Tag|Name|
|---|----|
|-n|Name|
|-i|Index|
|-d|Day Of the Week|
|-t|Time|


### View a staff - `view`

View the staff details of a single staff.

Examples:\
`view n/Candice`\
`view i/123`

### Adding a staff - `add`

* Adds a staff to the system. The tags and information are optional and can be presented in any order.
* Upon creation of a staff, the system creates an index for them which can be used to refer to them and access the system.

Format:\
`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS $/SALARY [s/STATUS] [r/ROLE]... [t/TAG]...`

Examples:\
`add n/Joe s/fulltime r/manager p/98765432 $/1234789 e/Joe@example.com a/John street, block 123, #01-01`\
`add n/Candice s/parttime p/91234567 $/2 e/candice@example.com a/Newgate Prison`

### Listing all persons : `list`

Shows a list of all staffs in the staff list.

Format: `list`

### Marking a staff as absent : `mark`

Marks a specified staff(s) as not working for a specified date.
The salary for that date will be not included in calculation,
depending on the staff's status. By default, the staff is recorded
as present for all shifts.

The format of the input date is in: `YYYY-MM-DD`

Format:\
`mark -i index d/startDate d/endDate`\
`mark -n name d/startDate d/endDate`\
`mark -t tag d/startDate d/endDate`

Possible to mark a single date `mark t/tag d/date`

Examples:\
`mark -i 1 d/2020-01-03 d/2021-01-03`\
`mark -n Alex Yeoh d/2020-01-03`


### Removing the absent mark `unmark`

Removes the period that was marked by the `mark` command.

The format of the input date is in: `YYYY-MM-DD`

Format:\
`unmark -n name d/startDate d/endDate`\
`unmark -i index d/startDate d/endDate`  

Examples:\
`unmark -i 1 d/2020-01-03 d/2021-01-03`\
`unmark -t friends d/2020-01-03`

### Deleting a Staff : `delete`

Deletes the specified staff from the staff list.

Formats:\
`delete -n NAME`\
`delete -i INDEX`\
`delete -r ROLE`\
`delete -s STATUS`

* Deletes the staff(s) with the specified `NAME`, `ROLE`, `STATUS`, `INDEX`.
* The index refers to the index number shown in the displayed staff list. It **must be a positive integer** 1, 2, 3, …​

Examples:\
`delete -n Candice`\
`delete -i 2`\
`delete -r cashiers`\
`delete -s fulltime`

[comment]: <> (* `list` followed by `delete 2` deletes the 2nd staff in the staff list.)
[comment]: <> (* `find Betsy` followed by `delete 1` deletes the 1st staff in the results of the `find` command.)

### Editing a staff : `edit`

Edits an existing staff in the Staff List.

Formats:\
`edit -n NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [$/SALARY] [s/STATUS] [r/ROLE]... [t/TAG]...`\
`edit -i INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [$/SALARY] [s/STATUS] [r/ROLE]... [t/TAG]...`


* Edits the staff of the specified `NAME`, `INDEX`
The index refers to the index number shown in the displayed staff list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:\
`edit -i 1 p/91234567 e/johndoe@example.com`\
`edit -n Bob p/69696969 e/candicepleasedateme@tinder.com`\
`edit -n Candice r/cook`


### Finding staff: `find`

Finds staff whose names contain any of the given keywords, or by their index in the staff list.

Format:\
`find -n KEYWORD [MORE_KEYWORDS]`
`find -i INDEX`

Name Search:
* The search is case-insensitive. e.g `bob` will match `Bob`
* The order of the keywords does not matter. e.g. `Candice Dee` will match `Dee Candice`
* Only full words will be matched e.g. `Boba` will not match `Bob`
* Staff matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `John Nathan` will return `John Wick`, `Nathan Tan`

Index Search:
* If previous searches have been made, the search is conducted on the displayed list. Otherwise, it will
  be performed on the overall staff list.
* The index must be within range (i.e. from 1 until the size of the Staff List, or trivially 0
  if the Staff List is empty)
* Only single search is supported, and this search will return only the specific Staff at that index

Examples:
* `find -n John` returns `john` and `John Doe`
* `find -n alex david` returns `Alex Yeoh`, `David Li`
* `find -i 3` returns the staff at the 3rd position on the list

List before using Find command:
  ![List before using the find command](images/findCommand/BeforeFindCommand.jpg)

List after using Find coma
![List after find command is called](images/findCommand/AfterFindCommand.jpg)


### Clearing all entries : `clear`

Clears all entries from the Staff List.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

## Basic Management of Staff Schedules

### Adding a shift to staff's schedule: `addShift`

Adds a time period where the staff is working to the staff’s schedule.

Formats:\
`addShift -n name d/fullDayName-shiftNumber` \
`addShift -i index d/fullDayName-shiftNumber`


* There are two ways to identify the staff to add the time period to: by their `name` or by their staff `index`.
* The `fulldayname` field required to specify shifts are not case sensitive.

Examples:\
`addShift -n Candice d/Monday-1` \
`addShift -i 1234 d/tuesday-0`

### View a staff schedule : `viewSchedule`

Views a specific staff’s schedule.

Formats:\
`viewSchedule -n name` \
`viewSchedule -i index`

Examples:\
`viewSchedule -n Candice` \
`viewSchedule -i 123`


### Deleting a staff schedule: `deleteSchedule`

Deletes a time period from the staff schedule.  There are two ways to identify the staff to delete the time period from: by their `name` or by their staff `index`. The deleted period must be the same as a period previously entered by the manager.

Formats:\
`deleteSchedule -n NAME d/fullDayName-shiftNumber` \
`deleteSchedule -i INDEX d/fullDayName-shiftNumber`

Examples:\
`deleteSchedule -n Joe d/tuesday-2` \
`deleteSchedule -i 1278 d/friday-1`

### Swapping shifts: `swapShift`
Swaps shifts between 2 staffs. The 2 staffs are identified using their names.

Formats:  
`swapShift -n NAME -n NAME d/day-shift_number d/day-shift_number`  
`swapShift -n NAME d/day-shift_number -n NAME d/day-shift_number`

Examples:  
`swapShift -n Candice -n Bob d/monday-0 d/tuesday-1`  
`swapShift -n Candice d/monday-0 -n Bob d/tuesday-1`

Note:
* The staff identified using the first name is associated with the first shift and the staff identified using the second name is associated with the second shift.
* This means that you can permute the order of the parameters in any way but the rule mentioned right above will always be followed.

### View all the staff working a shift: `viewShift`

Finds all the staff working at a particular shift. The shift can be specified either by detailing the day of the week and the time, or the day of the week and slot number.

Formats:\
`viewShift -d day-shift_number`\
`viewShift -t day-HH:mm` (Note that this is in 24-hour format)\
`viewShift` [This also displays the staff working during the current shift]

Note that day refers to the day of the week, and it is case-insensitive. However, it should be spelt in full (e.g. MONDAY instead of Mon).

Examples:\
`viewShift -d monday-1`\
`viewShift -d TUESDAY-0`\
`viewShift -t wednesday-12:00`\
`viewShift -t THURSDAY-16:30`\

Demonstration:\
![Example of ViewShiftCommand](images/viewShiftCommand/viewShift.jpg)


### Saving the data

Staff'd data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Staff'd data are saved as a JSON file. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, Staff'd will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous Staff'd home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
-------|------------------
**View** | `view -n NAME` <br> `view -i INDEX`
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS $/SALARY [s/STATUS] [r/ROLE]... [t/TAG]...`
**Delete** | `delete -n NAME` <br> `delete -i INDEX` <br> `delete -r role` <br> `delete -s STATUS`
**Edit** | `edit -n NAME [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [$/SALARY] [s/STATUS] [r/ROLE]... [t/TAG]...` <br> `edit -i INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [$/SALARY] [s/STATUS] [r/ROLE]... [t/TAG]...`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Add staff schedule** | `addShift -n NAME d/fullDayName-shiftNumber` <br> `addShift -i INDEX d/fullDayName-shiftNumber`
**View staff schedule** | `viewSchedlue -n NAME` <br> `viewSchedlue -i INDEX`
**Edit staff schedule** | `editShift n/NAME old/fullDayName-shiftNumber new/fullDayName-shiftNumber` <br> `editShift i/INDEX old/fullDayName-shiftNumber new/fullDayName-shiftNumber`
**Delete staff shift** | `deleteShift -n NAME d/fullDayName-shiftNumber` <br> `deleteShift -i INDEX d/fullDayName-shiftNumber`
**View shift** | `viewShift -d day-shift_number` <br> `viewShift -t day-HH:mm`
**Swap shifts** | `swapShift -n NAME -n NAME d/day-shift_number d/day-shift_number` <br> `swapShift -n NAME d/day-shift_number -n NAME d/day-shift_number`
**Mark absent** | `mark -i INDEX d/startDate [d/endDate]` <br> `mark -n NAME d/startDate [d/endDate]`
**Remove mark** | `unmark -i INDEX d/startDate [d/endDate]` <br> `mark -n NAME d/startDate [d/endDate]`
**List** | `list`
**Help** | `help`
**Clear** | `clear`
**Exit** | `exit`
