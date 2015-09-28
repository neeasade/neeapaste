from django.conf.urls import url

from . import views

urlpatterns = [
        url(r'^$', views.index, name='index'),
        url(r'^paste/', views.allpastes, name='paste'),
        url(r'^paste/all/', views.allpastes, name='paste'),
]
