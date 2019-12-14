from django.shortcuts import render

from .models import Instance

from django.http import *
from django.views.decorators.csrf import csrf_exempt

# Create your views here.

@csrf_exempt
def add_country(request):
    name = request.POST["name"]
    id_number = Instance.objects.filter().count()
    country = Instance(id_number+1,name,"country")
    country.save()
    return HttpResponse()

@csrf_exempt
def add_item(request):
    name = request.POST["name"]
    id_number = Instance.objects.filter().count()
    item = Instance(id_number+1,name,"item")
    item.save()
    return HttpResponse()