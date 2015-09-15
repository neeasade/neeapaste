##neeapaste

A pastebin to help me understand different technologies. Currently the Spring framework and Gradle, but may branch out.
This is not complete and in flux.

Running on windows:
`gradlew.bat bootRun`

Running on linux/osx(bash):
`gradlew bootRun`

goals/spec(probably going to change):

A paste here has the following properties: Id, Title, Content, Views.
Getting the value of the content property will increment the view property.

Pastes may be owned by a user, but this will not be a property of the paste itself. However, you may see all pastes made by a user.

GET		| result |	status
--------|--------|---
/paste/all	| all pastes and info in json format | yes
/paste/$id	| paste and information in json format | yes
/paste/$id/$property | paste property value | yes
/paste/search/$text	| return all pastes in with matching $text in title or content in json format. | yes
/paste/$user/ | pastes own by a $user. | no

POST to /paste params	| expected | status
------------------------|----------|----
title(default 'Untitled')   | title of a paste. | yes
content(default 'none') | content of a paste. | yes
user(optional)			| user who owns the paste | no
pass(optional)			| password for user making post request. | no

###database layout
User table
- Username
- hashed password
- id

Paste table
- title
- content
- views
- id

Relation table
- map foreign keys(user id to paste id)



