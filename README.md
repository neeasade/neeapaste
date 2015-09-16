## neeapaste

~in active development~

A pastebin implementation for me to practice Java, Spring, Gradle, JDBC.

### TODO
- [x] allow pastes
- [x] database persistence
- [ ] allow users
- [ ] allow pastes to be owned by users
- [ ] https/SSL
- [ ] continuous integration

Reference API below for status there.

### Running
Assuming you have java and javac in your path:

windows: `gradlew.bat bootRun`

linux/osx: `gradlew bootRun`

### Definitions
A **Paste** here has the following properties: Id, Title, Content, Views.
- Getting the value of the content property will increment the view property.

A **User** here has the following properties: Id, username, password(hashed when stored), Pastes
- A user may own a number of pastes.

### API
GET		| result |	status
--------|--------|---
/paste/all	| all pastes and info in json format | yes
/paste/$id	| paste and information in json format | yes
/paste/$id/$property | paste property value | yes
/paste/search?q=$text	| return all pastes in with matching $text in title or content in json format. | yes
/paste/$user/ | pastes own by a $user. | no

POST to /paste params	| expected | status
------------------------|----------|----
title(default 'Untitled')   | title of a paste. | yes
content(default 'none') | content of a paste. | yes
user(optional)			| user who owns the paste | no
pass(optional)			| password for user making post request. | no

### Database layout
table 'paste_users':

username | password(hashed) | id
------|-----|-----

table 'pastes':

title | content | views | id
------|-----|-----|-----


table 'paste_relates':

user id(foreign key) | paste id(foreign key)
------|-----

