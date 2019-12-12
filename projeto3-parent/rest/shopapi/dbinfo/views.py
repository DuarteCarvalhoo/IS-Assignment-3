from django.shortcuts import render

from .models import Country, Item

from django.http import *
from django.views.decorators.csrf import csrf_exempt

# Create your views here.

@csrf_exempt
def add_country(request):
    country = Country("3","japao")
    country.save()
    return HttpResponse()