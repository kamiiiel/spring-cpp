#include <string.h>
#include "test.h"

test::test()
{
  myComp1 = 0;
  myComp2 = 0;
  myComp3 = 0;
  myComponent4 = 0;
}

void *test::getBean(char *name)
{
  if(strcmp("myComp1",name)) return getMyComp1();
  if(strcmp("myComp2",name)) return getMyComp2();
  if(strcmp("myComp3",name)) return getMyComp3();
  if(strcmp("myComponent4",name)) return getMyComponent4();
  return 0;
}

void test::start()
{
  // ignored starting myComp1
  // ignored starting myComp2
  // ignored starting myComp3
  // ignored starting myComponent4
}

void test::stop()
{
  // ignored stopping myComp1
  // ignored stopping myComp2
  // ignored stopping myComp3
  // ignored stopping myComponent4
}

test::~test()
{
  if(myComp1) delete myComp1;
  if(myComp2) delete myComp2;
  if(myComp3) delete myComp3;
  if(myComponent4) delete myComponent4;
}

MyComponent1* test::getMyComp1()
{
  if(!myComp1) {
    myComp1 = new MyComponent1();
    myComp1->setMyComp2(getMyComp2());
  }
  return myComp1;
}

MyComponent2* test::getMyComp2()
{
  if(!myComp2) {
    myComp2 = new MyComponent2(0,true);
    myComp2->setText("mytext");
    myComp2->setInt(10);
  }
  return myComp2;
}

MyComponent3* test::getMyComp3()
{
  if(!myComp3) {
    myComp3 = new MyComponent3(getMyComp1());
  }
  return myComp3;
}

MyComponent4* test::getMyComponent4()
{
  if(!myComponent4) {
    myComponent4 = new MyComponent4();
  }
  return myComponent4;
}

