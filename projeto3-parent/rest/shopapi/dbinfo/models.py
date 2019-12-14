from django.db import models

# Create your models here.

class Instance(models.Model):

    class Meta:
        db_table = 'instances'

    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=100)
    data_type = models.CharField(max_length=10)
    revenue = models.DecimalField(max_digits=5, decimal_places=2, default=0)
    expenses = models.DecimalField(max_digits=5, decimal_places=2, default=0)
    profit = models.DecimalField(max_digits=5, decimal_places=2, default=0)

