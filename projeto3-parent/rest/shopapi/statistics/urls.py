from django.urls import path, re_path
from . import views

urlpatterns=[
    re_path('listcountries',views.list_database_countries),
    re_path('listitems',views.list_database_items),
    re_path('revenuebyitem',views.get_revenue_by_item),
    re_path('expensesbyitem',views.get_expenses_by_item),
    re_path('profitbyitem',views.get_profit_by_item),
    re_path('totalrevenue',views.get_total_revenue),
    re_path('totalexpenses',views.get_total_expenses),
    re_path('totalprofit',views.get_total_profit),
    re_path('averageamountspentbinpurchasebyitem',views.get_average_amount_spent_in_purchase_by_item),
    re_path('averageamountspentinpurchase',views.get_average_amount_spent_in_purchase),
    re_path('highestprofititem',views.get_highest_profit_item),
    re_path('totalrevenuelasttime',views.get_total_revenue_time),
    re_path('totalexpenseslasttime',views.get_total_expenses_time),
    re_path('totalprofitlasttime',views.get_total_profit_last_time),
    re_path('highestsalescountrybyitem',views.get_highest_sales_country_by_item),
]