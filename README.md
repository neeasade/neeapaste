##neeapaste

A pastebin to help me understand different technologies. Currently the Spring framework and Gradle, but may branch out.

goals/spec(probably going to change):

GET		| result
--------|-------
/paste	| all pastes and info in json format
/paste/$id	| paste and information in json format
/paste/$id/content	| paste content
/paste/search/$text	| return all pastes in with matching $text in title or content in json format.
/paste/$user/ | pastes own by a $user.

POST to /paste params	| expected.
------------------------|----------------------
content					| content of a paste.

