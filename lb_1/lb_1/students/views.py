from django.shortcuts import render

from django.shortcuts import render
from .models import Student

def student_list(request):
    students = Student.objects.all()
    return render(request, 'lb_1/index.html', {'students': students})

