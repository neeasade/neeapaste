##neeapaste

A pastebin to help me understand different technologies. Currently the Spring framework and Gradle, but may branch out.

goals/spec(probably going to change):

GET		| result |	status
--------|--------|---
/paste/all	| all pastes and info in json format | yes
/paste/$id	| paste and information in json format | yes
/paste/$id/content	| paste content | no
/paste/search/$text	| return all pastes in with matching $text in title or content in json format. | yes
/paste/$user/ | pastes own by a $user. | no

POST to /paste params	| expected | status
------------------------|----------|----
title					| title of a paste. | yes
content					| content of a paste. | yes
user(optional)			| user who owns the paste | no
pass(optional)			| password for user making post request. | no

