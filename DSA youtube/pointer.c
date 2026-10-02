#include<stdio.h>
int main(){
    int a[5]={2,4,6,8,10};
    int *p;
    p=a;
    for(int i =0; i<5; i++){
 printf("%d",a[i]);
 printf("%d",p[i]);
    }
   return 0;
}