#include<stdio.h>
#include<stdlib.h>
struct rectangle{
    int length;
    int breadth;
};
int main(){
struct rectangle *p;
p=(struct rectangle*)malloc(sizeof(struct rectangle));
p->length=10;
(*p).breadth=15;
printf("%d %d",(*p).length,p->breadth);

return 0;
}