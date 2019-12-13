from django.db import models

# Create your models here.

class Item(models.Model):

    class Meta:
        db_table = 'item'

    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=100)

class Country(models.Model):

    class Meta:
        db_table = 'countries'


    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=50)

