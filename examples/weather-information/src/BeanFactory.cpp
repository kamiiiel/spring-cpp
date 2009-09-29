#include <string.h>
#include "BeanFactory.h"

BeanFactory::BeanFactory()
{
  displayWeather = 0;
  myLocalWeatherService = 0;
  myRemoteWeatherService = 0;
  myInternetClient = 0;
}

void *BeanFactory::getBean(char *name)
{
  if(strcmp("displayWeather",name)) return getDisplayWeather();
  if(strcmp("myLocalWeatherService",name)) return getMyLocalWeatherService();
  if(strcmp("myRemoteWeatherService",name)) return getMyRemoteWeatherService();
  if(strcmp("myInternetClient",name)) return getMyInternetClient();
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
  if(displayWeather) delete displayWeather;
  if(myLocalWeatherService) delete myLocalWeatherService;
  if(myRemoteWeatherService) delete myRemoteWeatherService;
  if(myInternetClient) delete myInternetClient;
}

DisplayWeather* BeanFactory::getDisplayWeather()
{
  if(!displayWeather) {
    displayWeather = new DisplayWeather();
    displayWeather->weatherService = getMyRemoteWeatherService();
  }
  return displayWeather;
}

LocalWeatherService* BeanFactory::getMyLocalWeatherService()
{
  if(!myLocalWeatherService) {
    myLocalWeatherService = new LocalWeatherService();
  }
  return myLocalWeatherService;
}

RemoteWeatherService* BeanFactory::getMyRemoteWeatherService()
{
  if(!myRemoteWeatherService) {
    myRemoteWeatherService = new RemoteWeatherService();
    myRemoteWeatherService->setInternetClient(getMyInternetClient());
    myRemoteWeatherService->setHostname("http://my.hostname.com/");
  }
  return myRemoteWeatherService;
}

InternetClient* BeanFactory::getMyInternetClient()
{
  if(!myInternetClient) {
    myInternetClient = new InternetClient();
    myInternetClient->setNumThreads(3);
  }
  return myInternetClient;
}

