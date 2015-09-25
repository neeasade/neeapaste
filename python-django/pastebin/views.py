from django.shortcuts import render
from django.http import HttpResponse
from pastebin.models import Paste, User

def index(request):
    return HttpResponse("Index view placeholder.")

def paste(request):
     if request.method == 'GET':
        # Get paste model and return tostring
        return Paste.objects.all()
