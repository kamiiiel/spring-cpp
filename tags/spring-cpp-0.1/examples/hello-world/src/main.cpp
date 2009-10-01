//============================================================================
// Name        : hello-world.cpp
// Author      : Wojciech Mlynarczyk (wmlynar@gmail.com)
// Version     :
// Copyright   : 
// Description : Hello World in C++, Ansi-style
//============================================================================

#include "BeanFactory.h"

int main() {

    BeanFactory* beanFactory = new BeanFactory();

    beanFactory->getGreetings()->greet();

    delete beanFactory;

	return 0;
}
