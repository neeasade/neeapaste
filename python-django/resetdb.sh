rm ./db.sqlite3
rm ./pastebin/migrations/*.py
python ./manage.py makemigrations pastebin
python ./manage.py migrate
