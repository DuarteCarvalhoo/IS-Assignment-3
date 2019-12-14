# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models

# Create your models here.

class Statistics(models.Model):

    class Meta:
        db_table = 'statistics'

    total_revenues = models.DecimalField(max_digits=5, decimal_places=2)
    total_expenses = models.DecimalField(max_digits=5, decimal_places=2)
    total_profit = models.DecimalField(max_digits=5, decimal_places=2)
    average_amout_per_purchase = models.DecimalField(max_digits=5, decimal_places=2)
    highest_profit_item_id = models.IntegerField()