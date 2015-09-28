from django.conf.urls import url

from . import views

urlpatterns = [
        url(r'^$', views.index, name='index'),

        #Views for viewing pastes and paste properties
        url(r'^paste/(?P<index>[0-9]+)/$', views.findPaste),
        url(r'^paste/(?P<index>[0-9]+)/(?P<prop>[a-z]+)/$', views.findPasteProperty),
        url(r'^paste/all/', views.allPastes),
        url(r'^paste/search', views.searchPastes),

        # POST views for creating pastes/Users
        url(r'^paste/$', views.createPaste),
        url(r'^paste$', views.createPaste),
        url(r'^user/create/$', views.createUser),
        url(r'^user/create$', views.createUser),
]
