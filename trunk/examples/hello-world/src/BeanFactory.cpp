#include <string.h>
#include "BeanFactory.h"

BeanFactory::BeanFactory()
{
  greetings = 0;
}

void *BeanFactory::getBean(char *name)
{
  if(strcmp("greetings",name)) return getGreetings();
  return 0;
}

void BeanFactory::start()
{
}

void BeanFactory::stop()
{
}

BeanFactory::~BeanFactory()
{
  if(greetings) delete greetings;
}

Greetings* BeanFactory::getGreetings()
{
  if(!greetings) {
    greetings = new Greetings();
  }
  return greetings;
}

