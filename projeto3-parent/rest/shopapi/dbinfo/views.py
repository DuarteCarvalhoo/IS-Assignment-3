from django.shortcuts import render

from django.http import *
from django.views.decorators.csrf import csrf_exempt

# Create your views here.

@csrf_exempt
def add_country(request):
    country = Instance("3","japao","country")
    country.save()
    return HttpResponse()

@csrf_exempt
def add_item(request):
    item = Instance("1","pen","item")
    item.save()
    return HttpResponse()