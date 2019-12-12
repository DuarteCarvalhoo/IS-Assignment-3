from django.shortcuts import render

from .models import Country, Item
from .forms import CountryForm

from django.http import *
from django.views.decorators.csrf import csrf_exempt

# Create your views here.

@csrf_exempt
def add_country(request):
    country_form = CountryForm(request.POST)
    if country_form.is_valid():
        country = country_form.save(commit=False)
        return HttpResponse("DONE")
    return HttpResponseNotAllowed()