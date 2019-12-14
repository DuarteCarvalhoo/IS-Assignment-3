# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from dbinfo.models import Instance

from django.http import *
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

# Create your views here.

@csrf_exempt
def list_database_countries(request):
    country_list = list(Instance.objects.filter(data_type="country"))
    countries = ""
    for country in country_list:
        countries += country.name + ", "
    return HttpResponse(countries)

@csrf_exempt
def list_database_items(request):
    item_list = list(Instance.objects.filter(data_type="item"))
    items = ""
    for item in item_list:
        items += item.name + ", "
    return HttpResponse(items)

@csrf_exempt
def get_revenue_by_item(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_expenses_by_item(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_profit_by_item(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_total_revenue(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_total_expenses(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_total_profit(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_average_amount_spent_in_purchase_by_item(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_average_amount_spent_in_purchase(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_highest_profit_item(request):
    item_list = list(Instance.objects.filter(data_type="item"))
    item_name = ""
    profit = 0
    for item in item_list:
        if(item.profit > profit):
            profit = item.profit
            item_name = item.name
    return HttpResponse(item_name)

@csrf_exempt
def get_total_revenue_time(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_total_expenses_time(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_total_profit_last_time(request):
    #TO DO
    return HttpResponse()

@csrf_exempt
def get_highest_sales_country_by_item(request):
    #TO DO
    return HttpResponse()