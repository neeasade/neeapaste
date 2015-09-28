from django.shortcuts import render
from django.http import HttpResponse
from pastebin.models import Paste, User
from django.views.decorators.csrf import csrf_exempt
import json
from django.contrib.auth.hashers import make_password

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

# Method to create a paste - expects post.
@csrf_exempt
def createPaste(request):
    if request.method == 'GET':
        return HttpResponse("This address is meant for POST requests.\n")

    lUser = request.POST.get('user')
    lPassword = request.POST.get('password')
    lTitle = request.POST.get('title')
    lContent = request.POST.get('content')
    if lTitle == None:
        lTitle = "Untitled"

    p = Paste(content = lContent, title = lTitle)

    if lUser != None and lPassword != None:
        lUser = User.objects.filter(username=lUsername)[0]
        if lUser != Null:
            if check_password(lUser.password, lPassword):
                p.owner = lUser.id

    p.save()
    return HttpResponse("http://localhost:8000/paste/" + str(p.id) + "\n")

# Method to create a User - expects post.
@csrf_exempt
def createUser(request):
    if request.method == 'GET':
        return HttpResponse("This address is meant for POST requests.\n")

    lUsername = request.POST.get('username')
    lPassword = request.POST.get('password')

    if User.objects.filter(username=lUsername).count() != 0:
        return HttpResponse("User exists.\n")

    lHashPass = make_password(lPassword)
    lUser = User(username = lUsername, password = lHashPass)
    lUser.save()
    return HttpResponse("User Created.\n")

def searchPastes(request):
        return HttpResponse("stub.")


