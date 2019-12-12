from django.forms import ModelForm

from .models import Country, Item


class CountryForm(ModelForm):
    
    class Meta:
        model = Country
        fields =["name"]