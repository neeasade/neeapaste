from django.shortcuts import render
from django.http import HttpResponse
from pastebin.models import Paste, User
from django.views.decorators.csrf import csrf_exempt
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

@csrf_exempt
def makePaste(request):
    #assumes that request is a post, and that content is not null.
    ltitle=request.POST.get('title')
    lcontent=request.POST.get('content')
    if ltitle == None:
        ltitle = "Untitled"
    p=Paste(content = lcontent, title = ltitle)
    p.save()
    return HttpResponse("http://localhost:8000/paste/" + str(p.id) + "\n")
