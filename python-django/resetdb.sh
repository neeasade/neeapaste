rm ./db.sqlite3
python ./manage.py makemigrations pastebin
python ./manage.py migrate
