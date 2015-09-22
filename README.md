## neeapaste
![build status](https://api.travis-ci.org/neeasade/neeapaste.svg?branch=master)

~in active development~

A pastebin implementation for me to practice Java, Spring, Gradle, ~~JDBC~~jpa/hibernate.

### TODO
- [x] allow pastes
- [ ] database persistence
- [x] allow users
- [x] allow pastes to be owned by users
- [ ] https/SSL
- [x] continuous integration
- [ ] testing/code coverage
- [x] store passwords in not plain text

Reference API below for status there.

### Running
Assuming you have java and javac in your path(1.8):

windows: `gradlew.bat bootRun`

linux/osx: `gradlew bootRun`

The pastebin will then be hosted on http://localhost:8080/.

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
/search?q=$text	| return all pastes in with matching $text in title or content in json format. | yes
/user/$user/ | pastes own by a $user. | yes

POST | params | status
------------------------|----------|----
/paste | title(default 'Untitled')(optional) | yes
^      | content(required) |
^      | user(optional) |
^      | pass(optional, paired with user) |
/user/create | user | yes
^            | pass |

### Database layout
Refer to User and Paste Entites in src.
