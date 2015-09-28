from django.shortcuts import render
from django.http import HttpResponse
from pastebin.models import Paste, User
import json

def index(request):
    return HttpResponse("Index view placeholder.")

def allPastes(request):
    pastes=[]
    for paste in Paste.objects.all():
        pastes.append(paste.data())
    return HttpResponse(json.dumps(pastes))

def findPasteProperty(request, index, prop):
    paste = Paste.objects.filter(id=index)[0]
    return HttpResponse(getattr(paste, prop))

def findPaste(request, index):
    paste = Paste.objects.filter(id=index)[0]
    return HttpResponse(json.dumps(paste.data()))
