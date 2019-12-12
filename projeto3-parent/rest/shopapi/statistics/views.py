# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from dbinfo.models import Country, Item

from django.http import *
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

# Create your views here.

@csrf_exempt
def list_database_countries(request):
    resources = list(Country.objects.filter())
    for resource in resources:
        print(resource.name)
    return HttpResponse()