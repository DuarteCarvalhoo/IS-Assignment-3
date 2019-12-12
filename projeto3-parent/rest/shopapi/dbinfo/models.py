from django.db import models

# Create your models here.

class Item(models.Model):

    class Meta:
        db_table = 'item'

    
    name = models.CharField(max_length=100)
    price = models.DecimalField(max_digits=5,decimal_places=2)
    #country = Country()

class Country(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=20)

    def serialize(self):
        c = self
        return {
            "id": c.id,
            "name": c.name,
        }

    class Meta:
        db_table = 'countries'