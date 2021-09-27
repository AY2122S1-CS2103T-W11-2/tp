---
layout: page
title: User Guide
---

Staff’d helps F&B managers manage details and schedules of their staff. It is optimized for CLI users so that frequent tasks can be done faster by typing in commands.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start - `to ammend`

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `addressbook.jar` from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

   * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Staff List.

   * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

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
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

## Basic management of Staff Details

### View a staff

View the staff details of a single staff.

Example:\
`view n/Candice`\
`view id/12345678`

### Adding a staff - `add`

Adds a staff to the stored information. The tags and information are optional and can be presented in any order.
Upon creation of a staff, the system creates a staff ID for them which can be used to refer to them and access the system.

Format:\
`add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS t/Tag`

Example:\
`add n/Joe s/fulltime r/manager p/98765432 e/Joe@example.com a/John street, block 123, #01-01`\
`add n/Candice s/parttime p/91234567 e/candice@example.com a/Newgate Prison`

### Tag legend - `todo: standardize tags for all commands`

|Tag|Description|
|---|-----------|
|n/|Name of Staff|
|s/|Status (as a full-time/part-time worker)|
|r/|Role (e.g. Cook, Staff Management)|
|e/|Email|
|a/|Address of Staff|



### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


[comment]: <> (### Adding a person: `add`)

[comment]: <> (Adds a person to the address book.)

[comment]: <> (Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`)

[comment]: <> (<div markdown="span" class="alert alert-primary">:bulb: **Tip:**)

[comment]: <> (A person can have any number of tags &#40;including 0&#41;)

[comment]: <> (</div>)

[comment]: <> (Examples:)

[comment]: <> (* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`)

[comment]: <> (* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`)

### Listing all persons : `list`

Shows a list of all staffs in the staff list.

Format: `list`

### Editing a staff : `edit`

Edits an existing staff in the Staff List.

Format:\
`edit NAME [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]`
`edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]`\
`edit ID [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]`


* Edits the staff of the specified `NAME`, `INDEX`, or `ID`.
The index refers to the index number shown in the displayed staff list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the staff will be removed i.e adding of tags is not cumulative.
* You can remove all the staff’s tags by typing `t/` without specifying any tags after it.

Examples:\
`edit -i 1 p/91234567 e/johndoe@example.com`\
`edit -n Bob p/69696969 e/candicepleasedateme@tinder.com`\
`edit -n Candice p/12345678 e/noBOByouaretoochubby@tafclub.com`\
`edit -id 12345678 n/Candice p/12345678 e/nobobyouaretoofat@tafclub.com`

|Tag|Name|Description|
|---|----|-----------|
|-n|Name|Contacts with the name will be edited.|
|-i|ID|Contact with that id will be edited.|
|-t|Tag|Contacts with that tag will be edited.|

### Adding a staff's schedule: `addSchedule`

Adds a time period where the staff is working to the staff’s schedule.

Format: 
* `addSchedule [n/name] [day-startTime-endTime]`
* `addSchedule [id/ID] [day-startTime-endTime]`
* `addSchedule [n/name] [t/nonrecurr] [d/date] [startTime-endTime]`
* `addSchedule [id/ID] [t/nonrecurr] [d/date] [startTime-endTime]`

Note:
* There are two ways to identify the staff to add the time period to: by their name or by their staff ID.
* Also, there are two types of time periods that can be added - recurring and non-recurring.
* The default is a recurring schedule which will occur every week. For the addition of the non-recurring time periods, 
  the date which the staff works has to be specified.

Examples:
* `addSchedule n/Candice Mon-0800-1200`
* `addSchedule id/12345678 t/nonrecurr d/2021-09-09 0800-1200`

### View a staff schedule : `viewSchedule`

Views a specific staff’s schedule.

Format: 
* `viewSchedlue [n/name]`
* `viewSchedlue [id/ID]`

Examples:
* `viewSchedule n/Candice`
* `viewSchedule id/12345678`

### Locating persons by name: `find

Finds staff whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Staff matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a Staff : `delete`

Deletes the specified staff from the staff list.

Format: `delete INDEX`

* Deletes the staff(s) with the specified `NAME`, `TAG`, `INDEX`.
* The index refers to the index number shown in the displayed staff list. It **must be a positive integer** 1, 2, 3, …​
* If there are multiple staffs of the same name/tag group, Staff'd will prompt you again to be more specific.

Examples:\
`delete -n Candice`\
`delete -i 12345678`\
`delete -t {group}`

[comment]: <> (* `list` followed by `delete 2` deletes the 2nd staff in the staff list.)
[comment]: <> (* `find Betsy` followed by `delete 1` deletes the 1st staff in the results of the `find` command.)

|Tag|Name|Description|
|---|----|-----------|
|-n|Name|Contacts with the name will be deleted.|
|-i|ID|Contact with that id will be deleted.|
|-t|Tag|Contacts with that tag will be deleted.|

### Clearing all entries : `clear`

Clears all entries from the Staff List.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

Staff'd data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

Staff'd data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
