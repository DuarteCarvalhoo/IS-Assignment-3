from django.db import models

# Create your models here.

class Item(models.Model):

    class Meta:
        db_table = 'item'

    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=100)
    price = models.DecimalField(max_digits=5,decimal_places=2)
    country = models.CharField(max_length=50)

class Country(models.Model):

    class Meta:
        db_table = 'countries'


    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)

