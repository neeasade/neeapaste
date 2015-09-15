##neeapaste

A pastebin to help me understand different technologies. Currently the Spring framework and Gradle, but may branch out.

goals/spec(probably going to change):

GET		| result |	status
--------|--------|---
/paste/all	| all pastes and info in json format | [x]
/paste/$id	| paste and information in json format | [x]
/paste/$id/content	| paste content | [ ]
/paste/search/$text	| return all pastes in with matching $text in title or content in json format. | [x]
/paste/$user/ | pastes own by a $user. | [ ]

POST to /paste params	| expected.| status
------------------------|----------------------|--
title					| title of a paste. | [x]
content					| content of a paste. | [x]
user(optional)			| user who owns the paste | [  ]
pass(optional)			| password for user making post request. | [  ]

