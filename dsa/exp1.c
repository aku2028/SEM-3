#include <stdio.h>
#include <string.h>

struct Student
{
    int id;
    char name[30];
    int age;
    int m1, m2, m3, m4, m5;
    int total;
    float percentage;
    char grade[3];
};

int main()
{
    struct Student s[100];
    int n, i, highestTotal = 0;

    printf("Enter the number of students: ");
    scanf("%d", &n);

    for(i = 0; i < n; i++)
    {
        printf("\nEnter details of Student %d\n", i + 1);

        printf("Enter ID: ");
        scanf("%d", &s[i].id);

        printf("Enter Name: ");
        scanf("%s", s[i].name);

        printf("Enter Age: ");
        scanf("%d", &s[i].age);

        printf("Enter Marks in 5 Subjects: ");
        scanf("%d%d%d%d%d", &s[i].m1, &s[i].m2, &s[i].m3, &s[i].m4, &s[i].m5);

        s[i].total = s[i].m1 + s[i].m2 + s[i].m3 + s[i].m4 + s[i].m5;
        s[i].percentage = s[i].total / 5.0;

        if(s[i].percentage >= 90)
            strcpy(s[i].grade, "A+");
        else if(s[i].percentage >= 80)
            strcpy(s[i].grade, "A");
        else if(s[i].percentage >= 70)
            strcpy(s[i].grade, "B");
        else if(s[i].percentage >= 60)
            strcpy(s[i].grade, "C");
        else if(s[i].percentage >= 50)
            strcpy(s[i].grade, "D");
        else
            strcpy(s[i].grade, "F");

        if(s[i].total > highestTotal)
            highestTotal = s[i].total;
    }

    printf("\n----- Student Details -----\n");

    for(i = 0; i < n; i++)
    {
        printf("\nID = %d\n", s[i].id);
        printf("Name = %s\n", s[i].name);
        printf("Age = %d\n", s[i].age);
        printf("Total = %d\n", s[i].total);
        printf("Percentage = %.2f\n", s[i].percentage);
        printf("Grade = %s\n", s[i].grade);
    }

    printf("\n----- Topper(s) -----\n");

    for(i = 0; i < n; i++)
    {
        if(s[i].total == highestTotal)
        {
            printf("\nID = %d\n", s[i].id);
            printf("Name = %s\n", s[i].name);
            printf("Age = %d\n", s[i].age);
            printf("Total = %d\n", s[i].total);
            printf("Percentage = %.2f\n", s[i].percentage);
            printf("Grade = %s\n", s[i].grade);
        }
    }

    return 0;
}