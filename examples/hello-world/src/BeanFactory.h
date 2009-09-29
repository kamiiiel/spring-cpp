#ifndef _BEANFACTORY_H_
#define _BEANFACTORY_H_
#include "Greetings.h"
class BeanFactory {
public:
  BeanFactory();
  virtual void* getBean(char* name);
  virtual void start();
  virtual void stop();
  virtual ~BeanFactory();
  Greetings* getGreetings();
protected:
  Greetings* greetings;
};
#endif // _BEANFACTORY_H_

