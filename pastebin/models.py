from django.db import models

# Create your models here.

class User(models.Model):
    username = models.CharField(max_length=40)
    password = models.CharField(max_length=40)

class Paste(models.Model):
    content = models.CharField(max_length=65535)
    title = models.CharField(max_length=30)
    views = models.IntegerField(default=0)
    owner = models.ForeignKey(User)
    def __str__(self):
        return "[" + self.title + "] " + self.content

