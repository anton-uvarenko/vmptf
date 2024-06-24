from django.contrib import admin

from django.contrib import admin
from .models import Student, Subject, Grade

class GradeInline(admin.TabularInline):
    model = Grade
    extra = 1

class StudentAdmin(admin.ModelAdmin):
    inlines = [GradeInline]

admin.site.register(Student, StudentAdmin)
admin.site.register(Subject)
admin.site.register(Grade)

