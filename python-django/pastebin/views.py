from django.shortcuts import render
from django.http import HttpResponse
from pastebin.models import Paste, User
import json

def index(request):
    return HttpResponse("Index view placeholder.")

def allpastes(request):
    pastes=[]
    for paste in Paste.objects.all():
        pastes.append(paste.data())
    return HttpResponse(json.dumps(pastes))
