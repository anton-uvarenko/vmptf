from django.db import models

class Subject(models.Model):
    name = models.CharField(max_length=100)

    def __str__(self):
        return self.name

class Student(models.Model):
    full_name = models.CharField(max_length=200)
    group = models.CharField(max_length=50)
    subjects = models.ManyToManyField(Subject, through='Grade')

    def __str__(self):
        return self.full_name

class Grade(models.Model):
    student = models.ForeignKey(Student, on_delete=models.CASCADE)
    subject = models.ForeignKey(Subject, on_delete=models.CASCADE)
    grade = models.IntegerField()

    def __str__(self):
        return f"{self.student.full_name} - {self.subject.name}: {self.grade}"

