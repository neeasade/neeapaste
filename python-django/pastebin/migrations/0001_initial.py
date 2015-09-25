# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Paste',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, verbose_name='ID', serialize=False)),
                ('content', models.CharField(max_length=65535)),
                ('title', models.CharField(max_length=30)),
                ('views', models.IntegerField(default=0)),
            ],
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, verbose_name='ID', serialize=False)),
                ('username', models.CharField(max_length=40)),
                ('password', models.CharField(max_length=40)),
            ],
        ),
        migrations.AddField(
            model_name='paste',
            name='owner',
            field=models.ForeignKey(to='pastebin.User'),
        ),
    ]
