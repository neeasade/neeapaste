from django.shortcuts import render
from django.http import HttpResponse
from pastebin.models import Paste, User

def index(request):
    return HttpResponse("Index view placeholder.")

def paste(request):
    return Paste.objects.all()
