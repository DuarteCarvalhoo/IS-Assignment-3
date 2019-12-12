from django.urls import path, re_path
from . import views

urlpatterns=[
    re_path('listcountries',views.list_database_countries),
]