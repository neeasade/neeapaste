from django.shortcuts import render
from django.http import HttpResponse
from pastebin.models import Paste, User
from django.views.decorators.csrf import csrf_exempt
import json
from django.contrib.auth.hashers import make_password, check_password

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

    lUsername = request.POST.get('username')
    lPassword = request.POST.get('password')
    lTitle = request.POST.get('title')
    lContent = request.POST.get('content')
    if lTitle == None:
        lTitle = "Untitled"

    p = Paste(content = lContent, title = lTitle)

    if lUsername != None and lPassword != None:
        if User.objects.filter(username=lUsername).count() != 0:
            lUser = User.objects.filter(username = lUsername)[0]
            if check_password(lPassword, lUser.password):
                p.owner = lUser
            else:
                return HttpResponse("Authentification failed:")
        else:
            return HttpResponse("User does not exist.")

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

@csrf_exempt
def ownedPastes(request, aUsername):
    if (aUsername == "create"):
        return createUser(request)

    lPastes=[]
    lUser=User.objects.filter(username=aUsername)[0]
    for paste in Paste.objects.filter(owner=lUser):
        lPastes.append(paste.data())
    return HttpResponse(json.dumps(lPastes))
