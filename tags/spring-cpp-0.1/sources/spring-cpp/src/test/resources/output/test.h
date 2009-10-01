#ifndef _TEST_H_
#define _TEST_H_
#include "someFile.h"
#include "mycomponent1.h"
#include "mycomponent2.h"
#include "mycomponent3.h"
#include "mycomponent4.h"
class test {
public:
  test();
  virtual void* getBean(char* name);
  virtual void start();
  virtual void stop();
  virtual ~test();
  MyComponent1* getMyComp1();
  MyComponent2* getMyComp2();
  MyComponent3* getMyComp3();
  MyComponent4* getMyComponent4();
protected:
  MyComponent1* myComp1;
  MyComponent2* myComp2;
  MyComponent3* myComp3;
  MyComponent4* myComponent4;
};
#endif // _TEST_H_

