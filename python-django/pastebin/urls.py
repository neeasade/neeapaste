from django.conf.urls import url

from . import views

urlpatterns = [
        url(r'^$', views.index, name='index'),
        url(r'^paste/(?P<index>[0-9]+)/$', views.findPaste),
        url(r'^paste/(?P<index>[0-9]{1})/(?P<prop>[a-z]+)/$', views.findPasteProperty),
        url(r'^paste/all/', views.allPastes),
        url(r'^paste/$', views.makePaste),
]
